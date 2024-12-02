import manager.InMemoryTaskManager;
import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

    @Test
    void testGetDefaultReturnsInitializedTaskManager() {
        TaskManager taskManager = Managers.getDefault();
        assertNotNull(taskManager, "Менеджер задач не должен быть null.");
        assertTrue(taskManager instanceof InMemoryTaskManager, "Менеджер должен быть экземпляром InMemoryTaskManager.");
        assertNotNull(taskManager.getAllTasks(), "Список задач не должен быть null.");
        assertTrue(taskManager.getAllTasks().isEmpty(), "Список задач должен быть пустым при инициализации.");
    }
}