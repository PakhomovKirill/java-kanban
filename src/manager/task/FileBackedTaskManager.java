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
import java.util.Arrays;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager implements TaskManager{

    private static String fileName = "PracticumTaskList.csv";
    private static final String HOME = System.getProperty("user.home");
    private static Path path = Paths.get(HOME, fileName);
    private static FileUtil fileUtil = new FileUtil();
    public FileBackedTaskManager(){
        super();
        parseCsvFile();
    }

    private ArrayList<String[]> builtListToCsvTable(ArrayList<Task> list){
      String[] headers = new String[Enums.csvTableHeaders.values().length];
      ArrayList<String[]> csvList = new ArrayList<>();

      for(Enums.csvTableHeaders value: Enums.csvTableHeaders.values()){
          headers[value.ordinal()] = value.toString();
      }

      csvList.add(headers);

      for (Task item: list){
          String[] rowArray = Arrays.copyOfRange(item.toString().split(","), 0, Enums.csvTableHeaders.values().length);
          csvList.add(rowArray);
      }

      return csvList;
    }

    public void parseCsvFile(){
        if(fileUtil.fileIsExist(path)){
          try {
              File file = new File(path.toFile().toURI());
              ArrayList<String[]> csvList;
              csvList = fileUtil.loadFromFile(file);
              csvList.remove(0);

              for (String[] item: csvList){
                  Enums.TasksType key = item[1] != null ? Enums.TasksType.valueOf(item[1]) : null;

                  switch (key){
                      case EPIC:
                          {
                              Epic epic = new Epic(item[2], item[4]);
                              super.addNewEpic(epic);
                          }
                          break;
                      case SUBTASK:
                          {
                              if(item[5] != null){
                                  Subtask subtask = new Subtask(item[2], item[4], Enums.TaskStatus.valueOf(item[3]), Integer.parseInt(item[5]));
                                  super.addNewSubtask(subtask);
                              }
                          }
                          break;
                      case TASK:
                          {
                              Task task = new Task(item[2], item[4], Enums.TaskStatus.valueOf(item[3]));
                              super.addNewTask(task);
                          }
                          break;
                      default:
                          break;
                  }
              }
            } catch (ManagerSaveException e){
              System.out.println(e.getDetailMessage());
            } catch (Exception e){
              System.out.println(e.getMessage());
            }
        }
    }

    private void saveToFile() {
        ArrayList<Task> tasks = super.getTasks();
        ArrayList<Epic> epics = super.getEpics();
        ArrayList<Subtask> subtasks = super.getSubtasks();
        ArrayList<Task> concatTasksList = new ArrayList<>();

        List<ArrayList<? extends Task>> fullList = Arrays.asList(tasks,epics,subtasks);

        for(ArrayList<? extends Task> item: fullList){
            concatTasksList.addAll(item);
        }

        try {
            if(!fileUtil.fileIsExist(path)){
                fileUtil.fileCreate(path);
            }

            fileUtil.fileWrite(path, builtListToCsvTable(concatTasksList));
        } catch (ManagerSaveException e){
            System.out.println(e.getDetailMessage());
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
    public int addNewTask(Task task) {
        int taskId = super.addNewTask(task);
        saveToFile();

        return taskId;
    }

    public int addNewEpic(Epic epic) {
      int epicId = super.addNewEpic(epic);
      saveToFile();

      return epicId;
    }

    public Integer addNewSubtask(Subtask subtask) {
      Integer subtaskId = super.addNewSubtask(subtask);
      saveToFile();

      return subtaskId;
    }

    // обновление
    public Integer updateTask(Task task) {
      Integer taskId = super.updateTask(task);
      saveToFile();

      return taskId;
    }

    public Integer updateEpic(Epic epic) {
      Integer epicId = super.updateEpic(epic);
      saveToFile();

      return epicId;
    }

    public Integer updateSubtask(Subtask subtask) {
      Integer subtaskId = super.updateSubtask(subtask);
      saveToFile();

      return subtaskId;
    }

    // удаление
    public void deleteTasks() {
      super.deleteTasks();
      saveToFile();
    }

    public void deleteSubtasks() {
      super.deleteSubtasks();
      saveToFile();
    }

    public void deleteEpics() {
      super.deleteEpics();
      saveToFile();
    }

    // удаление по id
    public void deleteTask(int id) {
      super.deleteTask(id);
      saveToFile();
    }

    public void deleteEpic(int id) {
      super.deleteEpic(id);
      saveToFile();
    }

    public void deleteSubtask(int id) {
      super.deleteSubtask(id);
      saveToFile();
    }

    public void removeHistoryTask(Integer id){
      super.removeHistoryTask(id);
    }

    public ArrayList<Task> getHistory(){
      return super.getHistory();
    }
}
