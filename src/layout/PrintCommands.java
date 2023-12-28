package layout;
import task.Epic;
import task.Subtask;

public class PrintCommands {

    public void printStartMenu(){
      System.out.println(
                  "Выберите пункт основного меню: \n" +
                  "1 - Добавить новую задачу, \n" +
                  "2 - Обновить задачу, \n" +
                  "3 - Удалить все задачи: \n" +
                  "4 - Удалить задачу/эпик по id, \n" +
                  "5 - Получить эпик по id, \n" +
                  "6 - Получить задачу по id, \n" +
                  "7 - Выход, \n"
              );
    }

    public void printLabel(String value){
        System.out.println(value + "\n");
    }

    public void printEpicContains(Epic epic){
        System.out.println(epic.toString() + "\n");
    }

    public void printTaskContains(Subtask subtask){
        System.out.println(subtask.toString() + "\n");
    }
}
