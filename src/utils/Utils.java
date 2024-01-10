package utils;
import task.Subtask;
import java.util.ArrayList;

public class Utils {
    public static boolean isEpicListStatusDoneAll(ArrayList<Subtask> subtasks) {
        for (Subtask subtask : subtasks) {
            if (subtask.getStatus() != Enums.TaskStatus.DONE) {
                return false;
            }
        }

        return true;
    }

    public static boolean isEpicListInProgress(ArrayList<Subtask> subtasks) {
        for (Subtask subtask : subtasks) {
            if (subtask.getStatus() == Enums.TaskStatus.IN_PROGRESS) {
                return true;
            }
        }

        return false;
    }
}
