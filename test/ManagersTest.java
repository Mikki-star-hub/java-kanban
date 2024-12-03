import manager.InMemoryTaskManager;
import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.Test;
import manager.HistoryManager;
import static org.junit.jupiter.api.Assertions.*;
import manager.InMemoryHistoryManager;
class ManagersTest {

    @Test
    void testGetDefaultReturnsInitializedTaskManager() {
        TaskManager taskManager = Managers.getDefault();
        assertNotNull(taskManager, "Менеджер задач не должен быть null.");
        assertTrue(taskManager instanceof InMemoryTaskManager, "Менеджер должен быть экземпляром InMemoryTaskManager.");
    }

    @Test
    void testGetDefaultHistoryReturnsInitializedHistoryManager() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        assertNotNull(historyManager, "Менеджер истории не должен быть null.");
        assertTrue(historyManager instanceof InMemoryHistoryManager, "Менеджер истории должен быть экземпляром InMemoryHistoryManager.");
    }
}