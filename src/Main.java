import manager.TaskManager;
import task.Epic;
import task.Subtask;
import task.Task;
import utils.Enums;
public class Main {

    private static TaskManager TM;
    public static void main(String[] args) {
        TM = new TaskManager();

        create();
    }

    private static void printAllTasks(TaskManager manager) {
        System.out.println("Задачи:");
        for (Task task : manager.getTasks()) {
            System.out.println(task.toString());
        }
        System.out.println("Эпики:");
        for (Task epic : manager.getEpics()) {
            System.out.println(epic.toString());
            System.out.println("--> Подзадачи эпика:");
            for (Task task : manager.getEpicSubtasks(epic.getId())) {
                System.out.println("--> " + task.toString());
            }
        }
        System.out.println("Подзадачи:");
        for (Task subtask : manager.getSubtasks()) {
            System.out.println(subtask.toString());
        }
    }

    private static void create(){
        // Создание
        System.out.println("" + '\n' + '\n' +  "Создание " + '\n' + '\n');

        Task task1 = new Task("Task #1", "Task1 description", Enums.TaskStatus.NEW);
        Task task2 = new Task("Task #2", "Task2 description", Enums.TaskStatus.IN_PROGRESS);
        final int taskId1 = TM.addNewTask(task1);
        final int taskId2 = TM.addNewTask(task2);

        Epic epic1 = new Epic("Epic #1", "Epic1 description");
        Epic epic2 = new Epic("Epic #2", "Epic2 description");
        Epic epic3 = new Epic("Epic #3", "Epic3 description");
        final int epicId1 = TM.addNewEpic(epic1);
        final int epicId2 = TM.addNewEpic(epic2);
        final int epicId3 = TM.addNewEpic(epic3);

        Subtask subtask1 = new Subtask("Subtask #1-1", "Subtask1 description", Enums.TaskStatus.NEW, epicId1);
        Subtask subtask2 = new Subtask("Subtask #2-1", "Subtask1 description", Enums.TaskStatus.IN_PROGRESS, epicId1);
        Subtask subtask3 = new Subtask("Subtask #3-2", "Subtask1 description", Enums.TaskStatus.NEW, epicId2);

        final Integer subtaskId1 = TM.addNewSubtask(subtask1);
        final Integer subtaskId2 = TM.addNewSubtask(subtask2);
        final Integer subtaskId3 = TM.addNewSubtask(subtask3);

        printAllTasks(TM);

        //Обновление
        System.out.println("" + '\n' + '\n' + "Обновление " + '\n' + '\n');

        Task task3 = new Task("Task #1 updated", "Task1 description update-1", Enums.TaskStatus.NEW, taskId1);
        Task task4 = new Task("Task #2 updated", "Task2 description update-2", Enums.TaskStatus.IN_PROGRESS, taskId2);
        final int taskId3 = TM.updateTask(task3);
        final int taskId4 = TM.updateTask(task4);

        Epic epic1UPD = new Epic("Epic #1 update-1", "Epic1 description update-1", epicId1);
        Epic epic2UPD = new Epic("Epic #2 update-2", "Epic2 description update-2", epicId2);
        TM.updateEpic(epic1UPD);
        TM.updateEpic(epic2UPD);

        Subtask subtask4 = new Subtask("Subtask #1-1 update-1", "Subtask1 description update-1", Enums.TaskStatus.NEW, epicId1, subtaskId1);
        Subtask subtask5 = new Subtask("Subtask #2-1 update-2", "Subtask1 description update-2", Enums.TaskStatus.IN_PROGRESS, epicId1, subtaskId2);
        Subtask subtask6 = new Subtask("Subtask #3-2 update-3", "Subtask1 description update-3", Enums.TaskStatus.DONE, epicId2, subtaskId3);

        final Integer subtaskId1UPD = TM.updateSubtask(subtask4);
        final Integer subtaskId2UPD = TM.updateSubtask(subtask5);
        final Integer subtaskId3UPD = TM.updateSubtask(subtask6);

        printAllTasks(TM);

        //Удаление по id
        System.out.println("" + '\n' + '\n' + "Удаление по id " + '\n' + '\n');
        TM.deleteTask(taskId1);
        TM.deleteSubtask(subtaskId3);
        TM.deleteEpic(epicId1);

        printAllTasks(TM);

        //Удаление
        System.out.println("" + '\n' + '\n' + "Удаление" + '\n' + '\n');
        TM.deleteEpics();
        TM.deleteSubtasks();
        TM.deleteTasks();

        printAllTasks(TM);
    }
}
