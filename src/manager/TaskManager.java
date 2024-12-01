package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;

public interface TaskManager {
    void createTask(Task task);
    void createSubtask(Subtask subtask);
    void createEpic(Epic epic);

    Task getById(int id);

    boolean updateTask(Task task);
    boolean updateSubtask(Subtask subtask);
    boolean updateEpic(Epic epic);

    void removeTaskById(int id);
    void removeSubtaskById(int id);
    void removeEpicById(int id);

    ArrayList<Task> getAllTasks();
    ArrayList<Subtask> getAllSubtasks();
    ArrayList<Epic> getAllEpics();

    ArrayList<Subtask> getSubtasksByEpicId(int epicId);
    ArrayList<Task> getHistory();
}


