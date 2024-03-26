package manager.history;

import java.util.ArrayList;

public interface HistoryManager<T> {

   public void addTaskToHistory(T task);

   public void removeTaskFromHistory(Integer id);

   public ArrayList<T> getTasksHistory();
}
