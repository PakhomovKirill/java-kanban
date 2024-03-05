package manager.task;

import manager.Managers;
import manager.history.HistoryManager;
import task.Epic;
import task.Subtask;
import task.Task;
import utils.Enums;
import utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {

    private HistoryManager<Task> HM;
    private Managers Manager = new Managers();
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private int generatorId = 0;

    public InMemoryTaskManager(){
        HM = Manager.getDefaultHistory();
    }

    private void updateEpicStatus(Integer epicId){
        if(epicId == null) { return; }

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

        Task currentTask = tasks.get(id);
        if(HM != null){
          HM.addTaskToHistory(currentTask);
        }

        return currentTask;
    }
    public Subtask getSubtask(int id) {
        if(!subtasks.containsKey(id)) return null;

        Subtask currentSubtask = subtasks.get(id);
        if(HM != null){
          HM.addTaskToHistory(currentSubtask);
        }

        return subtasks.get(id);
    }
    public Epic getEpic(int id) {
        if(!epics.containsKey(id)) return null;

        Epic currentEpic = epics.get(id);
        if(HM != null){
          HM.addTaskToHistory(currentEpic);
        }

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
        task.Epic currentEpic = epics.get(parentId);

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
            tasks.put(taskId, task);
        }

        return taskId;
    }

    public Integer updateEpic(Epic epic) {
        Integer epicId = epic.getId();
        if(epics.containsKey(epicId)){
            Epic current = epics.get(epicId);
            current.setDescription(epic.getDescription());
            current.setTitle(epic.getTitle());
        }

        return epicId;
    }

    public Integer updateSubtask(Subtask subtask) {
        Integer subtaskId = subtask.getId();

        if(subtasks.containsKey(subtaskId)){
            Subtask current = subtasks.get(subtaskId);
            Integer subtaskParentId = current.getParentId();
            Epic currentEpic = epics.get(subtaskParentId);

            subtasks.put(subtaskId, subtask);
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
            HM.removeTaskFromHistory(id);
        }
    }
    public void deleteEpic(int id) {
        if(epics.containsKey(id)){
            epics.remove(id);
            HM.removeTaskFromHistory(id);
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
            HM.removeTaskFromHistory(id);
        }

        if(currentEpic.getAllChildrenList().values().size() == 0){
            epics.remove(currentEpic.getId());
            HM.removeTaskFromHistory(currentEpic.getId());
        }
    }

    public void removeHistoryTask(Integer id){
        HM.removeTaskFromHistory(id);
    }

    public ArrayList<Task> getHistory(){
        return HM.getTasksHistory();
    }
}
