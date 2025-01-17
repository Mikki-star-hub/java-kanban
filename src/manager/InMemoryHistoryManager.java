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

        Node(Task task) {
            this.task = task;
        }
    }

    private static final int MAX_HISTORY_SIZE = 10;
    private final HashMap<Integer, Node> nodeMap = new HashMap<>();
    private Node head;
    private Node tail;


    private void removeFirst() {
        if (head != null) {
            removeNode(head);
        }
    }

    @Override
    public void add(Task task) {
        if (task == null) return;
        if (nodeMap.containsKey(task.getId())) {
            removeNode(nodeMap.get(task.getId()));
        }
        linkLast(task);
        if (nodeMap.size() > MAX_HISTORY_SIZE) {
            removeFirst();
        }
    }

    @Override
    public void remove(int id) {
        if (nodeMap.containsKey(id)) {
            removeNode(nodeMap.get(id));
        }
    }


    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    private void linkLast(Task task) {
        Node newNode = new Node(task);

        if (tail != null) {
            tail.next = newNode;
            newNode.prev = tail;
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
