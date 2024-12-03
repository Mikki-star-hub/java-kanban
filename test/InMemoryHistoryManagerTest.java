import manager.HistoryManager;
import manager.InMemoryHistoryManager;
import manager.Managers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Task;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    private HistoryManager historyManager;
    private static final int MAX_HISTORY_SIZE = 10;

    @BeforeEach
    void setUp() {
        historyManager = Managers.getDefaultHistory();
    }

    @Test
    void testAddTaskToHistory() {
        Task task1 = new Task( "Task 1", "Description 1");
        Task task2 = new Task( "Task 2", "Description 2");

        historyManager.add(task1);
        historyManager.add(task2);

        assertEquals(2, historyManager.getHistory().size(), "История должна содержать 2 задачи.");
        assertTrue(historyManager.getHistory().contains(task1), "История должна содержать Task 1.");
        assertTrue(historyManager.getHistory().contains(task2), "История должна содержать Task 2.");
    }

    @Test
    void testSavedPreviousVersionOfTask() {
        Task task = new Task( "Constant Task Title", "Constant Task Description");
        historyManager.add(task);
        // Получаем задачу из истории
        ArrayList<Task> taskHistory = historyManager.getHistory();
        assertNotNull(taskHistory, "История задачи должна быть не пустой.");
        assertEquals(1, taskHistory.size(), "История задачи должна содержать одну задачу.");
        assertEquals(task, taskHistory.get(0), "История задачи должна содержать оригинальную задачу.");
    }

    @Test
    void testHistorySizeLimit() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        // Создаем и добавляем задачи, допустим их 12
        for (int i = 1; i <= 12; i++) {
            Task task = new Task("Task " + i, "Description " + i);
            task.setId(i);
            historyManager.add(task);
        }
        ArrayList<Task> taskHistory = historyManager.getHistory();
        assertEquals(MAX_HISTORY_SIZE, taskHistory.size(), "История должна содержать " + MAX_HISTORY_SIZE + " задач.");
        assertEquals(3, taskHistory.get(0).getId(), "Первая задача в истории должна быть Task 3.");
        assertEquals(12, taskHistory.get(taskHistory.size() - 1).getId(), "Последняя задача в истории должна быть Task 12.");
         // не понимаю почему он не считает кол-во? при тесте всегда 0 выводит
    }
}