package ru.kpfu.MotivationAppBackend.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.kpfu.MotivationAppBackend.entity.Task;
import ru.kpfu.MotivationAppBackend.enums.Platform;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@DataJpaTest
//@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TaskRepositoryTest {
    @Autowired
    private TaskRepository taskRepository;


    @Test
    //@Rollback(false)
    public void shouldSaveTask(){
        Task task = new Task();
        task.setPlatform(Platform.CODEFORCES);
        task.setTitle("MegaHell");
        task.setLink("https://codeforces.com/problemset/problem/666/A");

        // Сохраняем в БД
        Task savedTask = taskRepository.save(task);

        // Проверяем, что объект сохранился
        assertThat(savedTask.getId()).isNotNull();
    }

    @Test
    public void shouldRetrieveTask() {



        // Загружаем объект из БД
        Optional<Task> foundTask = taskRepository.findById(1L);

        assertThat(foundTask).isPresent();
        System.out.println(foundTask);
        // Проверяем корректность данных
        assertThat(foundTask.get().getTitle()).isEqualTo("Two Sum Problem");
        assertThat(foundTask.get().getPlatform()).isEqualTo(Platform.CODEFORCES);
        assertThat(foundTask.get().getLink()).isEqualTo("https://codeforces.com/problemset/problem/1/A");
    }
}