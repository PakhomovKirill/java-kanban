package task;
import utils.Enums;
import java.util.HashMap;

public class Epic extends Task {
  private HashMap<Integer, Subtask> childList = new HashMap<>();

  public Epic(String title, String description){
    super(title, description, Enums.TaskStatus.NEW);
  }

  public Epic(String title, String description, Integer taskId){
    super(title, description, Enums.TaskStatus.NEW, taskId);
  }

  public void setStatus(Enums.TaskStatus status){
    super.setStatus(status);
  }

  public void setChildSubtask(Subtask subtask){
    this.childList.put(subtask.getId(), subtask);
  }

  public void removeChildSubtask(int subtaskId){
    this.childList.remove(subtaskId);
  }

  public HashMap<Integer, Subtask> getAllChildrenList () {
    return this.childList;
  }

  public void updateChildList(int subtaskId, Subtask subtask){
    Subtask current = this.childList.get(subtaskId);
    current.setDescription(subtask.getDescription());
    current.setTitle(subtask.getTitle());
    current.setStatus(subtask.getStatus());
  }

  public void clearChildSubtasks(){
    this.childList.clear();
  }

  @Override
  public String toString() {
    String result = "Epic{" +
            "id='" + this.getId()+ '\'' +
            "status='" + this.getStatus() + '\'' +
            ",title='" + this.getTitle() + '\'' +
            ",description=" + this.getDescription() + '\'';

    return result + ",tasks=" + this.getAllChildrenList().toString() + '}';
  }
}
