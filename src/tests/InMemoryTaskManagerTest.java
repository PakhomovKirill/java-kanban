package tests;

import static org.junit.jupiter.api.Assertions.*;

import manager.Managers;
import manager.task.InMemoryTaskManager;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.Subtask;
import task.Task;
import utils.Enums;

class InMemoryTaskManagerTest {

    private static Managers Manager = new Managers();
    private static InMemoryTaskManager TM = Manager.getDefaultClass();

    @Test
    public void  IsTaskManagerCreateTaskTest(){
        Task task1 = new Task("Task #1", "Task1 description", Enums.TaskStatus.NEW);
        final int task1ID = TM.addNewTask(task1);
        assertNotNull(task1ID, "ID не должен равняться null");

        Epic epic1 = new Epic("Epic #1", "Epic1 description");
        final int epic1ID = TM.addNewEpic(epic1);
        assertNotNull(epic1ID, "ID не должен равняться null");

        Subtask subtask1 = new Subtask("Subtask #1", "Subtask1 description", Enums.TaskStatus.NEW, epic1ID);
        final int subtask1ID = TM.addNewSubtask(subtask1);
        assertNotNull(subtask1ID, "ID не должен равняться null");
    }
}