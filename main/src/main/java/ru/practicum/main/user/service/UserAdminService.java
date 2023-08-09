package ru.practicum.main.user.service;

import ru.practicum.main.user.dto.NewUserRequest;
import ru.practicum.main.user.dto.UserDto;

import java.util.List;

public interface UserAdminService {
    UserDto addUser(NewUserRequest request);

    List<UserDto> getAllUsers(Integer[] ids, Integer from, Integer size);

    void deleteUser(Long userId);
}
