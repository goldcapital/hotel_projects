package com.example.hotel_projects.service;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommandLineAppStartupRunner implements CommandLineRunner {
    private final  InitAminService initAminService;
    @Override
    public void run(String... args) throws Exception {
       initAminService.initAdmin();
    }
}
