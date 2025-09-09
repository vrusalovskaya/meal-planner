package com.app.mealplanner;

import com.app.mealplanner.services.SeedingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeedingConfig {
    @Bean
    @ConditionalOnProperty(name = "app.seeding-enabled", havingValue = "true")
    public CommandLineRunner fullSeeder(SeedingService seedingService) {
        return args -> {
            System.out.println("Full seeding triggered...");
            seedingService.seed();
        };
    }

    @Bean
    @ConditionalOnProperty(name = "app.seed-menus-only", havingValue = "true")
    public CommandLineRunner menuSeeder(SeedingService seedingService) {
        return args -> {
            System.out.println("Menu-only seeding triggered...");
            seedingService.seedMenusOnly();
        };
    }
}
