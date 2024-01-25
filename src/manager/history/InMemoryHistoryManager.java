package manager.history;

import java.util.ArrayList;

public class InMemoryHistoryManager<T> implements HistoryManager<T>{

    ArrayList<T> historyTasks = new ArrayList<T>();
    int historyListLimitValue;

    public InMemoryHistoryManager (int maxSize){
        this.historyListLimitValue = maxSize;
    }

    public void addTaskToHistory (T task){
        // прошу прощения, тупая привычка не ставить скобки + в моей практике обычно линтер все за меня делает) , хотел уточнить, а какой линтер лучше всего в использовать для java
        if(task == null) { return; }

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
