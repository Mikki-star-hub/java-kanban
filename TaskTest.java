import org.junit.jupiter.api.Test;
import tasks.Task;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void testTaskEqualityById() {
        Task task1 = new Task("Task 1", "Description 1");
        Task task2 = new Task("Task 1", "Description 1");
        task1.setId(1);
        task2.setId(1);
        assertEquals(task1, task2, "Задачи должны быть равны по ID.");
    }
}
