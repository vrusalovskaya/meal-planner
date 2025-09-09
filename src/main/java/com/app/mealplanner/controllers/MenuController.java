package com.app.mealplanner.controllers;

import com.app.mealplanner.dtos.dishes.DishDto;
import com.app.mealplanner.dtos.menus.DishUpdateInMenuDto;
import com.app.mealplanner.dtos.menus.MenuCreateDto;
import com.app.mealplanner.dtos.menus.MenuDto;
import com.app.mealplanner.mappers.ModelDtoMapper;
import com.app.mealplanner.models.DailyRation;
import com.app.mealplanner.models.Dish;
import com.app.mealplanner.models.Menu;
import com.app.mealplanner.services.MenuService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/menus")
@AllArgsConstructor
public class MenuController {

    private MenuService menuService;

    @GetMapping
    public List<MenuDto> getMenus() {
        return menuService.getAll().stream().map(ModelDtoMapper::toDto).toList();
    }

    @GetMapping("/past")
    public List<MenuDto> getPastMenus() {
        return menuService.getPast().stream().map(ModelDtoMapper::toDto).toList();
    }

    @GetMapping("/future")
    public List<MenuDto> getFutureMenus() {
        return menuService.getFuture().stream().map(ModelDtoMapper::toDto).toList();
    }

    @GetMapping("/{id}")
    public MenuDto getMenu(@PathVariable Long id) {
        return ModelDtoMapper.toDto(menuService.get(id));
    }

    @GetMapping("/current")
    public ResponseEntity<MenuDto> getCurrent() {
        return menuService.getCurrent()
                .map(menu -> ResponseEntity.ok(ModelDtoMapper.toDto(menu)))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping
    public ResponseEntity<MenuDto> create(@RequestBody MenuCreateDto createDto) {
        List<DailyRation> dailyRations = ModelDtoMapper.toCreateModels(createDto.getRations());
        Menu menu = menuService.create(createDto.getStartDate(), createDto.getEndDate(), dailyRations);
        MenuDto createdMenuDto = ModelDtoMapper.toDto(menu);
        URI location = URI.create(String.format("/api/v1/menus/%d", createdMenuDto.getId()));
        return ResponseEntity.created(location).body(createdMenuDto);
    }

    @PatchMapping("/current/{id}")
    public ResponseEntity<MenuDto> updateCurrent(@PathVariable Long id,
                                                 @RequestBody List<DishUpdateInMenuDto> updateRequest) {
        Map<Long, Long> updatedDishes = new HashMap<>();
        for (DishUpdateInMenuDto dto : updateRequest) {
            updatedDishes.put(dto.getRationItemId(), dto.getDishId());
        }
        Menu menu = menuService.updateCurrent(id, updatedDishes);
        MenuDto menuDto = ModelDtoMapper.toDto(menu);
        return ResponseEntity.ok(menuDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MenuDto> update(@PathVariable Long id,
                                          @RequestBody MenuDto updatedMenu) {
        Menu menu = menuService.update(ModelDtoMapper.toModel(updatedMenu));
        MenuDto menuDto = ModelDtoMapper.toDto(menu);
        return ResponseEntity.ok(menuDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        menuService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
