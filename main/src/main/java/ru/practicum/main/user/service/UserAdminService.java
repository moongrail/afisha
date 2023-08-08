package ru.practicum.main.user.service;

import ru.practicum.main.user.dto.NewUserRequest;
import ru.practicum.main.user.dto.UserDto;
import ru.practicum.main.user.dto.UserShortDto;
import ru.practicum.main.user.model.User;

import java.util.List;

public interface UserAdminService {
    UserShortDto addUser(NewUserRequest request);

    List<UserDto> getAllUsers(Integer[] ids, Integer from, Integer size);

    void deleteUser(Long userId);
}
