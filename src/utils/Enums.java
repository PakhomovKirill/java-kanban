package utils;

public class Enums {
    public enum CommandsIndex {
        CREATE_TASK,
        CREATE_SUBTASK,
        CREATE_EPIC,
        UPDATE_TASK,
        UPDATE_SUBTASK,
        UPDATE_EPIC,
        GET_EPIC_BY_ID,
        GET_SUBTASK_BY_ID,
        GET_TASK_BY_ID,
        REMOVE_ALL_TASKS,
        REMOVE_ALL_SUBTASKS,
        REMOVE_ALL_EPICS,
        REMOVE_TASK_BY_ID,
        REMOVE_SUBTASK_BY_ID,
        REMOVE_EPIC_BY_ID,
        GET_EPICS,
        GET_TASKS,
        GET_SUBTASKS,
        EXIT,
        DEFAULT
    }

    public enum TaskStatus {
        NEW,
        DONE,
        IN_PROGRESS
    }

    public enum TasksType {
        TASK,
        EPIC,
        SUBTASK
    }
}
