import manager.HistoryManager;
import manager.InMemoryTaskManager;
import manager.Managers;
import org.junit.jupiter.api.Test;
import tasks.Task;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    private InMemoryTaskManager taskManager = new InMemoryTaskManager(Managers.getDefaultHistory());
    private HistoryManager historyManager = Managers.getDefaultHistory();

    @Test
    void testAddToHistory() {
        Task task = new Task("Test Task", "Test description");
        taskManager.createTask(task);
        historyManager.add(task);
        final ArrayList<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не должна быть пустой.");
        assertEquals(1, history.size(), "История должна содержать одну задачу.");
        assertEquals(task, history.get(0), "Задача в истории не совпадает с добавленной.");
    }

    @Test
    void testSavedPreviousVersionOfTask() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        InMemoryTaskManager taskManager = new InMemoryTaskManager(historyManager);
        Task constTask = new Task("Constant Task Title", "Constant Task Description");
        taskManager.createTask(constTask);
        ArrayList<Task> taskHistory = historyManager.getHistory();
        assertNotNull(taskHistory, "История задачи должна быть не пустой.");
        assertEquals(1, taskHistory.size(), "История задачи должна содержать одну задачу.");
        assertEquals(constTask, taskHistory.get(0), "История задачи должна содержать оригинальную задачу.");
    }
}