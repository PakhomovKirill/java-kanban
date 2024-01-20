package manager;

import manager.task.InMemoryTaskManager;
import manager.history.InMemoryHistoryManager;

public class Managers {
    private InMemoryTaskManager DefaultManager;
    private InMemoryHistoryManager HistoryManager;

    public Managers(){
        HistoryManager = new InMemoryHistoryManager(10);
        DefaultManager = new InMemoryTaskManager(HistoryManager);
    }

    public InMemoryTaskManager getDefaultClass (){
        return DefaultManager;
    }

    public InMemoryHistoryManager getDefaultHistory(){
       return HistoryManager;
    }
}
