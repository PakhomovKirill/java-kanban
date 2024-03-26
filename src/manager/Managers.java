package manager;

import manager.task.InMemoryTaskManager;
import manager.history.InMemoryHistoryManager;
import manager.task.TaskManager;
import manager.history.HistoryManager;
import manager.task.FileBackedTaskManager;

public class Managers {

    public TaskManager getDefault(){
        return new FileBackedTaskManager();
    }

    public HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public InMemoryTaskManager inMemoryTaskManager() {
        return new InMemoryTaskManager();
    }
}
