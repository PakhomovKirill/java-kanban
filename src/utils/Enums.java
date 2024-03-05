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
        epic
    }

    public enum TasksType{
        SUBTASK,
        EPIC,
        TASK
    }

    public enum TaskManagerClassTypes {
        DEFAULT
    }
}
