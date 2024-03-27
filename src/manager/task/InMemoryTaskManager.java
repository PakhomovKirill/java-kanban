package manager.task;

import manager.Managers;
import manager.history.HistoryManager;
import task.Epic;
import task.Subtask;
import task.Task;
import utils.Enums;
import utils.Utils;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InMemoryTaskManager implements TaskManager {

    private HistoryManager<Task> historyManager;
    private Managers managers = new Managers();
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private TreeMap<Long, Task> uniqueTimestampTaskList = new TreeMap<>();

    private List<Task> prioritizedList = new ArrayList<>();
    private int generatorId = 0;

    public InMemoryTaskManager() {
        historyManager = managers.getDefaultHistory();
    }

    public void parseTaskByTimestampValue(Enums.TaskActionType type, Object...args) {

        switch (type) {
            case REMOVE:
                Integer id = (Integer) List.of(args).get(0);
                uniqueTimestampTaskList.values().stream().filter(task -> task.getId() != id);
                break;

            case REMOVE_ALL_EPICS:
                uniqueTimestampTaskList.values().stream().filter(task -> task.getTaskType() != Enums.TasksType.EPIC.toString());
                break;

            case REMOVE_ALL_SUBTASKS:
                uniqueTimestampTaskList.values().stream().filter(task -> task.getTaskType() != Enums.TasksType.SUBTASK.toString());
                break;

            case REMOVE_ALL_TASKS:
                uniqueTimestampTaskList.values().stream().filter(task -> task.getTaskType() != Enums.TasksType.TASK.toString());
                break;

            case UPDATE_TASK_BY_ID:
                try {
                    Task task = (Task) List.of(args).get(0);
                    if (this.uniqueTimestampTaskList.containsKey(task.getStartTimeToSeconds())) {
                        this.uniqueTimestampTaskList.put(task.getStartTimeToSeconds(), task);
                    }
                } catch (IllegalStateException error) {
                    // println тк необходимо не останавливать выполнение потока программы
                    // throw new CustomTaskManagerException(error.getmessage());
                   System.out.println(error);
                }
                break;
            case CHECK_TO_UNIQUE_TIMESTAMP:
                try {
                    setUniqueTimestampTask((Task) List.of(args).get(0));
                } catch (IllegalStateException error) {
                    // println тк необходимо не останавливать выполнение потока программы
                    // throw new CustomTaskManagerException(error.getmessage());
                    System.out.println(error);
                }

                break;
            default:
                break;
        }

    }


    private boolean checkToUniqueTimeStamp(Task currentTask, Task newTask) {
        long  currentTaskEndTime = currentTask.getEndTimeToSeconds();
        long  newTaskStartTime = newTask.getStartTimeToSeconds();

        return currentTaskEndTime > newTaskStartTime ? true : false;
    }

    private void setUniqueTimestampTask(Task task) {

        if (task == null) {
            throw new IllegalStateException("Таск не существует");
        }

        if (task.getStartTimeToSeconds() == null) {
            throw new IllegalStateException("Таск не имеет время начала");
        }

        if (uniqueTimestampTaskList.size() == 0) {
            uniqueTimestampTaskList.put(task.getStartTimeToSeconds(), task);
        }

        uniqueTimestampTaskList.values().stream().forEach((item) -> {
            if (item.getStartTimeToSeconds() == null) {
                throw new IllegalStateException("Таск не имеет время начала");
            }

            if (checkToUniqueTimeStamp(item, task)) {
                throw new IllegalStateException("Время выполнения имеет пересечение с другими задачами");
            }
        });

        uniqueTimestampTaskList.put(task.getStartTimeToSeconds(), task);

        prioritizedList = Stream.concat(uniqueTimestampTaskList.values().stream(), this.epics.values().stream().filter(epic -> epic.getStartTimeToSeconds() != null)).collect(Collectors.toList());
    }

    private void updateEpicStatus(Integer epicId) {
        if (epicId == null) {
            return;
        }

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
        if (!tasks.containsKey(id)) {
            return null;
        }

        Task currentTask = tasks.get(id);
        if (historyManager != null) {
            historyManager.addTaskToHistory(currentTask);
        }

        return currentTask;
    }

    public Subtask getSubtask(int id) {
        if (!subtasks.containsKey(id)) {
            return null;
        }

        Subtask currentSubtask = subtasks.get(id);
        if (historyManager != null) {
            historyManager.addTaskToHistory(currentSubtask);
        }

        return subtasks.get(id);
    }

    public Epic getEpic(int id) {
        if (!epics.containsKey(id)) {
            return null;
        }

        Epic currentEpic = epics.get(id);
        if (historyManager != null) {
            historyManager.addTaskToHistory(currentEpic);
        }

        return epics.get(id);
    }

    // создание
    public int addNewTask(Task task) {
        task.setId(++this.generatorId);

        if (!this.tasks.containsKey(task.getId())) {
            this.tasks.put(task.getId(), task);
        }

        return task.getId();
    }

    public int addNewEpic(Epic epic) {
        epic.setId(++this.generatorId);

        if (!this.epics.containsKey(epic.getId())) {
            this.epics.put(epic.getId(), epic);
        }

        updateEpicStatus(epic.getId());

        return epic.getId();
    }

    public Integer addNewSubtask(Subtask subtask) {
        Integer parentId = subtask.getParentId();
        task.Epic currentEpic = epics.get(parentId);

        if (currentEpic == null) {
            return null;
        }

        subtask.setId(++this.generatorId);
        Integer subtaskId = subtask.getId();

        if (!this.subtasks.containsKey(subtaskId)) {
            this.subtasks.put(subtaskId, subtask);
            currentEpic.setChildSubtask(subtask);
        }

        updateEpicStatus(subtask.getParentId());

        return subtaskId;
    }

    // обновление
    public Integer updateTask(Task task) {
        Integer taskId = task.getId();
        if (tasks.containsKey(task.getId())) {
            tasks.put(taskId, task);
        }

        return taskId;
    }

    public Integer updateEpic(Epic epic) {
        Integer epicId = epic.getId();
        if (epics.containsKey(epicId)) {
            Epic current = epics.get(epicId);
            current.setDescription(epic.getDescription());
            current.setTitle(epic.getTitle());
        }

        return epicId;
    }

    public Integer updateSubtask(Subtask subtask) {
        Integer subtaskId = subtask.getId();

        if (subtasks.containsKey(subtaskId)) {
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
        if (tasks.containsKey(id)) {
            tasks.remove(id);
            historyManager.removeTaskFromHistory(id);
        }
    }

    public void deleteEpic(int id) {
        if (epics.containsKey(id)) {
            epics.remove(id);
            historyManager.removeTaskFromHistory(id);
        }

        subtasks.entrySet()
                .stream()
                .forEach(subtask -> {
                    if (subtask.getValue().getId() == id) {
                        subtasks.remove(subtask.getValue().getId());
                    }
                });
    }

    public void deleteSubtask(int id) {
        Subtask currentSubtask = subtasks.get(id);
        Integer currentSubtaskParentId = currentSubtask.getParentId();
        Epic currentEpic = epics.get(currentSubtaskParentId);

        if (currentSubtaskParentId != null) {
            currentEpic.removeChildSubtask(id);
        }

        if (subtasks.containsKey(id)) {
            subtasks.remove(id);
            historyManager.removeTaskFromHistory(id);
        }

        if (currentEpic.getAllChildrenList().values().size() == 0) {
            epics.remove(currentEpic.getId());
            historyManager.removeTaskFromHistory(currentEpic.getId());
        }
    }

    public void removeHistoryTask(Integer id) {
        historyManager.removeTaskFromHistory(id);
    }

    public ArrayList<Task> getHistory() {
        return historyManager.getTasksHistory();
    }

    public List<Task> getPrioritizedTasks() {
        return this.prioritizedList;
    }
}
