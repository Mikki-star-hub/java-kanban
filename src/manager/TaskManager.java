package manager;

import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private int idCounter = 0;

    private int generateId() {
        return ++idCounter;
    }

    public void createTask(Task task) {
        task.setId(generateId());
        tasks.put(task.getId(), task);
    }

    public void createSubtask(Subtask subtask) {
        subtask.setId(generateId());
        subtasks.put(subtask.getId(), subtask);
        if (epics.containsKey(subtask.getEpicId())) {
            epics.get(subtask.getEpicId()).addSubtask(subtask.getId());
            updateEpicStatus(epics.get(subtask.getEpicId()));
        }
    }

    public void createEpic(Epic epic) {
       epic.setId(generateId());
        epics.put(epic.getId(), epic);
    }

    public Task getById(int id) {
        Task task = tasks.get(id);
        if (task != null) {
            return task;
        } else {
            task = subtasks.get(id);
            if (task != null) {
                return task;
            } else {
                return epics.get(id);
            }
        }
    }

    public boolean updateTask(Task task) {
        Task task1 = tasks.get(task.getId());
        if (task1 == null) {
            return false;
        }
        task1.setTitle(task.getTitle());
        task1.setDescription(task.getDescription());
        task.setStatus(task.getStatus());
        return true;
    }


    public boolean updateSubtask(Subtask subtask) {
        Subtask subtask1 = subtasks.get(subtask.getId());
        if (subtask1 == null) {
            return false;
        }
        subtask1.setTitle(subtask.getTitle());
        subtask1.setDescription(subtask.getDescription());
        subtask1.setStatus(subtask.getStatus());
        Epic epic = epics.get(subtask1.getEpicId());
        if (epic != null) {
            updateEpicStatus(epic);
        }
        return true;
    }

    public boolean updateEpic(Epic epic) {
        Epic epic1 = epics.get(epic.getId());
        if (epic1 == null) {
            return false;
        }
        epic1.setTitle(epic.getTitle());
        epic1.setDescription(epic.getDescription());
        epic1.setStatus(epic.getStatus());
        return true;
    }

    public void removeTaskById(int id) {
        tasks.remove(id);
    }

    public void removeSubtaskById(int id) {
        Subtask subtask = subtasks.remove(id);
        if (subtask != null) {
            Epic epic = epics.get(subtask.getEpicId());
            if (epic != null) {
                epic.getSubtaskIds().remove(Integer.valueOf(id));
                updateEpicStatus(epic);
            }
        }
    }

    public void removeEpicById(int id) {
        Epic epic = epics.remove(id);
        if (epic != null) {
            for (Integer subtaskId : epic.getSubtaskIds()) {
                subtasks.remove(subtaskId);
            }
        }
    }

    public void removeAllTasks() {
        tasks.clear();
    }

    public void removeAllSubtasks() {
        for (Subtask subtask : subtasks.values()) {
            Epic epic = epics.get(subtask.getEpicId());
            if (epic != null) {
                epic.getSubtaskIds().remove(Integer.valueOf(subtask.getId()));
                updateEpicStatus(epic);
            }
        }
        subtasks.clear();
    }

    public void removeAllEpics() {
        for (Epic epic : epics.values()) {
            for (Integer subtaskId : epic.getSubtaskIds()) {
                subtasks.remove(subtaskId);
            }
        }
        epics.clear();
    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }


    private void updateEpicStatus(Epic epic) {
        boolean allDone = true;
        boolean allNew = false;

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
}



