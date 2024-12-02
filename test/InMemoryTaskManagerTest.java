import manager.InMemoryTaskManager;
import manager.Managers;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class InMemoryTaskManagerTest {

    @Test
    void testAddAndFindTasksById() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager(Managers.getDefaultHistory());
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
}