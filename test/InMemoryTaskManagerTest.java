import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import manager.Managers;
import manager.task.TaskManager;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.Subtask;
import task.Task;
import utils.Enums;

import java.time.Duration;

class InMemoryTaskManagerTest {

    private static Managers manager = new Managers();
    private static TaskManager tm = manager.getDefault();

    @Test
    public void  IsTaskManagerCreateTaskTest(){
        Task task1 = new Task("Task #1", "Task1 description", Enums.TaskStatus.NEW);
        final int task1ID = tm.addNewTask(task1);
        assertNotNull(task1ID, "ID не должен равняться null");

        Epic epic1 = new Epic("Epic #1", "Epic1 description");
        final int epic1ID = tm.addNewEpic(epic1);
        assertNotNull(epic1ID, "ID не должен равняться null");

        Subtask subtask1 = new Subtask("Subtask #1", "Subtask1 description", Enums.TaskStatus.NEW, epic1ID);
        final int subtask1ID = tm.addNewSubtask(subtask1);
        assertNotNull(subtask1ID, "ID не должен равняться null");
    }

    @Test
    public void  IsTaskManagerCreateEpicTest(){
        Epic epic1 = new Epic("Task #1", "Task1 description");
        final int epic1ID = tm.addNewEpic(epic1);
        Subtask subtask1 = new Subtask("Subtask #1", "Subtask description", Enums.TaskStatus.NEW, epic1ID);
        Subtask subtask2 = new Subtask("Subtask #1", "Subtask description", Enums.TaskStatus.NEW, epic1ID);
        tm.addNewSubtask(subtask1);
        tm.addNewSubtask(subtask2);

        assertEquals(tm.getEpics().get(0).getStatus().toString(), Enums.TaskStatus.NEW.toString(), "Статус не соответствует нужному значению");

        Epic epic2 = new Epic("Task #1", "Task1 description");
        final int epic2ID = tm.addNewEpic(epic2);
        Subtask subtask3= new Subtask("Subtask #2", "Subtask description", Enums.TaskStatus.IN_PROGRESS, epic2ID);
        Subtask subtask4 = new Subtask("Subtask #2", "Subtask description", Enums.TaskStatus.IN_PROGRESS, epic2ID);
        tm.addNewSubtask(subtask3);
        tm.addNewSubtask(subtask4);

        assertEquals(tm.getEpics().get(1).getStatus().toString(), Enums.TaskStatus.IN_PROGRESS.toString(), "Статус не соответствует нужному значению");

        Epic epic3 = new Epic("Task #1", "Task1 description");
        final int epic3ID = tm.addNewEpic(epic3);
        Subtask subtask5 = new Subtask("Subtask #3", "Subtask description", Enums.TaskStatus.DONE, epic3ID);
        Subtask subtask6 = new Subtask("Subtask #3", "Subtask description", Enums.TaskStatus.DONE, epic3ID);
        tm.addNewSubtask(subtask5);
        tm.addNewSubtask(subtask6);

        assertEquals(tm.getEpics().get(2).getStatus().toString(), Enums.TaskStatus.DONE.toString(), "Статус не соответствует нужному значению");
    }

    @Test
    public void  IsTaskManagerCreateTimestampTest(){
        Task task1 = new Task("Task #1", "Task1 description", Enums.TaskStatus.NEW, "12.03.24 13:30", Duration.ofHours(2));
        Task task2 = new Task("Task #2", "Task2 description", Enums.TaskStatus.IN_PROGRESS, "12.03.24 12:30", Duration.ofHours(2));

        tm.addNewTask(task1);
        tm.addNewTask(task2);

        assertNotEquals(2, tm.getPrioritizedTasks().size(), "Таск с пересечением по времени не должен быть добавлен");
    }
}