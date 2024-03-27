import manager.Managers;
import manager.history.DoubleLinkedList;
import manager.history.Node;
import manager.task.TaskManager;
import org.junit.jupiter.api.Test;
import task.Task;
import utils.Enums;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class InMemoryHistoryManagerTest {

    private static Managers manager = new Managers();

    private static TaskManager tm = manager.getDefault();

    @Test
    public void IsUniqueTaskInHistoryIdTest(){
        Task task1 = new Task("Task #1", "Task1 description", Enums.TaskStatus.NEW);
        final int taskId1 = tm.addNewTask(task1);
        tm.getTask(taskId1);

        Task task2 = new Task("Task #1 updated", "Task1 description updated", Enums.TaskStatus.NEW, taskId1);
        tm.updateTask(task2);
        tm.getTask(taskId1);

        ArrayList<Task> historyList = tm.getHistory();
        assertNotEquals(historyList.size(), 2, "Лист не может содержать 2 обектов с идентичным id");
    }

    @Test
    public void RemoveItemFromHistoryList(){
        Task task1 = new Task("Task #1", "Task1 description", Enums.TaskStatus.NEW);
        final int taskId1 = tm.addNewTask(task1);
        tm.getTask(taskId1);

        assertNotNull(tm.getHistory().get(0),"Задача не добавлена в историю");

        tm.removeHistoryTask(taskId1);

        ArrayList<Task> historyList = tm.getHistory();

        assertNotEquals(1,historyList.size(),"Задача не удалена из истории");
    }

    @Test
    public void RemoveItemFromLinkedListTest(){
        Task task1 = new Task("Task #1", "Task1 description", Enums.TaskStatus.NEW);
        final int taskId1 = tm.addNewTask(task1);

        HashMap<Integer, Node> nodeList = new HashMap<>();
        DoubleLinkedList linkedList = new DoubleLinkedList();

        Node addNode = linkedList.addLastToList(task1);
        nodeList.put(taskId1, addNode);

        assertNotNull(linkedList,"Узел не добавлен в список");

        Node removedNode = nodeList.remove(taskId1);
        linkedList.removeFromList(removedNode);

        assertNotEquals(1, linkedList.getSize(),"Узел не удален из списка");
    }

    @Test
    public void TasksDoublesInHistoryTest(){
        Task task1 = new Task("Task #1", "Task1 description", Enums.TaskStatus.NEW);

        final int taskId1 = tm.addNewTask(task1);
        tm.getTask(taskId1);
        tm.getTask(taskId1);
        assertNotEquals(2, tm.getHistory().size(), "В истории просмотров не должно быть дублирующихся тасков");
    }
}