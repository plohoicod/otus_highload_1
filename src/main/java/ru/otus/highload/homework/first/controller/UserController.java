package ru.otus.highload.homework.first.controller;


import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Получение анкеты пользователя")
    public UserDto getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/user/register")

    @Operation(summary = "Регистрация нового пользователя")
    public Long registerUser(@RequestBody RegisterDto registerDto) {
        return userService.registerUser(registerDto);
    }


    @PostMapping("/login")

    @Operation(summary = "Вход в систему")
    public void login() {
    }
}
