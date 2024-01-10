package manager;

import task.Epic;
import task.Subtask;
import task.Task;
import utils.Enums;
import utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private int generatorId = 0;

    private void updateEpicStatus(Integer epicId){
        if(epicId == null) return;

        ArrayList<Subtask> allEpicList = getEpicSubtasks(epicId);
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
    public ArrayList<Subtask> getSubtasks() {
        return new ArrayList<>(this.subtasks.values());
    }

    public ArrayList<Task> getTasks() {
        return new ArrayList<>(this.tasks.values());
    }

    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(this.epics.values());
    }

    public ArrayList<Subtask> getEpicSubtasks(int epicId) {
        Epic currentEpic = epics.get(epicId);
        return new ArrayList<>(currentEpic.getAllChildrenList().values());
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
        task.setId(++this.generatorId);

        if(!this.tasks.containsKey(task.getId())){
            this.tasks.put(task.getId(), task);
        }

        return task.getId();
    }

    public int addNewEpic(Epic epic) {
        epic.setId(++this.generatorId);

        if(!this.epics.containsKey(epic.getId())){
            this.epics.put(epic.getId(), epic);
        }

        updateEpicStatus(epic.getId());

        return epic.getId();
    }

    public Integer addNewSubtask(Subtask subtask) {
        Integer parentId = subtask.getParentId();
        Epic currentEpic = epics.get(parentId);

        if(currentEpic == null){
            return null;
        }

        subtask.setId(++this.generatorId);
        Integer subtaskId = subtask.getId();

        if(!this.subtasks.containsKey(subtaskId)){
            this.subtasks.put(subtaskId, subtask);

            currentEpic.setChildSubtask(subtask);
        }

        updateEpicStatus(subtask.getParentId());

        return subtaskId;
    }

    // обновление
    public Integer updateTask(Task task) {
        Integer taskId = task.getId();
        if(tasks.containsKey(task.getId())){
            Task current = tasks.get(taskId);
            current.setDescription(task.getDescription());
            current.setTitle(task.getTitle());
            current.setStatus(task.getStatus());
        }

        return taskId;
    }

    public void updateEpic(Epic epic) {
        Integer epicId = epic.getId();
        if(epics.containsKey(epicId)){
            Epic current = epics.get(epicId);
            current.setDescription(epic.getDescription());
            current.setTitle(epic.getTitle());
        }
    }

    public Integer updateSubtask(Subtask subtask) {
        Integer subtaskId = subtask.getId();

        if(subtasks.containsKey(subtaskId)){
            Subtask current = subtasks.get(subtaskId);
            Integer subtaskParentId = current.getParentId();
            Epic currentEpic = epics.get(subtaskParentId);

            current.setDescription(subtask.getDescription());
            current.setTitle(subtask.getTitle());
            current.setStatus(subtask.getStatus());

            currentEpic.updateChildList(subtaskId, subtask);
            updateEpicStatus(subtaskParentId);
        }

        return subtaskId;
    }

    // удаление
    public void deleteTasks() {
        tasks.clear();
    }
    public void deleteSubtasks() {

        subtasks.clear();

        for (Epic epic : epics.values()) {
            epic.clearChildSubtasks();
        }

    }
    public void deleteEpics() {
        epics.clear();
        subtasks.clear();
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

        subtasks.entrySet()
                .stream()
                .forEach(subtask -> {
                    if(subtask.getValue().getId() == id){
                        subtasks.remove(subtask.getValue().getId());
                    }
        });
    }
    public void deleteSubtask(int id) {
        Subtask currentSubtask = subtasks.get(id);
        Integer currentSubtaskParentId = currentSubtask.getParentId();
        Epic currentEpic = epics.get(currentSubtaskParentId);

        if(currentSubtaskParentId != null){
            currentEpic.removeChildSubtask(id);
        }

        if(subtasks.containsKey(id)){
            subtasks.remove(id);
        }

        if(currentEpic.getAllChildrenList().values().size() == 0){
            epics.remove(currentEpic.getId());
        }
    }

}
