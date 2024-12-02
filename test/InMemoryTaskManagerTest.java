import manager.HistoryManager;
import manager.InMemoryTaskManager;
import manager.Managers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
   private InMemoryTaskManager taskManager;
    HistoryManager historyManager ;

    @BeforeEach
    void toInstall() {
        historyManager = Managers.getDefaultHistory();
        taskManager = new InMemoryTaskManager(historyManager);
    }

    @Test
    void testAddAndFindTasksById() {
        Task task = new Task("Task Title", "Task Description");
        taskManager.createTask(task);
        Epic epic = new Epic("Epic Title", "Epic Description");
        taskManager.createEpic(epic);
        Subtask subtask = new Subtask("Subtask Title", "Subtask Description", epic.getId());
        taskManager.createSubtask(subtask);
        Task foundTask = taskManager.getById(task.getId());
        assertNotNull(foundTask, "Задача должна быть найдена по ID.");
        assertEquals(task.getTitle(), foundTask.getTitle(), "Заголовок найденной задачи должен совпадать.");
        Epic foundEpic = (Epic) taskManager.getById(epic.getId());
        assertNotNull(foundEpic, "Эпик должен быть найден по ID.");
        assertEquals(epic.getTitle(), foundEpic.getTitle(), "Заголовок найденного эпика должен совпадать.");
        Subtask foundSubtask = (Subtask) taskManager.getById(subtask.getId());
        assertNotNull(foundSubtask, "Подзадача должна быть найдена по ID.");
        assertEquals(subtask.getTitle(), foundSubtask.getTitle(), "Заголовок найденной подзадачи должен совпадать.");
    }

    @Test
    void testTaskUnchangedAfterAdding() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager(Managers.getDefaultHistory());
        Task consttask = new Task("Constant Task Title", "Constant Task Description");
        taskManager.createTask(consttask);
        Task addedTask = taskManager.getById(consttask.getId());
        assertEquals(consttask, addedTask, "Задача должна оставаться неизменной после вставки.");
    }

    @Test
    void testCreateTask() {
        Task task = new Task("Task 1", "Description 1");
        taskManager.createTask(task);
        assertEquals(1, taskManager.getAllTasks().size(), "Должна быть одна задача");
    }

    @Test
    void testCreateSubtask() {
        Epic epic = new Epic("Epic 1", "Epic Description");
        taskManager.createEpic(epic);
        Subtask subtask = new Subtask("Subtask 1", "Subtask Description", epic.getId());
        assertTrue(taskManager.createSubtask(subtask), "Subtask должен быть успешно создан");
        assertEquals(1, taskManager.getAllSubtasks().size(), "Должна быть одна подзадача");
    }

    @Test
    void testCreateEpic() {
        Epic epic = new Epic("Epic 1", "Epic Description");
        taskManager.createEpic(epic);
        assertEquals(1, taskManager.getAllEpics().size(), "Должен быть один эпик");
    }

    @Test
    void testGetById() {
        Task task = new Task("Task 1", "Description 1");
        taskManager.createTask(task);
        Task retrievedTask = taskManager.getById(task.getId());
        assertEquals(task.getId(), retrievedTask.getId(), "Должен быть возвращен правильный ID задачи");
    }

    @Test
    void testUpdateTask() {
        Task task = new Task("Task 1", "Description 1");
        taskManager.createTask(task);
        task.setDescription("Updated Description");
        assertTrue(taskManager.updateTask(task), "Задача должна быть успешно обновлена");
        assertEquals("Updated Description", taskManager.getById(task.getId()).getDescription(), "Описание должно быть обновлено");
    }

    @Test
    void testUpdateSubtask() {
        Epic epic = new Epic("Epic 1", "Epic Description");
        taskManager.createEpic(epic);
        Subtask subtask = new Subtask("Subtask 1", "Subtask Description", epic.getId());
        taskManager.createSubtask(subtask);
        subtask.setDescription("Updated Subtask Description");
        assertTrue(taskManager.updateSubtask(subtask), "Подзадача должна быть успешно обновлена");
        assertEquals("Updated Subtask Description", taskManager.getSubtasksByEpicId(epic.getId()).get(0).getDescription(), "Описание подзадачи должно быть обновлено");
    }

    @Test
    void testUpdateEpic() {
        Epic epic = new Epic("Epic 1", "Epic Description");
        taskManager.createEpic(epic);
        epic.setDescription("Updated Epic Description");
        assertTrue(taskManager.updateEpic(epic), "Эпик должен быть успешно обновлен");
        assertEquals("Updated Epic Description", taskManager.getAllEpics().get(0).getDescription(), "Описание эпика должно быть обновлено");
    }

    @Test
    void testRemoveTaskById() {
        Task task = new Task("Task 1", "Description 1");
        taskManager.createTask(task);
        taskManager.removeTaskById(task.getId());
        assertTrue(taskManager.getAllTasks().isEmpty(), "Задача должна быть удалена");
    }

    @Test
    void testRemoveSubtaskById() {
        Epic epic = new Epic("Epic 1", "Epic Description");
        taskManager.createEpic(epic);
        Subtask subtask = new Subtask("Subtask 1", "Subtask Description", epic.getId());
        taskManager.createSubtask(subtask);
        taskManager.removeSubtaskById(subtask.getId());
        assertTrue(taskManager.getAllSubtasks().isEmpty(), "Подзадача должна быть удалена");
    }

    @Test
    void testRemoveEpicById() {
        Epic epic = new Epic("Epic 1", "Epic Description");
        taskManager.createEpic(epic);
        taskManager.removeEpicById(epic.getId());
        assertTrue(taskManager.getAllEpics().isEmpty(), "Эпик должен быть удален");
    }

    @Test
    void testGetAllTasks() {
        Task task1 = new Task("Task 1", "Description 1");
        Task task2 = new Task("Task 2", "Description 2");
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        assertEquals(2, taskManager.getAllTasks().size(), "Должны быть две задачи");
    }

    @Test
    void testGetAllSubtasks() {
        Epic epic = new Epic("Epic 1", "Epic Description");
        taskManager.createEpic(epic);
        Subtask subtask1 = new Subtask("Subtask 1", "Subtask Description", epic.getId());
        Subtask subtask2 = new Subtask("Subtask 2", "Subtask Description", epic.getId());
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);
        assertEquals(2, taskManager.getAllSubtasks().size(), "Должны быть две подзадачи");
    }

    @Test
    void testGetAllEpics() {
        Epic epic1 = new Epic("Epic 1", "Epic Description");
        Epic epic2 = new Epic("Epic 2", "Epic Description");
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);
        assertEquals(2, taskManager.getAllEpics().size(), "Должны быть два эпика");
    }

    @Test
    void testGetSubtasksByEpicId() {
        Epic epic = new Epic("Epic 1", "Epic Description");
        taskManager.createEpic(epic);
        Subtask subtask = new Subtask("Subtask 1", "Subtask Description", epic.getId());
        taskManager.createSubtask(subtask);
        assertEquals(1, taskManager.getSubtasksByEpicId(epic.getId()).size(), "Должна быть одна подзадача для данного эпика");
    }

    @Test
    void testGetHistory() {
        Task task1 = new Task("Task 1", "Description 1");
        Task task2 = new Task("Task 2", "Description 2");
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.getById(task1.getId());
        taskManager.getById(task2.getId());
        ArrayList<Task> history = taskManager.getHistory();
        assertEquals(2, history.size(), "История должна содержать две задачи");
        assertEquals(task1.getId(), history.get(0).getId(), "Первая задача в истории должна быть Task 1");
        assertEquals(task2.getId(), history.get(1).getId(), "Вторая задача в истории должна быть Task 2");
    }
}