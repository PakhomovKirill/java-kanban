package utils;
import task.Epic;
import task.Subtask;
import task.Task;

import java.util.HashMap;
import java.util.Map;
public class Utils {
    public static boolean isEpicListStatusDoneAll(Map<Integer, Subtask> subtasks) {
        for (Subtask subtask : subtasks.values()) {
            if (subtask.getStatus() != Enums.TaskStatus.DONE) {
                return false;
            }
        }

        return true;
    }

    public static boolean isEpicListInProgress(Map<Integer, Subtask> subtasks) {
        for (Subtask subtask : subtasks.values()) {
            if (subtask.getStatus() == Enums.TaskStatus.IN_PROGRESS) {
                return true;
            }
        }

        return false;
    }

    public static boolean isEpicsListHasItemById (Integer id, HashMap<Integer, Epic> epics){
        return epics.containsKey(id);
    }

    public static boolean isTasksListHasItemById (Integer id, HashMap<Integer, Task> tasks){
        return tasks.containsKey(id);
    }

    public static boolean isSubtasksListHasItemById (Integer id, HashMap<Integer, Subtask> subtasks){
        return subtasks.containsKey(id);
    }
}
