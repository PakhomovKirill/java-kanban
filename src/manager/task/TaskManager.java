package manager.task;

import task.Epic;
import task.Subtask;
import task.Task;

import java.util.ArrayList;

public interface TaskManager {

    public int addNewTask(Task task);

    public int addNewEpic(Epic epic);

    public ArrayList<Subtask> getSubtasks();

    public ArrayList<Task> getTasks();

    public ArrayList<Epic> getEpics();

    public ArrayList<Subtask> getEpicSubtasks(int epicId);

    public Task getTask(int id);

    public Subtask getSubtask(int id);

    public Epic getEpic(int id);

    public Integer addNewSubtask(Subtask subtask);

    public Integer updateTask(Task task);

    public void updateEpic(Epic epic);

    public Integer updateSubtask(Subtask subtask);

    public void deleteTasks();

    public void deleteSubtasks();

    public void deleteEpics();

    public void deleteTask(int id);

    public void deleteEpic(int id);

    public void deleteSubtask(int id);

    public ArrayList<Task> getHistory();

    public void removeHistoryTask(Integer id);

}
