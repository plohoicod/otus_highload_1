package ru.otus.highload.homework.first.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.highload.homework.first.dto.RegisterDto;
import ru.otus.highload.homework.first.dto.UserDto;
import ru.otus.highload.homework.first.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;


    @GetMapping("/user/get/{id}")
    public UserDto getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/user/register")
    public Integer registerUser(@RequestBody RegisterDto registerDto) {
        return userService.registerUser(registerDto);
    }


    @PostMapping("/login")
    public void login() {
    }
}
