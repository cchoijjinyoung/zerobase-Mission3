package zerobase.weather.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import zerobase.weather.domain.Memo;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class JdbcMemoRepositoryTest {

    @Autowired
    JdbcMemoRepository jdbcMemoRepository;

    @Test
    @DisplayName("메모 insert 테스트")
    void insertMemoTest() {
        // given
        Memo newMemo = new Memo(1, "this is new memo");
        // when
        jdbcMemoRepository.save(newMemo);
        // then
        Optional<Memo> result = jdbcMemoRepository.findById(1);

        assertEquals("this is new memo", result.get().getText());
    }

    @Test
    @DisplayName("모든 메모 목록 가져오기 테스트")
    void findAllTest() {
        // given
        List<Memo> newMemos = Arrays.asList(
                new Memo(1, "first"),
                new Memo(2, "second"),
                new Memo(3, "third"));

        newMemos.stream().forEach(memo -> jdbcMemoRepository.save(memo));
        // when
        List<Memo> AllMemo = jdbcMemoRepository.findAll();
        // then
        assertEquals(3, AllMemo.size());
    }
}