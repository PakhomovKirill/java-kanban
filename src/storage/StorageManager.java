package storage;

import layout.PrintCommands;
import task.Epic;
import task.Subtask;
import task.Task;
import utils.Enums;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class StorageManager {
    private static HashMap<String, Epic> epicsList = new HashMap<>();
    private static HashMap<String, Subtask> taskList = new HashMap<>();

    private static PrintCommands printCommands = new PrintCommands();

    private static void updateEpicStatus(String epicId){
        Map<String, Subtask> allEpicList = getAllSubtasksByEpicId(epicId);
        Epic currentEpic =  epicsList.get(epicId);

        if (currentEpic == null || allEpicList.size() == 0) {
            return;
        }

        if (isEpicListInProgress(allEpicList)) {
            currentEpic.setStatus(Enums.TaskStatus.IN_PROGRESS);
        } else if (isEpicListStatusDoneAll(allEpicList)) {
            currentEpic.setStatus(Enums.TaskStatus.DONE);
        } else {
            currentEpic.setStatus(Enums.TaskStatus.NEW);
        }
    }

    public static HashMap<String, Epic> getEpicsList(){
        return epicsList;
    }

    public static HashMap<String, Subtask> getTaskList(){
        return taskList;
    }

    public static HashMap<String, Epic> setEpic(Epic epic){
        if(!epicsList.equals(epic)){
            epicsList.put(epic.getId(), epic);
        }

        updateEpicStatus(epic.getId());

        return epicsList;
    }

    public static HashMap<String, Subtask> setTask(Subtask subtask){
        if(!taskList.equals(subtask)){
            taskList.put(subtask.getId(), subtask);
        }

        return taskList;
    }

    public static HashMap<String, Subtask> setSubtask(Subtask subtask){
        if(!taskList.equals(subtask)){
            taskList.put(subtask.getId(), subtask);
        }

        updateEpicStatus(subtask.getParentId());

        return taskList;
    }

    public static boolean isEpicListStatusDoneAll(Map<String, Subtask> subtasks) {
        for (Subtask subtask : subtasks.values()) {
            if (subtask.getStatus() != Enums.TaskStatus.DONE) {
                return false;
           }
        }

        return true;
    }

    public static boolean isEpicListInProgress(Map<String, Subtask> subtasks) {
        for (Subtask subtask : subtasks.values()) {
            if (subtask.getStatus() == Enums.TaskStatus.IN_PROGRESS) {
                return true;
            }
        }

        return false;
    }

    public static void updateTask(Task task , Enums.TasksType tasksType, String taskId){

            if(epicsList.containsKey(task.getId()) && tasksType == Enums.TasksType.EPIC){
                Epic current = epicsList.get(taskId);
                current.setDescription(task.getDescription());
                current.setTitle(task.getTitle());
                updateEpicStatus(current.getId());
            }

            if(taskList.containsKey(task.getId()) && tasksType == Enums.TasksType.TASK){
                Subtask current = taskList.get(taskId);
                current.setDescription(task.getDescription());
                current.setTitle(task.getTitle());
                current.setStatus(task.getStatus());
                updateEpicStatus(current.getParentId());
            }

    }

    public static Map<String, Subtask> getAllSubtasksByEpicId (String epicId){
        Map<String, Subtask> result = taskList.entrySet()
                .stream()
                .filter(value -> (value.getValue().getParentId() != null && value.getValue().getParentId().contains(epicId)))
                .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));

        return result;
    }

    public void removeById(String id){
        if(epicsList.containsKey(id)){
            epicsList.remove(id);
        }

        if(taskList.containsKey(id)){
            taskList.remove(id);
        }
    }

    public void removeAll(){
        epicsList.clear();
        taskList.clear();
    }

    public Epic getEpicById(String epicId){
        return epicsList.get(epicId);
    }

    public Subtask getTaskById(String epicId){
        return taskList.get(epicId);
    }

    public static boolean isEpicsListHasItemById (String id){
        return epicsList.containsKey(id);
    }

    public static boolean isTasksListHasItemById (String id){
        return taskList.containsKey(id);
    }

}
