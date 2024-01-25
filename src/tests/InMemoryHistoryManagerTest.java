package tests;

import manager.Managers;
import manager.task.TaskManager;
import org.junit.jupiter.api.Test;
import task.Task;
import utils.Enums;

import java.util.ArrayList;

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

        assertNotEquals(historyList.get(0), historyList.get(1), "Задачи не должны быть равны равны.");
    }

}