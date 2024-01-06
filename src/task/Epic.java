package task;

import manager.TaskManager;
import utils.Enums;

import java.util.HashMap;
import java.util.Map;

public class Epic extends Task {
  private HashMap<String, Subtask> childList = new HashMap<>();
  private TaskManager TM = new TaskManager();

  public Epic(String title, String description){
    super(title, description, Enums.TaskStatus.NEW);
  }

  public Epic(String title, String description, Integer taskId){
    super(title, description, Enums.TaskStatus.NEW, taskId);
  }

  public void setStatus(Enums.TaskStatus status){
    super.setStatus(status);
  }

  public Map<Integer, Subtask> getAllChildren(){
    return TM.getEpicSubtasks(this.getId());
  }

  @Override
  public String toString() {
    String result = "Epic{" +
            "id='" + this.getId()+ '\'' +
            "status='" + this.getStatus() + '\'' +
            ",title='" + this.getTitle() + '\'' +
            ",description=" + this.getDescription() + '\'';

    return result + ",tasks=" + this.getAllChildren().toString() + '}';
  }
}
