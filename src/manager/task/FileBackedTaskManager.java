package manager.task;

import exception.ManagerSaveException;
import task.Epic;
import task.Subtask;
import task.Task;
import utils.Enums;
import utils.FileUtil;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager implements TaskManager{

    private static String fileName = "PracticumTaskList.csv";
    private static final String HOME = System.getProperty("user.home");
    private static Path path = Paths.get(HOME, fileName);
    private static FileUtil fileUtil = new FileUtil();

    public FileBackedTaskManager() {
        super();
        try {
            parseCsvFile();
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
    }

    public void parseCsvFile() throws ManagerSaveException {
        if(fileUtil.fileIsExist(path)){
          try {
              File file = new File(path.toFile().toURI());
              ArrayList<String[]> csvList;
              csvList = fileUtil.loadFromFile(file);
              csvList.remove(0);

              for (String[] item: csvList){
                  final Integer id = Integer.parseInt(item[0]);
                  final Enums.TasksType type = Enums.TasksType.valueOf(item[1]);
                  final String name = item[2];
                  final String status = item[3];
                  final String description = item[4];
                  final String epicId = item[5] != null ? item[5] : null;

                  Enums.TasksType key = id != null ? type : null;

                  switch (key){
                      case EPIC: {
                              Epic epic = new Epic(name, description);
                              super.addNewEpic(epic);
                          }
                          break;
                      case SUBTASK: {
                              if(epicId  != null){
                                  Subtask subtask = new Subtask(name, description, Enums.TaskStatus.valueOf(status), Integer.parseInt(epicId));
                                  super.addNewSubtask(subtask);
                              }
                          }
                          break;
                      case TASK: {
                              Task task = new Task(name, description, Enums.TaskStatus.valueOf(status));
                              super.addNewTask(task);
                          }
                          break;
                      default:
                          break;
                  }
              }
            } catch (ManagerSaveException e){
              throw new ManagerSaveException(e.getMessage());
            } catch (Exception e){
              throw new ManagerSaveException(e.getMessage());
            }
        }
    }

    private void saveToFile(Enums.TaskActionType type, Object ...args) throws ManagerSaveException {

        ArrayList<Task> tasks = super.getTasks();
        ArrayList<Epic> epics = super.getEpics();
        ArrayList<Subtask> subtasks = super.getSubtasks();
        String[] headers = new String[Enums.csvTableHeaders.values().length];

        super.parseTaskByTimestampValue(type, args);

        for(Enums.csvTableHeaders value: Enums.csvTableHeaders.values()){
            headers[value.ordinal()] = value.toString();
        }

        try {
            if(!fileUtil.fileIsExist(path)){
                fileUtil.fileCreate(path);
            }

            fileUtil.fileWrite(path, tasks, epics, subtasks, headers);
        } catch (ManagerSaveException e){
            throw new ManagerSaveException(e.getMessage());
        }
    }

    // получение
    public ArrayList<Subtask> getSubtasks() {
      ArrayList<Subtask> subtasks = super.getSubtasks();
      return subtasks;
    }

    public ArrayList<Task> getTasks() {
      ArrayList<Task> tasks = super.getTasks();
      return tasks;
    }

    public ArrayList<Epic> getEpics() {
      ArrayList<Epic> epics = super.getEpics();
      return epics;
    }

    public ArrayList<Subtask> getEpicSubtasks(int epicId) {
      ArrayList<Subtask> epicSubtasks = super.getEpicSubtasks(epicId);
      return epicSubtasks;
    }

    // получение по id
    public Task getTask(int id) {
      Task task = super.getTask(id);
      return task;
    }

    public Subtask getSubtask(int id) {
      Subtask subtask = super.getSubtask(id);
      return subtask;
    }

    public Epic getEpic(int id) {
      Epic epic = super.getEpic(id);
      return epic;
    }

    // создание
    public int addNewTask(Task task)  {
        int taskId = super.addNewTask(task);
        try {
            saveToFile(Enums.TaskActionType.CHECK_TO_UNIQUE_TIMESTAMP , task);
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }

        return taskId;
    }

    public int addNewEpic(Epic epic) {
      int epicId = super.addNewEpic(epic);
        try {
            saveToFile(Enums.TaskActionType.CHECK_TO_UNIQUE_TIMESTAMP, epic);
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }

        return epicId;
    }

    public Integer addNewSubtask(Subtask subtask) {
      Integer subtaskId = super.addNewSubtask(subtask);
        try {
            saveToFile(Enums.TaskActionType.CHECK_TO_UNIQUE_TIMESTAMP, subtask);
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }

        return subtaskId;
    }

    // обновление
    public Integer updateTask(Task task) {
      Integer taskId = super.updateTask(task);
        try {
            saveToFile(Enums.TaskActionType.CHECK_TO_UNIQUE_TIMESTAMP, task);
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }

        return taskId;
    }

    public Integer updateEpic(Epic epic) {
      Integer epicId = super.updateEpic(epic);
        try {
            saveToFile(Enums.TaskActionType.CHECK_TO_UNIQUE_TIMESTAMP, epic);
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }

        return epicId;
    }

    public Integer updateSubtask(Subtask subtask) {
      Integer subtaskId = super.updateSubtask(subtask);
        try {
            saveToFile(Enums.TaskActionType.CHECK_TO_UNIQUE_TIMESTAMP, subtask);
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }

        return subtaskId;
    }

    // удаление
    public void deleteTasks() {
      super.deleteTasks();
        try {
            saveToFile(Enums.TaskActionType.REMOVE_ALL_TASKS);
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteSubtasks() {
      super.deleteSubtasks();
        try {
            saveToFile(Enums.TaskActionType.REMOVE_ALL_SUBTASKS);
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteEpics() {
      super.deleteEpics();
        try {
            saveToFile(Enums.TaskActionType.REMOVE_ALL_EPICS);
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
    }

    // удаление по id
    public void deleteTask(int id) {
      super.deleteTask(id);
        try {
            saveToFile(Enums.TaskActionType.REMOVE, id);
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteEpic(int id) {
      super.deleteEpic(id);
        try {
            saveToFile(Enums.TaskActionType.REMOVE, id);
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteSubtask(int id) {
      super.deleteSubtask(id);
        try {
            saveToFile(Enums.TaskActionType.REMOVE, id);
        } catch (ManagerSaveException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeHistoryTask(Integer id){
      super.removeHistoryTask(id);
    }

    public ArrayList<Task> getHistory(){
      return super.getHistory();
    }

    public List<Task> getPrioritizedTasks(){
        return super.getPrioritizedTasks();
    }
}
