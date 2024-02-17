package manager.history;

import task.Task;
import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryHistoryManager<T extends Task> implements HistoryManager<T>{
    HashMap<Integer, Node> nodeList = new HashMap<>();
    DoubleLinkedList linkedList = new DoubleLinkedList();

    public void addTaskToHistory (T task){
        int taskId = task.getId();

        if(this.nodeList.containsKey(taskId)){
            removeTaskFromHistory(taskId);
        }

        Node addNode = linkedList.addLastToList(task);
        this.nodeList.put(taskId, addNode);
    }

    public void removeTaskFromHistory(Integer taskId){
        Node removedNode = this.nodeList.remove(taskId);
        this.linkedList.removeFromList(removedNode);
    }

    public ArrayList<T> getTasksHistory(){
        return this.linkedList.getList();
    }
}
