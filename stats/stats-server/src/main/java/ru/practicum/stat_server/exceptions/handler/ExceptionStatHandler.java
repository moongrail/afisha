package ru.practicum.stat_server.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.stat_server.controllers.StatisticController;
import ru.practicum.stat_server.exceptions.StatDateParameterException;

@RestControllerAdvice(basePackageClasses = StatisticController.class)
public class ExceptionStatHandler {
    @ExceptionHandler(StatDateParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleStatDateParameterException(StatDateParameterException ex) {
        return ex.getMessage();
    }
}
