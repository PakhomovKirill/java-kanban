package utils;

public class Enums {
    public enum TaskStatus {
        NEW,
        DONE,
        IN_PROGRESS
    }

    public enum csvTableHeaders {
        id,
        type,
        name,
        status,
        description,
        epic,
        startTime,
        endTime
    }

    public enum TasksType{
        SUBTASK,
        EPIC,
        TASK
    }

    public enum TaskActionType{
        REMOVE,
        CHECK_TO_UNIQUE_TIMESTAMP,
        REMOVE_ALL_TASKS,
        REMOVE_ALL_SUBTASKS,
        REMOVE_ALL_EPICS,
        UPDATE_TASK_BY_ID
    }

    public enum TimeEnum {
        DATE_MASK {
            @Override
            public String toString() {
                return "dd.MM.yy HH:mm";
            }
        },
        TIMEZONE_MOSCOW {
            @Override
            public String toString() {
                return "Europe/Moscow";
            }
        },
    }
}
