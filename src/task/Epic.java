package task;
import utils.Enums;
import java.util.HashMap;
import java.util.Objects;

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
    this.childList.put(subtaskId, subtask);
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

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (this.getClass() != obj.getClass()) return false;
    Epic otherTask = (Epic) obj;
    return Objects.equals(title, otherTask.title) &&
            Objects.equals(description, otherTask.description) &&
            Objects.equals(id, otherTask.id) &&
            Objects.equals(childList.toString(), otherTask.childList.toString()) &&
            Objects.equals(status, otherTask.status);
  }
}
