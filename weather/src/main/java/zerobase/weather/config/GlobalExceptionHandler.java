package zerobase.weather.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import zerobase.weather.dto.ErrorResponse;
import zerobase.weather.exception.WeatherException;

import static zerobase.weather.type.ErrorCode.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WeatherException.class)
    public ErrorResponse handleWeatherException(WeatherException e) {
        log.error("{} is occurred.", e.getErrorCode());
        return new ErrorResponse(e.getErrorCode(), e.getErrorMessage());
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponse handleAllException(Exception e) {
        log.error("Exception is occurred.", e);
        return new ErrorResponse(INTERNAL_SERVER_ERROR,
                INTERNAL_SERVER_ERROR.getDescription());
    }
}
