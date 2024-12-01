package manager;

import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryHistoryManager implements HistoryManager {
    private final HashMap<Integer, Task> history = new HashMap<>();
    private final ArrayList<Integer> order = new ArrayList<>();

    @Override
    public void add(Task task) {
        if (task != null) {
            if (history.containsKey(task.getId())) {
                order.remove(Integer.valueOf(task.getId()));
            } else if (history.size() >= 10) {

                Integer firstId = order.remove(0);
                history.remove(firstId);
            }
            history.put(task.getId(), task);
            order.add(task.getId());
        }
    }

    @Override
    public ArrayList<Task> getHistory() {
        ArrayList<Task> result = new ArrayList<>();
        for (Integer id : order) {
            result.add(history.get(id));
        }
        return result;
    }
}
