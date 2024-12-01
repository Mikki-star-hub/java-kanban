package manager;

import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final ArrayList<Task> history = new ArrayList<>();
    private final HistoryManager historyManager;
    private int idCounter = 0;

    public InMemoryTaskManager(HistoryManager historyManager) {
        this.historyManager = historyManager;
    }

    private int generateId() {
        return ++idCounter;
    }

    @Override
    public void createTask(Task task) {
        task.setId(generateId());
        tasks.put(task.getId(), task);
    }

    @Override
    public void createSubtask(Subtask subtask) {
        if (epics.containsKey(subtask.getEpicId())) {
            subtask.setId(generateId());
            subtasks.put(subtask.getId(), subtask);
            epics.get(subtask.getEpicId()).addSubtask(subtask.getId());
            updateEpicStatus(epics.get(subtask.getEpicId()));
        }
    }

    @Override
    public void createEpic(Epic epic) {
        epic.setId(generateId());
        epics.put(epic.getId(), epic);
    }

    @Override
    public Task getById(int id) {
        Task task = tasks.get(id);
        if (task != null) {
            historyManager.add(task);
            return task;
        }
        Subtask subtask = subtasks.get(id);
        if (subtask != null) {
            historyManager.add(subtask);
            return subtask;
        }
        return epics.get(id);
    }

    @Override
    public boolean updateTask(Task task) {
        Task savedTask = tasks.get(task.getId());
        if (savedTask == null) {
            return false;
        }
        savedTask.setTitle(task.getTitle());
        savedTask.setDescription(task.getDescription());
        savedTask.setStatus(task.getStatus());
        return true;
    }

    @Override
    public boolean updateSubtask(Subtask subtask) {
        Subtask savedSubtask = subtasks.get(subtask.getId());
        if (savedSubtask == null) {
            return false;
        }
        if (epics.containsKey(savedSubtask.getEpicId())) {
            savedSubtask.setTitle(subtask.getTitle());
            savedSubtask.setDescription(subtask.getDescription());
            savedSubtask.setStatus(subtask.getStatus());
            Epic epic = epics.get(savedSubtask.getEpicId());
            if (epic != null) {
                updateEpicStatus(epic);
            }
        }
        return true;
    }

    @Override
    public boolean updateEpic(Epic epic) {
        Epic savedEpic = epics.get(epic.getId());
        if (savedEpic == null) {
            return false;
        }
        savedEpic.setTitle(epic.getTitle());
        savedEpic.setDescription(epic.getDescription());
        return true;
    }

    @Override
    public void removeTaskById(int id) {
        tasks.remove(id);
    }

    @Override
    public void removeSubtaskById(int id) {
        Subtask subtask = subtasks.remove(id);
        if (subtask == null) {
            return;
        }
        Epic epic = epics.get(subtask.getEpicId());
        if (epic != null) {
            epic.removeSubtaskId(id);
            updateEpicStatus(epic);
        }
    }

    @Override
    public void removeEpicById(int id) {
        Epic epic = epics.remove(id);
        if (epic == null) {
            return;
        }
        for (Integer subtaskId : epic.getSubtaskIds()) {
            subtasks.remove(subtaskId);
        }
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public ArrayList<Subtask> getSubtasksByEpicId(int epicId) {
        ArrayList<Subtask> epicSubtasks = new ArrayList<>();
        Epic epic = epics.get(epicId);
        if (epic != null) {
            for (int subtaskId : epic.getSubtaskIds()) {
                Subtask subtask = subtasks.get(subtaskId);
                if (subtask != null) {
                    epicSubtasks.add(subtask);
                }
            }
        }
        return epicSubtasks;
    }

    private void updateEpicStatus(Epic epic) {
        boolean allDone = true;
        boolean allNew = true;

        for (Integer subtaskId : epic.getSubtaskIds()) {
            Subtask subtask = subtasks.get(subtaskId);
            if (subtask != null) {
                if (subtask.getStatus() != Status.DONE) {
                    allDone = false;
                }
                if (subtask.getStatus() != Status.NEW) {
                    allNew = false;
                }
            }
        }

        if (allNew) {
            epic.setStatus(Status.NEW);
        } else if (allDone) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }

    private void addToHistory(Task task) {
        if (history.size() == 10) {
            history.remove(0);
        }
        history.add(task);
    }

    @Override
    public ArrayList<Task> getHistory() {
        return historyManager.getHistory();
    }
}
