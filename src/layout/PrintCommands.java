package layout;
import task.Epic;
import task.Subtask;
import task.Task;

import java.util.HashMap;

public class PrintCommands {

    public void printStartMenu(){
      System.out.println(
                  "Выберите пункт основного меню: \n" +
                  "1 - Добавить таск, \n" +
                  "2 - Добавить сабтаск, \n" +
                  "3 - Добавить епик, \n" +

                  "4 - Обновить таск, \n" +
                  "5 - Обновить сабтаск, \n" +
                  "6 - Обновить епик, \n" +

                  "7 - Получить таск, \n" +
                  "8 - Получить сабтаск, \n" +
                  "9 - Получить епик, \n" +

                  "10 - Удалить таски, \n" +
                  "11 - Удалить сабтаски, \n" +
                  "12 - Удалить епики, \n" +

                  "13 - Удалить таск, \n" +
                  "14 - Удалить сабтаск, \n" +
                  "15 - Удалить епик, \n" +

                  "16 - Удалить таск, \n" +
                  "17 - Удалить сабтаск, \n" +
                  "18 - Удалить епик, \n" +

                  "16 - Получить таски, \n" +
                  "17 - Получить сабтаски, \n" +
                  "18 - Получить епики, \n" +

                  "19 - Выход, \n"
              );
    }

    public void printLabel(String value){
        System.out.println(value + "\n");
    }

    public void printEpicsContains(HashMap<Integer, Epic> epics){
        if(epics != null){
            System.out.println(epics.toString() + "\n");
        } else {
            System.out.println("Нет данных");
        }
    }

    public void printSubtasksContains(HashMap<Integer, Subtask> subtasks){
        if(subtasks != null){
            System.out.println(subtasks.toString() + "\n");
        } else {
            System.out.println("Нет данных");
        }
    }

    public void printTasksContains(HashMap<Integer, Task> tasks){
        if(tasks != null){
            System.out.println(tasks.toString() + "\n");
        } else {
            System.out.println("Нет данных");
        }
    }

    public void printEpicContains(Epic epic){
        if(epic != null){
            System.out.println(epic.toString() + "\n");
        } else {
            System.out.println("Нет данных");
        }
    }

    public void printSubtaskContains(Subtask subtask){
        if(subtask != null){
            System.out.println(subtask.toString() + "\n");
        } else {
            System.out.println("Нет данных");
        }
    }

    public void printTaskContains(Task task){
        if(task != null){
            System.out.println(task.toString() + "\n");
        } else {
            System.out.println("Нет данных");
        }
    }
}
