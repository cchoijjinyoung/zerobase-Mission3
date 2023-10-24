package zerobase.weather.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    INTERNAL_SERVER_ERROR("내부 서버 오류가 발생했습니다."),
    FUTURE_DATE_NOT_ALLOWED("너무 미래의 날짜입니다."),
    PAST_DATE_NOT_ALLOWED("너무 과거의 날짜입니다.");

    private final String description;
}
