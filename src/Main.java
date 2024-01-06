import layout.UserInput;
import layout.PrintCommands;
import layout.UserInsert;
import manager.TaskManager;
import utils.Enums.CommandsIndex;

public class Main {
    private static UserInput userInput;
    private static PrintCommands printCommands;
    private static TaskManager TM;
    private static UserInsert UI;

    public static void main(String[] args) {
        // тестовый пакет layout только для проверки работоспособности Task Manager, отсутствует в master ветке
        userInput = new UserInput();
        printCommands = new PrintCommands();
        TM = new TaskManager();
        UI = new UserInsert(TM);

        printCommands.printLabel("Добро пожаловать! Это простой TODO-лист с эпиками и 3 статусами!)");

        while (true){
            printCommands.printStartMenu();

            Integer inputValue = userInput.setUserInputInt( 19, 1);

            CommandsIndex commandIndexKey = inputValue != null ? CommandsIndex.values()[inputValue - 1] : CommandsIndex.DEFAULT;

            switch (commandIndexKey){
                case CREATE_TASK:
                    UI.insertCreateTask();
                    break;
                case CREATE_SUBTASK:
                    UI.insertCreateSubtask();
                    break;
                case CREATE_EPIC:
                    UI.insertCreateEpic();
                    break;
                case UPDATE_TASK:
                    UI.insertUpdateTask();
                    break;
                case UPDATE_SUBTASK:
                    UI.insertUpdateSubTask();
                    break;
                case UPDATE_EPIC:
                    UI.insertUpdateEpic();
                    break;
                case GET_EPIC_BY_ID:
                    UI.getEpic();
                    break;
                case GET_SUBTASK_BY_ID:
                    UI.getSubtask();
                    break;
                case GET_TASK_BY_ID:
                    UI.getTask();
                    break;
                case REMOVE_ALL_TASKS:
                     TM.deleteTasks();
                    break;
                case REMOVE_ALL_SUBTASKS:
                    TM.deleteSubtasks();
                    break;
                case REMOVE_ALL_EPICS:
                    TM.deleteEpics();
                    break;
                case REMOVE_TASK_BY_ID:
                    UI.removeTaskById();
                    break;
                case REMOVE_SUBTASK_BY_ID:
                    UI.removeSubtaskById();
                    break;
                case REMOVE_EPIC_BY_ID:
                    UI.removeEpicById();
                    break;
                case GET_EPICS:
                    UI.getEpics();
                    break;
                case GET_TASKS:
                    UI.getTasks();
                    break;
                case GET_SUBTASKS:
                    UI.getSubtasks();
                    break;
                case EXIT:
                    printCommands.printLabel("До новых встреч");
                    return;
                case DEFAULT:
                    printCommands.printLabel("В меню нет такой команды");
                    break;
            }
        }
    }
}
