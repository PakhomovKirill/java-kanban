package tests;

import manager.Managers;
import manager.history.DoubleLinkedList;
import manager.history.Node;
import manager.task.TaskManager;
import org.junit.jupiter.api.Test;
import task.Task;
import utils.Enums;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    private static Managers Manager = new Managers();

    private static TaskManager TM = Manager.getDefault();

    @Test
    public void IsUniqueTaskInHistoryIdTest(){
        Task task1 = new Task("Task #1", "Task1 description", Enums.TaskStatus.NEW);
        final int taskId1 = TM.addNewTask(task1);
        TM.getTask(taskId1);

        Task task2 = new Task("Task #1 updated", "Task1 description updated", Enums.TaskStatus.NEW, taskId1);
        TM.updateTask(task2);
        TM.getTask(taskId1);

        ArrayList<Task> historyList = TM.getHistory();
        assertNotEquals(historyList.size(), 2, "Лист не может содержать 2 обектов с идентичным id");
    }

    @Test
    public void RemoveItemFromHistoryList(){
        Task task1 = new Task("Task #1", "Task1 description", Enums.TaskStatus.NEW);
        final int taskId1 = TM.addNewTask(task1);
        TM.getTask(taskId1);

        assertNotNull(TM.getHistory().get(0),"Задача не добавлена в историю");

        TM.removeHistoryTask(taskId1);

        ArrayList<Task> historyList = TM.getHistory();

        assertNotEquals(1,historyList.size(),"Задача не удалена из истории");
    }

    @Test
    public void RemoveItemFromLinkedListTest(){
        Task task1 = new Task("Task #1", "Task1 description", Enums.TaskStatus.NEW);
        final int taskId1 = TM.addNewTask(task1);

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

        final int taskId1 = TM.addNewTask(task1);
        TM.getTask(taskId1);
        TM.getTask(taskId1);

        assertNotEquals(2, TM.getHistory().size(), "В истории просмотров не должно быть дублирующихся тасков");
    }
}