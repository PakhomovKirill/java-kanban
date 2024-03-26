package manager.history;

import java.util.ArrayList;
import task.Task;

public class DoubleLinkedList<T extends Task> {

    private Node<T> headOfList;
    private Node<T> tailOfList;
    private int sizeList;

    private boolean isEmptyList() {
        return sizeList == 0;
    }

    public Node addLastToList(T task) {
        final Node<T> newNode = new Node<>(tailOfList, task, null);

        if (headOfList == null) {
            headOfList = newNode;
        } else {
            tailOfList.next = newNode;
        }

        tailOfList = newNode;
        sizeList++;

        return newNode;
    }

    public void removeFromList(Node node) {
        if (node == null) {
            return;
        }

        Node prev = node.prev;
        Node next = node.next;

        if (prev == null) {
            headOfList = next;
        } else {
            prev.next = next;
            node.prev = null;
        }

        if (next == null) {
            tailOfList = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }

        node.data = null;
        sizeList--;
    }

    public ArrayList<Task> getList() {
        Node current = headOfList;
        ArrayList<Task> historyList = new ArrayList<>();

        while (current != null) {
            historyList.add(current.data);
            current = current.next;
        }

        return historyList;
    }

    public int getSize() {
        return sizeList;
    }
}
