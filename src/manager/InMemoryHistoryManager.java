package manager;

import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryHistoryManager implements HistoryManager {

    private static final int MAX_HISTORY_SIZE = 10; // Константа для максимального размера истории
    private final ArrayList<Task> history = new ArrayList<>();

    @Override
    public void add(Task task) {
        if (task != null) {
            history.remove(task);
            history.add(task);
            if (history.size() >= MAX_HISTORY_SIZE) {
                history.remove(0);
            }
        }
    }

    @Override
    public ArrayList<Task> getHistory() {
        return new ArrayList<>(history);
    }
}
