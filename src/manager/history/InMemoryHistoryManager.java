package manager.history;

import java.util.ArrayList;

public class InMemoryHistoryManager<T> implements HistoryManager<T>{

    ArrayList<T> historyTasks = new ArrayList<T>();
    int historyListLimitValue;

    public InMemoryHistoryManager(int maxSize){
        this.historyListLimitValue = maxSize;
    }
    public void addTaskToHistory(T task){
        if(task == null) return;

        int size = this.historyTasks.size();
        this.historyTasks.add(task);

        if(size >= this.historyListLimitValue) {
            this.historyTasks.remove(0);
        }
    }

    public ArrayList<T> getTasksHistory(){
       return this.historyTasks;
    }
}
