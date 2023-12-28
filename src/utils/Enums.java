package utils;

public class Enums {
    public enum CommandsIndex {
        CREATE,
        UPDATE,
        REMOVE_ALL,
        REMOVE_BY_ID,
        GET_EPIC_BY_ID,
        GET_TASK_BY_ID,
        EXIT,
        DEFAULT
    }

    public enum TaskTypeCommand {
        CREATE_TASK,
        UPDATE_TASK,
        REMOVE_BY_ID,
        GET_TASK_BY_ID,
        GET_EPIC_BY_ID,
        REMOVE_ALL,
        CREATE_EPIC,
        CREATE_SUBTASK,
        DEFAULT;

        public static TaskTypeCommand getEnumByType(CommandsIndex command, Integer key){
            if(command == CommandsIndex.CREATE && key == 1) { return CREATE_TASK; }
            if(command == CommandsIndex.CREATE && key == 2) { return CREATE_EPIC; }
            if(command == CommandsIndex.CREATE && key == 3) { return CREATE_SUBTASK; }
            if(command == CommandsIndex.GET_TASK_BY_ID) { return GET_TASK_BY_ID; }
            if(command == CommandsIndex.GET_EPIC_BY_ID) { return GET_EPIC_BY_ID; }
            if(command == CommandsIndex.REMOVE_ALL) { return REMOVE_ALL; }
            if(command == CommandsIndex.REMOVE_BY_ID) { return REMOVE_BY_ID; }
            if(command == CommandsIndex.UPDATE) { return UPDATE_TASK; }

            return DEFAULT;
        }
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
