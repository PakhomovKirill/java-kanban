package manager;

import layout.PrintCommands;
import layout.UserInsert;
import storage.StorageManager;
import task.Epic;
import task.Subtask;
import task.Task;
import utils.Enums;

public class TaskManager {
    private UserInsert userInsert;
    private PrintCommands printCommands = new PrintCommands();
    private StorageManager SM = new StorageManager();

    public TaskManager(){
        userInsert = new UserInsert(this );
    }

    public void taskObjectManager(Enums.TaskTypeCommand taskType, Integer taskTypeKey, String taskId){
        switch (taskType){
            case  CREATE_SUBTASK:
                {
                    Subtask result = createNewSubtask();
                    if(result != null){
                        SM.setSubtask(result);
                    }
                }
              break;
            case CREATE_EPIC:
                {
                    Epic result = createNewEpic();
                    if(result != null){
                        SM.setEpic(result);
                    }
                }
                break;
            case CREATE_TASK:
                {
                    Subtask result = createNewTask();
                    if(result != null){
                        SM.setTask(result);
                    }
                }
                break;

            case UPDATE_TASK:
                {
                    if(taskTypeKey == 0){
                        return;
                    }
                    Enums.TasksType key = Enums.TasksType.values()[taskTypeKey - 1];
                    Task result = updateTask(key);

                    if(result != null){
                        SM.updateTask(result, key, result.getId());
                    }
                }
                break;
            case REMOVE_BY_ID:
                if(taskId != null){
                    SM.removeById(taskId);
                }
                break;
            case GET_EPIC_BY_ID:
                if(taskId != null){
                  Epic epic = SM.getEpicById(taskId);
                  printCommands.printEpicContains(epic);
                }
                break;
            case GET_TASK_BY_ID:
                if(taskId != null){
                    Subtask subtask = SM.getTaskById(taskId);
                    printCommands.printTaskContains(subtask);
                }
                break;
            case REMOVE_ALL:
                SM.removeAll();
                break;
            default:
                break;
        }
    }

    public Subtask createNewSubtask (){
        boolean result = userInsert.insertCreateSubtask();

        if(result){
            Subtask subtask = new Subtask(userInsert.getTaskName(), userInsert.getTaskDescription(), Enums.TaskStatus.NEW, userInsert.getParentTaskId());
            printCommands.printLabel("Добавлена подзадача с идентификатором - " + subtask.getId());
            return subtask;
        }

        return null;

    }

    public Epic createNewEpic(){
        boolean result = userInsert.insertCreateTask("Эпик(а)");

        if(result){
            Epic epic = new Epic(userInsert.getTaskName(), userInsert.getTaskDescription());

            printCommands.printLabel("Добавлен эпик с идентификатором - " + epic.getId());
            return epic;
        }

        return null;
    }

    public Subtask createNewTask(){
        boolean result = userInsert.insertCreateTask("Таск(а)");

        if(result){
            Subtask task = new Subtask(userInsert.getTaskName(), userInsert.getTaskDescription(), Enums.TaskStatus.NEW);

            printCommands.printLabel("Добавлен задача с идентификатором - " + task.getId());
            return task;
        }

        return null;
    }

    public Task updateTask(Enums.TasksType key){
        boolean result = userInsert.insertUpdateTask("Таск(а)/Епик(а)", key);

        if(result){
            Task task = new Task(userInsert.getTaskName(), userInsert.getTaskDescription(), userInsert.getTaskStatus(), userInsert.getTaskId());

            printCommands.printLabel("Таск с идентификатором обновлен" + userInsert.getTaskId());
            return task;
        }

        return null;
    }

    public void taskEditor(Enums.CommandsIndex commandType){
        switch (commandType){
            case CREATE:
                userInsert.insertNewTask(commandType);
                break;
            case UPDATE:
                userInsert.insertUpdateTask(commandType);
                break;
            case REMOVE_BY_ID:
            case GET_TASK_BY_ID:
            case GET_EPIC_BY_ID:
                userInsert.insertTaskId(commandType);
                break;
            case REMOVE_ALL:
                taskObjectManager(Enums.TaskTypeCommand.REMOVE_ALL, null, null);
            default:
                break;
        }
    }
}
