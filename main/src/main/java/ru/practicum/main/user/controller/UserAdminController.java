package ru.practicum.main.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.user.dto.NewUserRequest;
import ru.practicum.main.user.dto.UserDto;
import ru.practicum.main.user.dto.UserShortDto;
import ru.practicum.main.user.model.User;
import ru.practicum.main.user.service.UserAdminService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@Validated
public class UserAdminController {
    private final UserAdminService userAdminService;

    @PostMapping
    public ResponseEntity<UserShortDto> addUser(@RequestBody @Valid NewUserRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userAdminService.addUser(request));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestParam(required = false) Integer[] ids,
                                                     @RequestParam( defaultValue = "0") @PositiveOrZero Integer from,
                                                     @RequestParam( defaultValue = "10") @Positive Integer size) {
        log.info("Get all users: {}", ids);
        return ResponseEntity.ok(userAdminService.getAllUsers(ids, from, size));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        userAdminService.deleteUser(userId);
        return ResponseEntity
                .ok()
                .body("User deleted: " + userId);
    }
}
