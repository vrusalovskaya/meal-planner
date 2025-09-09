package com.app.mealplanner.services;

import com.app.mealplanner.entities.MenuEntity;
import com.app.mealplanner.models.Menu;
import com.app.mealplanner.repositories.MenuRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MenuServiceImplTest {

    private static final LocalDate FIXED_NOW = LocalDate.parse("2025-07-20");

    @Mock
    private MenuRepository menuRepository;

    @InjectMocks
    private MenuServiceImpl menuService;

    @Test
    void validateDates_shouldThrow_whenStartDateInPast() {
        LocalDate startDate = FIXED_NOW.minusDays(1);
        LocalDate endDate = FIXED_NOW.plusDays(1);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> menuService.validateDates(null, startDate, endDate)
        );

        assertTrue(ex.getMessage().contains("must not be in the past"));
    }

    @ParameterizedTest
    @MethodSource("provideOverlappingDateRanges")
    void validateDates_shouldThrow_whenOverlapsWithExistingMenu(
            LocalDate existingStart, LocalDate existingEnd
    ) {
        LocalDate newStart = FIXED_NOW.plusDays(10);
        LocalDate newEnd = FIXED_NOW.plusDays(20);

        MenuEntity overlappingMenu = new MenuEntity();
        overlappingMenu.setId(1L);
        overlappingMenu.setStartDate(existingStart);
        overlappingMenu.setEndDate(existingEnd);

        when(menuRepository.findMenusEndingNowOrLater(any()))
                .thenReturn(List.of(overlappingMenu));

        assertThrows(IllegalStateException.class,
                () -> menuService.validateDates(2L, newStart, newEnd));
    }

    @ParameterizedTest
    @MethodSource("provideNonOverlappingDateRanges")
    void validateDates_shouldPass_whenNoOverlap(
            LocalDate existingStart, LocalDate existingEnd
    ) {
        LocalDate newStart = FIXED_NOW.plusDays(10);
        LocalDate newEnd = FIXED_NOW.plusDays(20);

        MenuEntity nonOverlappingMenu = new MenuEntity();
        nonOverlappingMenu.setId(1L);
        nonOverlappingMenu.setStartDate(existingStart);
        nonOverlappingMenu.setEndDate(existingEnd);

        when(menuRepository.findMenusEndingNowOrLater(any()))
                .thenReturn(List.of(nonOverlappingMenu));

        assertDoesNotThrow(() -> menuService.validateDates(2L, newStart, newEnd));
    }

    @Test
    void validateDates_shouldPass_whenNoExistingMenus() {
        LocalDate startDate = FIXED_NOW.plusDays(1);
        LocalDate endDate = FIXED_NOW.plusDays(2);

        when(menuRepository.findMenusEndingNowOrLater(any()))
                .thenReturn(Collections.emptyList());

        assertDoesNotThrow(() -> menuService.validateDates(null, startDate, endDate));
    }

    @Test
    void validateDates_shouldPass_whenUpdatingSameMenu() {
        LocalDate startDate = FIXED_NOW.plusDays(10);
        LocalDate endDate = FIXED_NOW.plusDays(20);

        MenuEntity existingMenu = new MenuEntity();
        existingMenu.setId(1L);
        existingMenu.setStartDate(startDate);
        existingMenu.setEndDate(endDate);

        when(menuRepository.findMenusEndingNowOrLater(any()))
                .thenReturn(List.of(existingMenu));

        assertDoesNotThrow(() -> menuService.validateDates(1L, startDate, endDate));
    }

    private static Stream<Arguments> provideOverlappingDateRanges() {
        LocalDate base = FIXED_NOW;

        return Stream.of(
                Arguments.of(base.plusDays(5), base.plusDays(15)),
                Arguments.of(base.plusDays(15), base.plusDays(25)),
                Arguments.of(base.plusDays(12), base.plusDays(18)),
                Arguments.of(base.plusDays(5), base.plusDays(25)),
                Arguments.of(base.plusDays(10), base.plusDays(20)),
                Arguments.of(base.plusDays(0), base.plusDays(10)),
                Arguments.of(base.plusDays(20), base.plusDays(30))
        );
    }

    private static Stream<Arguments> provideNonOverlappingDateRanges() {
        LocalDate base = FIXED_NOW;

        return Stream.of(
                Arguments.of(base.plusDays(0), base.plusDays(9)),
                Arguments.of(base.plusDays(21), base.plusDays(30))
        );
    }
}

