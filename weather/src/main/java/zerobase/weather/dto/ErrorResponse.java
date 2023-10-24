package zerobase.weather.dto;

import lombok.*;
import zerobase.weather.type.ErrorCode;

@Getter
@RequiredArgsConstructor
public class ErrorResponse {
    private final ErrorCode errorCode;
    private final String errorMessage;
}
