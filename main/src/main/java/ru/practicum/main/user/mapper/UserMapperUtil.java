package ru.practicum.main.user.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.main.user.dto.NewUserRequest;
import ru.practicum.main.user.dto.UserShortDto;
import ru.practicum.main.user.model.User;

@UtilityClass
public class UserMapperUtil {

    public static NewUserRequest toDtoRequest(User user) {
        return NewUserRequest.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public static UserShortDto toDtoUserShort(User user) {
        return UserShortDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }

    public static User fromDto(NewUserRequest newUserRequest) {
        return User.builder()
                .name(newUserRequest.getName())
                .email(newUserRequest.getEmail())
                .build();
    }
}
