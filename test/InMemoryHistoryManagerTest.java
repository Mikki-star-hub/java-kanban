import manager.HistoryManager;
import manager.InMemoryHistoryManager;
import manager.Managers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    private HistoryManager historyManager;
    private Task task1;
    private Task task2;
    private Task task3;
    private static final int MAX_HISTORY_SIZE = 10;

    @BeforeEach
    void setUp() {
        historyManager = Managers.getDefaultHistory();
        task1 = new Task("Task 1", "Description 1");
        task2 = new Task("Task 2", "Description 2");
        task3 = new Task("Task 3", "Description 3");
        task1.setId(1);
        task2.setId(2);
        task3.setId(3);
    }

    @Test
    void testAddTaskToHistory() {
        historyManager.add(task1);
        historyManager.add(task2);

        assertEquals(2, historyManager.getHistory().size(), "История должна содержать 2 задачи.");
        assertTrue(historyManager.getHistory().contains(task1), "История должна содержать Task 1.");
        assertTrue(historyManager.getHistory().contains(task2), "История должна содержать Task 2.");
    }

    @Test
    void testSavedPreviousVersionOfTask() {
        historyManager.add(task1);
        // Получаем задачу из истории
        List<Task> taskHistory = historyManager.getHistory();
        assertNotNull(taskHistory, "История задачи должна быть не пустой.");
        assertEquals(1, taskHistory.size(), "История задачи должна содержать одну задачу.");
        assertEquals(task1, taskHistory.get(0), "История задачи должна содержать оригинальную задачу.");
    }

    @Test
    void testAddAndGetHistory() {
        historyManager.add(task1);
        historyManager.add(task2);

        List<Task> history = historyManager.getHistory();

        assertEquals(2, history.size());
        assertEquals(task1, history.get(0));
        assertEquals(task2, history.get(1));
    }
    @Test
    void testRemoveFromHistoryStart() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);

        historyManager.remove(1); // Удаляем из начала

        List<Task> history = historyManager.getHistory();

        assertEquals(2, history.size(), "История должна содержать 2 задачи.");
        assertEquals(task2, history.get(0), "Первая задача должна быть Task 2.");
        assertEquals(task3, history.get(1), "Вторая задача должна быть Task 3.");
    }

    @Test
    void testRemoveFromHistoryMiddle() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);

        historyManager.remove(2); // Удаляем из середины

        List<Task> history = historyManager.getHistory();

        assertEquals(2, history.size(), "История должна содержать 2 задачи.");
        assertEquals(task1, history.get(0), "Первая задача должна быть Task 1.");
        assertEquals(task3, history.get(1), "Вторая задача должна быть Task 3.");
    }

    @Test
    void testRemoveFromHistoryEnd() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);

        historyManager.remove(3); // Удаляем из конца

        List<Task> history = historyManager.getHistory();

        assertEquals(2, history.size(), "История должна содержать 2 задачи.");
        assertEquals(task1, history.get(0), "Первая задача должна быть Task 1.");
        assertEquals(task2, history.get(1), "Вторая задача должна быть Task 2.");
    }

    @Test
    void testDuplicateAddition() {
        historyManager.add(task1);
        historyManager.add(task1); // Добавляем дубликат

        List<Task> history = historyManager.getHistory();

        assertEquals(1, history.size());
        assertEquals(task1, history.get(0));
    }
}
