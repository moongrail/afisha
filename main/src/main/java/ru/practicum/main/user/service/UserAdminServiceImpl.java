package ru.practicum.main.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.main.user.dto.NewUserRequest;
import ru.practicum.main.user.dto.UserDto;
import ru.practicum.main.user.exception.UserNotFoundException;
import ru.practicum.main.user.exception.UserUniqueParameterEmailException;
import ru.practicum.main.user.model.User;
import ru.practicum.main.user.repositories.UserRepository;

import java.util.Arrays;
import java.util.List;

import static ru.practicum.main.user.mapper.UserMapperUtil.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAdminServiceImpl implements UserAdminService {
    private final UserRepository userRepository;

    @Override
    public UserDto addUser(NewUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserUniqueParameterEmailException("User with this email already exists");
        }

        User user = userRepository.save(fromDto(request));
        log.info("Added user: {}", user);

        return toUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers(Integer[] ids, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from, size, Sort.Direction.ASC, "id");

        if (ids == null) {
            return toUserDtoList(userRepository.findAll(pageable).getContent());
        }

        Long[] longIds = Arrays.stream(ids)
                .map(Long::valueOf)
                .toArray(Long[]::new);

        return toUserDtoList(userRepository.searchUsersByIds(longIds, pageable));
    }

    @Override
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User with this id does not exist");
        }

        userRepository.deleteById(userId);
    }
}
