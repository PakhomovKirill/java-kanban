package utils;
import task.Subtask;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;

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

    public static DateTimeFormatter dateTimeFormatter(Enums.TimeEnum dateTimeFormat) {
       return DateTimeFormatter.ofPattern(dateTimeFormat.toString());
    }
}
