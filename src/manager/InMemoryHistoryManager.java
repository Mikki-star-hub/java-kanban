package manager;

import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class InMemoryHistoryManager implements HistoryManager {

    private static class Node {
        Task task;
        Node next;
        Node prev;

        Node(Task task, Node prev, Node next) {
            this.task = task;
            this.prev = prev;
            this.next = next;
        }
    }

    private final HashMap<Integer, Node> nodeMap = new HashMap<>();
    private Node head;
    private Node tail;

    @Override
    public void add(Task task) {
        removeNode(nodeMap.get(task.getId()));
        linkLast(task);
    }

    @Override
    public void remove(int id) {
        removeNode(nodeMap.get(id));
    }


    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    private void linkLast(Task task) {
        Node newNode = new Node(task, tail, null);

        if (tail != null) {
            tail.next = newNode;
        } else {
            head = newNode;
        }
        tail = newNode;

        nodeMap.put(task.getId(), newNode);
    }


    private void removeNode(Node node) {
        if (node == null) return;

        Node prevNode = node.prev;
        Node nextNode = node.next;

        if (prevNode != null) {
            prevNode.next = nextNode;
        } else {
            head = nextNode;
        }

        if (nextNode != null) {
            nextNode.prev = prevNode;
        } else {
            tail = prevNode;
        }

        nodeMap.remove(node.task.getId());
    }

    // Собирает все задачи из связного списка в ArrayList
    private List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        Node current = head;
        while (current != null) {
            tasks.add(current.task);
            current = current.next;
        }
        return tasks;
    }
}
