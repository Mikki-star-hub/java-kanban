import manager.InMemoryTaskManager;
import manager.Managers;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {
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
    void testSubtaskCannotBeItsOwnEpic() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager(Managers.getDefaultHistory());
        Epic epic = new Epic("Epic Title", "Epic Description");
        taskManager.createEpic(epic);
        Subtask subtask = new Subtask("Subtask Title", "Subtask Description", epic.getId());
        subtask.setId(epic.getId()); // Устанавливаем идентификатор подзадачи равным идентификатору эпика
        boolean result = taskManager.createSubtask(subtask);
        assertFalse(result, "Подзадача не должна быть создана, если она ссылается на свой же эпик.");
    }
}