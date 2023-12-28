package layout;
import manager.TaskManager;
import storage.StorageManager;
import utils.Enums;
public class UserInsert {
    private UserInput userInput = new UserInput();
    private PrintCommands printCommands = new PrintCommands();
    private TaskManager TM;
    private StorageManager SM = new StorageManager();
    private String taskID;
    private String parentTaskID;
    private String taskName;
    private String taskDescription;
    private Enums.TaskStatus taskStatus = Enums.TaskStatus.NEW;

    public String getTaskId(){
        return this.taskID;
    }

    public String getParentTaskId(){
        return this.parentTaskID;
    }

    public String getTaskName(){
        return this.taskName;
    }

    public String getTaskDescription(){
        return this.taskDescription;
    }

    public Enums.TaskStatus getTaskStatus(){
        return this.taskStatus;
    }

    public UserInsert(TaskManager taskManager){
        this.TM = taskManager;
    }

    public void insertNewTask(Enums.CommandsIndex commandType){
        printCommands.printLabel("Выберите тип задачи: 1 - задача, 2 - эпик, 3 - подзадача, 0 - возврат в предыдущее меню ");

        while (true){

            Integer taskType = userInput.setUserInputInt(3,0);

            if(taskType != null){
                this.TM.taskObjectManager(Enums.TaskTypeCommand.getEnumByType(commandType, taskType), taskType, null);

                return;
            }
        }
    }

    public void insertUpdateTask(Enums.CommandsIndex commandType){
        printCommands.printLabel("Выберите тип задачи: 1 - задача, 2 - эпик,  0 - возврат в предыдущее меню ");

        while (true){

            Integer taskType = userInput.setUserInputInt(2,0);

            if(taskType != null){
                this.TM.taskObjectManager(Enums.TaskTypeCommand.getEnumByType(commandType, taskType), taskType, null);

                return;
            }
        }
    }

    public void insertTaskId (Enums.CommandsIndex commandType){
        printCommands.printLabel("Укажите id таска!");
        this.taskID = userInput.setUserInputString();

        if(this.taskID != null){
            this.TM.taskObjectManager(Enums.TaskTypeCommand.getEnumByType(commandType, null), null, this.taskID);
        }
    }

    public boolean insertCreateSubtask (){
        printCommands.printLabel("Укажите id эпика!");
        this.parentTaskID = userInput.setUserInputString();

        if(!SM.isEpicsListHasItemById(this.parentTaskID)){
            printCommands.printLabel("Эпика c таким id не существует!");
            return false;
        }

        printCommands.printLabel("Укажите имя задачи задачи!");
        this.taskName = userInput.setUserInputString();

        printCommands.printLabel("Укажите описание задачи!");
        this.taskDescription = userInput.setUserInputString();

        return true;
    }

    public boolean insertCreateTask (String typeName){
        printCommands.printLabel("Укажите название "+ typeName +"!");
        this.taskName = userInput.setUserInputString();

        printCommands.printLabel("Укажите описание "+ typeName +"!");
        this.taskDescription = userInput.setUserInputString();

        return true;
    }

    public boolean insertUpdateTask (String typeName, Enums.TasksType key){
        printCommands.printLabel("Укажите id таска!");
        this.taskID = userInput.setUserInputString();

        if(!SM.isEpicsListHasItemById(this.taskID) && key == Enums.TasksType.EPIC){
            printCommands.printLabel("Эпика c таким id не существует!");
            return false;
        }

        if(!SM.isTasksListHasItemById(this.taskID) && key == Enums.TasksType.TASK){
            printCommands.printLabel("Таска c таким id не существует!");
            return false;
        }

        insertCreateTask("Таска(а)");

        if(key == Enums.TasksType.TASK){
            printCommands.printLabel("Укажите статус "+ typeName +"где: 1 - NEW, 2 - DONE, 3 - IN PROGRESS");
            Integer typeStatusValue = userInput.setUserInputInt(3,1);
            this.taskStatus = Enums.TaskStatus.values()[typeStatusValue - 1];
        }

        if(this.taskStatus == null){
            return false;
        }


        return true;
    }
}
