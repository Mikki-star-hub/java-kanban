import manager.TaskManager;
import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        Task task1 = new Task("Переезд ", "Переезд в новую квартиру ");
        taskManager.createTask(task1);

        Task task2 = new Task("День рождения !", "Собрать друзей на День рождения ");
        taskManager.createTask(task2);

        Epic epic1 = new Epic("Бизнес ", "Открыть бизнес ");
        taskManager.createEpic(epic1);

        Subtask subtask1 = new Subtask("Выбрать товар", "Определиться с товаром ", epic1.getId());
        taskManager.createSubtask(subtask1);

        Subtask subtask2 = new Subtask("Закупить товар ", "Поехать на рынок и купить товар ", epic1.getId());
        taskManager.createSubtask(subtask2);

        Epic epic2 = new Epic("Уборка дома ", "Навести порядок и чистоту ");
        taskManager.createEpic(epic2);

        Subtask subtask3 = new Subtask("Помыть полы ", "Сделать влажную уборку дома ", epic2.getId());
        taskManager.createSubtask(subtask3);

        System.out.println("Все задачи: " + taskManager.getAllTasks());
        System.out.println("Все подзадачи: " + taskManager.getAllSubtasks());
        System.out.println("Все эпики: " + taskManager.getAllEpics());

        task1.setStatus(Status.IN_PROGRESS);
        subtask1.setStatus(Status.DONE);
        subtask2.setStatus(Status.IN_PROGRESS);
        subtask3.setStatus(Status.IN_PROGRESS);

        System.out.println("Статусы после изменений:");
        System.out.println(task1.getStatus());
        System.out.println(subtask1.getStatus());
        System.out.println(subtask2.getStatus());
        System.out.println(subtask3.getStatus());

        taskManager.removeTaskById(task1.getId());
        taskManager.removeEpicById(epic1.getId());


        System.out.println("После удаления:");
        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpics());
    }
}
