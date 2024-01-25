package tests;

import manager.Managers;
import manager.task.TaskManager;
import org.junit.jupiter.api.Test;
import task.Task;
import task.Epic;
import task.Subtask;
import utils.Enums;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskTest {

    private static Managers Manager = new Managers();
    private static TaskManager TM = Manager.getDefault();

    @Test
    public void IsEqualTasksTest(){
        Task task1 = new Task("Task #1", "Task1 description", Enums.TaskStatus.NEW, 1);
        Task task2 = new Task("Task #1", "Task1 description", Enums.TaskStatus.NEW, 1);

        assertEquals(task2, task1, "Задачи не равны.");
    }

    @Test
    public void IsEqualEpicTest(){
        Epic task1 = new Epic("Epic #1", "Epic1 description", 1);
        Epic task2 = new Epic("Epic #1", "Epic1 description", 1);

        assertEquals(task2, task1, "Задачи не равны.");
    }

    @Test
    public void IsEqualSubtaskTest(){
        Subtask task1 = new Subtask("Subtask #1", "Subtask1 description", Enums.TaskStatus.NEW, 1);
        Subtask task2 = new Subtask("Subtask #1", "Subtask1 description", Enums.TaskStatus.NEW, 1);

        assertEquals(task2, task1, "Задачи не равны.");
    }

    @Test
    public void IsEpicNotNullTest(){
        // крайне неверно описанный в тз тесткейс, ментор описал следующим образом:

        // достаточно добавить проверку на то что если в объекте сабтаски добавляемой в
        // эпик передать в качестве epic_id число которого нет в хранилище эпиков, т.е. id несуществующего эпика, то в эпик ничего не будет добавлено.

        Subtask subtask1 = new Subtask("Subtask #1-1", "Subtask1 description", Enums.TaskStatus.NEW, 0);

        Integer subtask1ID = TM.addNewSubtask(subtask1);

        assertNull(subtask1ID, "Айди сабтаска не равен null");
        // assertNotNull(subtask1ID, "Айди сабтаска равен null"); //--> вернет exception;
    }

    @Test
    public void IsSubtaskNotNullTest(){
        // крайне неверно описанный в тз тесткейс, ментор описал следующим образом:

        // при редактировании сабтаски, должны быть проверки что id редактируемой сабтаски есть в хранилище сабтасок и что epic_id
        // редактируемой сабтаски это id существующего в хранилище эпиков эпика, а не какое-то случайное число

        Subtask subtask1 = new Subtask("Subtask #1-1", "Subtask1 description", Enums.TaskStatus.NEW, 0);

        Integer subtask1ID = TM.updateSubtask(subtask1);

        assertNull(subtask1ID, "Айди сабтаска не равен null");
        //assertNotNull(subtask1ID, "Айди сабтаска равен null"); // --> вернет exception;
    }

    @Test
    public void IsUniqueTaskIdTest(){
        // крайне неверно описанный в тз тесткейс, ментор описал следующим образом:

        // нужно проверять что при сохранении новых сущностей они сохраняются со сгенерированным id, а не с id который приходит в объекте изначально
        // (т.к. такие ситуации могут привести к конфликтам и нарушению уникальности. Например у нас уже было создано 123 таски,
        // а мы в addTask передаём объект Task с id=6, если мы не перезапишем id с 6 на генерируемое значение,
        // то у нас вместо добавления новой таски, перезапишутся данные старой уже существовавшей таски с id=6, а это не то чего мы хотим)

        int sameId = 0;

        Task task1 = new Task("Task #1", "Task1 description", Enums.TaskStatus.NEW, sameId);
        final int taskId1 = TM.addNewTask(task1);

        assertNotEquals(taskId1, sameId, "Айди не должны совпадать и соответствовать sameId");

        Epic epic1 = new Epic("Task #1", "Task1 description", sameId);
        final int epicId1 = TM.addNewEpic(epic1);

        assertNotEquals(epicId1, sameId, "Айди не должны совпадать и соответствовать sameId");

        Subtask subtask1 = new Subtask("Task #1", "Task1 description", Enums.TaskStatus.NEW, epicId1, sameId);
        final int subtaskId1 = TM.addNewSubtask(subtask1);

        assertNotEquals(subtaskId1, sameId, "Айди не должны совпадать и соответствовать sameId");
    }


}