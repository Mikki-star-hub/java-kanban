package test;

import manager.InMemoryTaskManager;
import manager.HistoryManager;
import manager.Managers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.Status;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    private InMemoryTaskManager taskManager = new InMemoryTaskManager(Managers.getDefaultHistory());
    private HistoryManager historyManager = Managers.getDefaultHistory();

    @Test
    void testTaskEqualityById() {
        Task task1 = new Task("Task 1", "Description 1");
        Task task2 = new Task("Task 1", "Description 1");
        task1.setId(1);
        task2.setId(1);
        assertEquals(task1, task2, "Задачи должны быть равны по ID.");
    }

    @Test
    void testSubtaskEqualityById() {
        Epic epic = new Epic("Epic 1", "Epic Description");
        Subtask subtask1 = new Subtask("Subtask 1", "Subtask Description", epic.getId());
        Subtask subtask2 = new Subtask("Subtask 1", "Subtask Description", epic.getId());
        subtask1.setId(1);
        subtask2.setId(1);
        assertEquals(subtask1, subtask2, "Подзадачи должны быть равны по ID.");
    }

    @Test
    void testEpicCannotBeSubtaskOfItself() {
        Epic epic = new Epic("Epic 1", "Epic Description");
        epic.setId(1);
        Subtask subtask = new Subtask("Subtask", "Subtask Description", epic.getId());
        subtask.setId(1);
        taskManager.createEpic(epic);
        taskManager.createSubtask(subtask);
        ArrayList<Subtask> subtasks = taskManager.getSubtasksByEpicId(epic.getId());
        assertTrue(subtasks.isEmpty(), "Эпик не должен иметь подзадач, если подзадача ссылается на него.");
    }

    @Test
    void testInMemoryTaskManagerAddsDifferentTaskTypes() {
        Task task = new Task("Task", "Description");
        Subtask subtask = new Subtask("Subtask", "Description", 1);
        Epic epic = new Epic("Epic", "Description");
        taskManager.createTask(task);
        taskManager.createSubtask(subtask);
        taskManager.createEpic(epic);
        assertEquals(task, taskManager.getById(task.getId()), "Задача не найдена по ID.");
        assertEquals(subtask, taskManager.getById(subtask.getId()), "Подзадача не найдена по ID.");
        assertEquals(epic, taskManager.getById(epic.getId()), "Эпик не найден по ID.");
    }

    @Test
    void testNoConflictWithGeneratedIds() {
        Task task1 = new Task("Task 1", "Description");
        taskManager.createTask(task1);
        Task task2 = new Task("Task 2", "Description");
        taskManager.createTask(task2);

        assertNotEquals(task1.getId(), task2.getId(), "ID задач должны быть уникальными.");
        assertEquals(task1, taskManager.getById(task1.getId()), "Задача с ID 1 должна быть task1.");
    }

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
}
