package manager;

import task.Epic;
import task.Subtask;
import task.Task;
import utils.Enums;
import utils.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class TaskManager {

    private static final HashMap<Integer, Task> tasks = new HashMap<>();
    private static final HashMap<Integer, Epic> epics = new HashMap<>();
    private static final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private int generatorId = 0;


    private static void updateEpicStatus(Integer epicId){
        System.out.println(epicId);
        if(epicId == null) return;

        Map<Integer, Subtask> allEpicList = getEpicSubtasks(epicId);
        Epic currentEpic =  epics.get(epicId);

        if (currentEpic == null || allEpicList.size() == 0) {
            return;
        }

        if (Utils.isEpicListInProgress(allEpicList)) {
            currentEpic.setStatus(Enums.TaskStatus.IN_PROGRESS);
        } else if (Utils.isEpicListStatusDoneAll(allEpicList)) {
            currentEpic.setStatus(Enums.TaskStatus.DONE);
        } else {
            currentEpic.setStatus(Enums.TaskStatus.NEW);
        }
    }

    // получение
    public HashMap<Integer, Subtask> getSubtasks() {
        return this.subtasks;
    }

    public HashMap<Integer, Task> getTasks() {
        return this.tasks;
    }

    public HashMap<Integer, Epic> getEpics() {
        return this.epics;
    }

    public static Map<Integer, Subtask> getEpicSubtasks(int epicId) {
        Map<Integer, Subtask> result = subtasks.entrySet()
                .stream()
                .filter(value -> (value.getValue().getParentId() != null && value.getValue().getParentId() == epicId))
                .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));

        return result;
    }

    // получение по id
    public Task getTask(int id) {
        if(!tasks.containsKey(id)) return null;

        return tasks.get(id);
    }
    public Subtask getSubtask(int id) {
        if(!subtasks.containsKey(id)) return null;

        return subtasks.get(id);
    }
    public Epic getEpic(int id) {
        if(!epics.containsKey(id)) return null;

        return epics.get(id);
    }

    // создание
    public int addNewTask(Task task) {
        task.setId(this.generatorId += 1);

        if(!this.tasks.equals(task)){
            this.tasks.put(task.getId(), task);
        }

        return task.getId();
    }

    public int addNewEpic(Epic epic) {
        epic.setId(this.generatorId += 1);

        if(!this.epics.equals(epic)){
            this.epics.put(epic.getId(), epic);
        }

        updateEpicStatus(epic.getId());

        return epic.getId();
    }

    public Integer addNewSubtask(Subtask subtask) {
        subtask.setId(this.generatorId += 1);

        if(!this.subtasks.equals(subtask)){
            this.subtasks.put(subtask.getId(), subtask);
        }

        updateEpicStatus(subtask.getParentId());

        return subtask.getId();
    }

    // обновление
    public void updateTask(Task task) {
        Integer taskId = task.getId();
        if(tasks.containsKey(task.getId())){
            Task current = tasks.get(taskId);
            current.setDescription(task.getDescription());
            current.setTitle(task.getTitle());
            current.setStatus(task.getStatus());
        }
    }

    public void updateEpic(Epic epic) {
        Integer epicId = epic.getId();
        if(epics.containsKey(epicId)){
            Epic current = epics.get(epicId);
            current.setDescription(epic.getDescription());
            current.setTitle(epic.getTitle());
            updateEpicStatus(current.getId());
        }
    }

    public void updateSubtask(Subtask subtask) {
        Integer subtaskId = subtask.getId();
        System.out.println(subtaskId);
        if(subtasks.containsKey(subtaskId)){
            Subtask current = subtasks.get(subtaskId);
            current.setDescription(subtask.getDescription());
            current.setTitle(subtask.getTitle());
            current.setStatus(subtask.getStatus());
            updateEpicStatus(current.getParentId());
        }
    }

    // удаление
    public void deleteTasks() {
        tasks.clear();
    }
    public void deleteSubtasks() {
        subtasks.clear();
    }
    public void deleteEpics() {
        epics.clear();
    }


    // удаление по id
    public void deleteTask(int id) {
        if(tasks.containsKey(id)){
            tasks.remove(id);
        }
    }
    public void deleteEpic(int id) {
        if(epics.containsKey(id)){
            epics.remove(id);
        }
    }
    public void deleteSubtask(int id) {
        if(subtasks.containsKey(id)){
            subtasks.remove(id);
        }
    }

}
