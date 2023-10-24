package zerobase.weather.exception;

import lombok.*;
import zerobase.weather.type.ErrorCode;

@Getter
@RequiredArgsConstructor
public class WeatherException extends RuntimeException {
    private final ErrorCode errorCode;

    private final String errorMessage;

    public WeatherException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}
