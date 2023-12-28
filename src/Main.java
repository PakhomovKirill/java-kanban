import layout.UserInput;
import layout.PrintCommands;
import manager.TaskManager;
import utils.Enums.CommandsIndex;

public class Main {
    private static UserInput userInput;
    private static PrintCommands printCommands;
    private static TaskManager TM;

    public static void main(String[] args) {

        userInput = new UserInput();
        printCommands = new PrintCommands();
        TM = new TaskManager();

        printCommands.printLabel("Добро пожаловать! Это простой TODO-лист с эпиками и 3 статусами!)");

        while (true){
            printCommands.printStartMenu();

            Integer inputValue = userInput.setUserInputInt( 7, 1);

            CommandsIndex commandIndexKey = inputValue != null ? CommandsIndex.values()[inputValue - 1] : CommandsIndex.DEFAULT;

            switch (commandIndexKey){
                case CREATE:
                    TM.taskEditor(CommandsIndex.CREATE);
                    break;
                case UPDATE:
                    TM.taskEditor(CommandsIndex.UPDATE);
                    break;
                case REMOVE_ALL:
                    TM.taskEditor(CommandsIndex.REMOVE_ALL);
                    break;
                case REMOVE_BY_ID:
                    TM.taskEditor(CommandsIndex.REMOVE_BY_ID);
                    break;
                case GET_EPIC_BY_ID:
                    TM.taskEditor(CommandsIndex.GET_EPIC_BY_ID);
                    break;
                case GET_TASK_BY_ID:
                    TM.taskEditor(CommandsIndex.GET_TASK_BY_ID);
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
