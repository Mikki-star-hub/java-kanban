import manager.InMemoryTaskManager;
import manager.Managers;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

class EpicTest {
    private InMemoryTaskManager taskManager = new InMemoryTaskManager(Managers.getDefaultHistory());

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
    void testEpicEqualityWithSameId() {
        Epic epic1 = new Epic( "Epic One", "Description One");
        Epic epic2 = new Epic( "Epic Two", "Description Two");
        assertEquals(epic1, epic2, "Эпики с одинаковыми ID должны считаться равными.");
    }
}