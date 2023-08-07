package ru.practicum.main.common.util;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.practicum.main.user.exception.PaginationParameterException;

@UtilityClass
public class PaginationUtil {
    public static Pageable getPaginationAsc(Integer from, Integer size) {
        if (from == null || size == null) {
            return PageRequest.of(0, Integer.MAX_VALUE);
        }

        if (from < 0 || size < 0) {
            throw new PaginationParameterException("Неверные параметры пагинации.");
        }

        return PageRequest.of(from / size, size, Sort.Direction.ASC);
    }
}
