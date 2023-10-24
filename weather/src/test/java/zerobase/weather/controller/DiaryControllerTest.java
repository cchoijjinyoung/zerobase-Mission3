package zerobase.weather.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import zerobase.weather.domain.Diary;
import zerobase.weather.exception.WeatherException;
import zerobase.weather.service.DiaryService;
import zerobase.weather.type.ErrorCode;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DiaryController.class)
class DiaryControllerTest {

    @MockBean
    private DiaryService diaryService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("일기 저장 테스트")
    void successCreateDiary() throws Exception {
        LocalDate date = LocalDate.of(2023, 10, 20);
        String text = "오늘 날씨 너무 좋다.";

        mockMvc.perform(post("/create/diary")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("date", date.toString())
                        .content(text)
                )
                .andExpect(status().isOk())
                .andDo(print());

        verify(diaryService).createDiary(date, text);
    }

    @Test
    @DisplayName("일기 조회 테스트")
    void successReadDiary() throws Exception {
        // given
        LocalDate date = LocalDate.of(2023, 10, 20);
        Diary diary1 = new Diary();
        diary1.setDate(date);
        diary1.setText("다이어리1");

        Diary diary2 = new Diary();
        diary2.setDate(date);
        diary2.setText("다이어리2");

        Diary diary3 = new Diary();
        diary3.setDate(date);
        diary3.setText("다이어리3");

        List<Diary> diaries = Arrays.asList(diary1, diary2, diary3);

        given(diaryService.readDiary(any(LocalDate.class)))
                .willReturn(diaries);
        // when

        // then
        mockMvc.perform(get("/read/diary")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("date", date.toString())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].date").value("2023-10-20"))
                .andExpect(jsonPath("$.[0].text").value("다이어리1"))
                .andExpect(jsonPath("$.[1].date").value("2023-10-20"))
                .andExpect(jsonPath("$.[1].text").value("다이어리2"))
                .andExpect(jsonPath("$.[2].date").value("2023-10-20"))
                .andExpect(jsonPath("$.[2].text").value("다이어리3"))
                .andDo(print());
    }

    @Test
    @DisplayName("일기 조회 테스트 - 실패")
    void failReadDiary() throws Exception {
        // given
        given(diaryService.readDiary(any(LocalDate.class)))
                .willThrow(new WeatherException(ErrorCode.FUTURE_DATE_NOT_ALLOWED));
        // when

        // then
        mockMvc.perform(get("/read/diary")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("date", "4000-10-20")
                )
                .andExpect(jsonPath("$.errorCode").value("FUTURE_DATE_NOT_ALLOWED"))
                .andExpect(jsonPath("$.errorMessage").value("너무 미래의 날짜입니다."))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("선택 기간 모든 일기 조회 테스트")
    void successReadDiaries() throws Exception {
        // given
        LocalDate date = LocalDate.of(2023, 10, 20);
        LocalDate startDate = LocalDate.of(2023, 10, 18);
        LocalDate endDate = LocalDate.of(2023, 10, 22);
        Diary diary1 = new Diary();
        diary1.setDate(startDate);
        diary1.setText("18일 다이어리");

        Diary diary2 = new Diary();
        diary2.setDate(date);
        diary2.setText("20일 다이어리");

        Diary diary3 = new Diary();
        diary3.setDate(endDate);
        diary3.setText("22일 다이어리");

        List<Diary> diaries = Arrays.asList(diary1, diary2, diary3);

        given(diaryService.readDiaries(any(LocalDate.class), any(LocalDate.class)))
                .willReturn(diaries);
        // when

        // then
        mockMvc.perform(get("/read/diaries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].date").value("2023-10-18"))
                .andExpect(jsonPath("$.[0].text").value("18일 다이어리"))
                .andExpect(jsonPath("$.[1].date").value("2023-10-20"))
                .andExpect(jsonPath("$.[1].text").value("20일 다이어리"))
                .andExpect(jsonPath("$.[2].date").value("2023-10-22"))
                .andExpect(jsonPath("$.[2].text").value("22일 다이어리"))
                .andDo(print());
    }

    @Test
    @DisplayName("일기 수정 테스트")
    void successUpdateDiary() throws Exception {
        // given
        LocalDate date = LocalDate.of(2023, 10, 20);
        String text = "오늘 날씨 너무 좋다.";

        // when

        // then
        mockMvc.perform(put("/update/diary")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("date", date.toString())
                        .content(text)
                )
                .andExpect(status().isOk())
                .andDo(print());

        verify(diaryService).updateDiary(date, text);

    }


    @Test
    @DisplayName("일기 삭제 테스트")
    void successDeleteDiary() throws Exception {
        // given
        LocalDate date = LocalDate.of(2023, 10, 20);
        String text = "오늘 날씨 너무 좋다.";

        // when

        // then
        mockMvc.perform(delete("/delete/diary")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("date", date.toString())
                        .content(text)
                )
                .andExpect(status().isOk())
                .andDo(print());

        verify(diaryService).deleteDiary(date);
    }
}