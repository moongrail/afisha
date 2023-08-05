package ru.practicum.main.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.main.user.dto.NewUserRequest;
import ru.practicum.main.user.dto.UserShortDto;
import ru.practicum.main.user.exception.UserNotFoundException;
import ru.practicum.main.user.exception.UserUniqueParameterEmailException;
import ru.practicum.main.user.mapper.UserMapperUtil;
import ru.practicum.main.user.model.User;
import ru.practicum.main.user.repositories.UserRepository;
import ru.practicum.main.util.PaginationUtil;

import java.util.List;

import static ru.practicum.main.user.mapper.UserMapperUtil.*;
import static ru.practicum.main.util.PaginationUtil.getPaginationWithoutSort;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAdminServiceImpl implements UserAdminService {
    private final UserRepository userRepository;

    @Override
    public UserShortDto addUser(NewUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserUniqueParameterEmailException("User with this email already exists");
        }

        User user = userRepository.save(fromDto(request));
        log.info("Added user: {}", user);

        return toDtoUserShort(user);
    }

    @Override
    public List<User> getAllUsers(Integer[] ids, Integer from, Integer size) {
        Pageable pageable = getPaginationWithoutSort(from, size);

        return userRepository.searchUsersByIds(ids, pageable);
    }

    @Override
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User with this id does not exist");
        }

        userRepository.deleteById(userId);
    }
}
