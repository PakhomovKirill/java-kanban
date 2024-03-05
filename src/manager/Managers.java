package manager;

import manager.task.InMemoryTaskManager;
import manager.history.InMemoryHistoryManager;
import manager.task.TaskManager;
import manager.history.HistoryManager;
import manager.task.FileBackedTaskManager;

public class Managers {
    public TaskManager getDefault() {
        return new InMemoryTaskManager();
    }
    public HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public FileBackedTaskManager getFileBackedTaskManager(){
        return new FileBackedTaskManager();
    }
}
