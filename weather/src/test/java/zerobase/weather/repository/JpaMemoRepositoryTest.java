package zerobase.weather.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import zerobase.weather.domain.Memo;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class JpaMemoRepositoryTest {

    @Autowired
    JpaMemoRepository jpaMemoRepository;

    @Test
    @DisplayName("메모 insert 테스트")
    void insertMemoTest() {
        // given
        Memo newMemo = new Memo(1, "now test" + LocalDateTime.now());
        // when
        jpaMemoRepository.save(newMemo);
        // then
        List<Memo> result = jpaMemoRepository.findAll();
        assertTrue(result.size() > 0);
    }

    @Test
    @DisplayName("모든 메모 목록 가져오기 테스트")
    void findAllTest() {
        // given
        List<Memo> newMemos = Arrays.asList(
                new Memo(1, "first"),
                new Memo(2, "second"),
                new Memo(3, "third"));

        newMemos.stream().forEach(memo -> jpaMemoRepository.save(memo));
        // when
        List<Memo> AllMemo = jpaMemoRepository.findAll();
        // then
        assertEquals(3, AllMemo.size());
    }

    @Test
    @DisplayName("아이디로 메모찾기 테스트")
    void findByIdTest() {
        // given
        Memo newMemo = new Memo(1, "jpa");

        // when
        Memo memo = jpaMemoRepository.save(newMemo);

        // then
        Optional<Memo> findMemo = jpaMemoRepository.findById(memo.getId());
        assertEquals("jpa", findMemo.get().getText());
    }
}