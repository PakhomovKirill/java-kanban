package layout;
import manager.TaskManager;
import task.Epic;
import task.Subtask;
import task.Task;
import utils.*;
public class UserInsert {
    private UserInput userInput = new UserInput();
    private PrintCommands printCommands = new PrintCommands();
    private TaskManager TM;
    private Integer taskID;
    private Integer parentTaskID;
    private String taskName;
    private String taskDescription;
    private Enums.TaskStatus taskStatus = Enums.TaskStatus.NEW;

    public UserInsert(TaskManager taskManager){
        this.TM = taskManager;
    }

    public void insertCreateSubtask (){
        printCommands.printLabel("Укажите id эпика!");
        this.parentTaskID = userInput.setUserInputInt(Constants.MAX_INT_VALUE,0);

        if(!Utils.isEpicsListHasItemById(this.parentTaskID, TM.getEpics())){
            printCommands.printLabel("Эпика c таким id не существует!");
        } else if (this.parentTaskID != null) {
            insertCreate();

            Subtask subtask = new Subtask(this.taskName, this.taskDescription, Enums.TaskStatus.NEW, this.parentTaskID);
            int taskId = TM.addNewSubtask(subtask);

            printCommands.printLabel("Сабтаск с идентификатором создан" + taskId);
        }
    }

    public void insertCreate () {
        printCommands.printLabel("Укажите название !");
        this.taskName = userInput.setUserInputString();

        printCommands.printLabel("Укажите описание !");
        this.taskDescription = userInput.setUserInputString();

    }

    public void insertCreateTask () {
        insertCreate();

        Task task = new Task(this.taskName, this.taskDescription, this.taskStatus, this.taskID);
        int taskId = TM.addNewTask(task);

        printCommands.printLabel("Таск с идентификатором создан" + taskId);

    }

    public void insertCreateEpic () {
        insertCreate();

        Epic task = new Epic(this.taskName, this.taskDescription);
        int epicId = TM.addNewEpic(task);

        printCommands.printLabel("Эпик с идентификатором создан" + epicId);

    }

    public void insertUpdateTask (){
        printCommands.printLabel("Укажите id таска!");
        this.taskID = userInput.setUserInputInt(Constants.MAX_INT_VALUE,0);

        if(!Utils.isTasksListHasItemById(this.taskID, TM.getTasks())){
            printCommands.printLabel("Таск не существует");
        } else {
            insertCreate();

            printCommands.printLabel("Укажите статус где: 1 - NEW, 2 - DONE, 3 - IN PROGRESS");
            Integer typeStatusValue = userInput.setUserInputInt(3,1);
            this.taskStatus = Enums.TaskStatus.values()[typeStatusValue - 1];

            if(this.taskStatus != null){
                Task task = new Task(this.taskName, this.taskDescription, this.taskStatus);
                task.setId(taskID);
                TM.updateTask(task);
                printCommands.printLabel("Таск с идентификатором обновлен" + this.taskID);
            }
        }
    }

    public void insertUpdateSubTask (){
        printCommands.printLabel("Укажите id сабтаска!");
        this.taskID = userInput.setUserInputInt(Constants.MAX_INT_VALUE,0);

        if(!Utils.isSubtasksListHasItemById(this.taskID, TM.getSubtasks())){
            printCommands.printLabel("Сабтаск не существует");
        } else {
            insertCreate();

            printCommands.printLabel("Укажите статус где: 1 - NEW, 2 - DONE, 3 - IN PROGRESS");
            Integer typeStatusValue = userInput.setUserInputInt(3,1);
            this.taskStatus = Enums.TaskStatus.values()[typeStatusValue - 1];

            if(this.taskStatus != null){
                Subtask subtask = new Subtask(this.taskName, this.taskDescription, this.taskStatus);
                subtask.setId(this.taskID);
                TM.updateSubtask(subtask);
                printCommands.printLabel("Сабтаск с идентификатором обновлен" + this.taskID);
            }
        }
    }

    public void insertUpdateEpic (){
        printCommands.printLabel("Укажите id эпика!");
        this.taskID = userInput.setUserInputInt(Constants.MAX_INT_VALUE,0);

        if(!Utils.isEpicsListHasItemById(this.taskID, TM.getEpics())){
            printCommands.printLabel("Эпик не существует");
        } else {
            insertCreate();

            if(this.taskStatus != null){
                Epic epic = new Epic(this.taskName, this.taskDescription, this.taskID);
                TM.updateEpic(epic);
                printCommands.printLabel("Эпик с идентификатором обновлен" + this.taskID);
            }
        }
    }

    public void getEpic (){
        printCommands.printLabel("Укажите id эпика!");
        this.taskID = userInput.setUserInputInt(Constants.MAX_INT_VALUE,0);

        if(this.taskID != null){
            Epic epic = TM.getEpic(this.taskID);
            printCommands.printEpicContains(epic);
        }
    }

    public void getTask (){
        printCommands.printLabel("Укажите id таска!");
        this.taskID = userInput.setUserInputInt(Constants.MAX_INT_VALUE,0);

        if(this.taskID != null){
            Task task = TM.getTask(this.taskID);
            printCommands.printTaskContains(task);
        }
    }

    public void getSubtask (){
        printCommands.printLabel("Укажите id сабтаска");
        this.taskID = userInput.setUserInputInt(Constants.MAX_INT_VALUE,0);

        if(this.taskID != null){
            Subtask subtask = TM.getSubtask(this.taskID);
            printCommands.printSubtaskContains(subtask);
        }
    }

    public void removeTaskById (){
        printCommands.printLabel("Укажите id таска");
        this.taskID = userInput.setUserInputInt(Constants.MAX_INT_VALUE,0);

        TM.deleteTask(this.taskID);
    }

    public void removeSubtaskById (){
        printCommands.printLabel("Укажите id сабтаска");
        this.taskID = userInput.setUserInputInt(Constants.MAX_INT_VALUE,0);

        TM.deleteSubtask(this.taskID);
    }

    public void removeEpicById (){
        printCommands.printLabel("Укажите id эпика");
        this.taskID = userInput.setUserInputInt(Constants.MAX_INT_VALUE,0);

        TM.deleteEpic(this.taskID);
    }

    public void getEpics(){
        printCommands.printEpicsContains(TM.getEpics());
    }

    public void getSubtasks(){
        printCommands.printSubtasksContains(TM.getSubtasks());
    }

    public void getTasks(){
        printCommands.printTasksContains(TM.getTasks());
    }
}
