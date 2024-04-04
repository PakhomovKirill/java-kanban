package task;
import utils.Enums;
import java.util.*;

public class Epic extends Task {
  private HashMap<Integer, Subtask> childList = new HashMap<>();

  public Epic(String title, String description) {
    super(title, description, Enums.TaskStatus.NEW);
    super.setTaskType(Enums.TasksType.EPIC);

    setEndStartTime();
  }

  public Epic(String title, String description, Integer taskId) {
    super(title, description, Enums.TaskStatus.NEW, taskId);
    super.setTaskType(Enums.TasksType.EPIC);

    setEndStartTime();
  }

  private void setEndStartTime() {
    TreeMap<Long, Task> start = new TreeMap<>();
    TreeMap<Long,Task> end = new TreeMap<>();

    if (this.childList.size() == 0) {
      return;
    }

    this.childList.values().stream().forEach(item -> {
      Long time = item.getStartTimeToSeconds();

      if (time != null) {
        start.put(time, item);
        end.put(time, item);
      }
    });

    ArrayList<Task> list = new ArrayList<>(end.values());

    this.startTime = !start.values().stream().findFirst().isEmpty() ? start.values().stream().findFirst().get().getStartTime() : null;
    this.startTime =  list.size() != 0 ? list.get(list.size() - 1).getEndTime() : null;

  }

  public void setStatus(Enums.TaskStatus status) {
    super.setStatus(status);
  }

  public void setChildSubtask(Subtask subtask) {

    this.childList.put(subtask.getId(), subtask);

    setEndStartTime();
  }

  public void removeChildSubtask(int subtaskId) {
    this.childList.remove(subtaskId);
  }

  public HashMap<Integer, Subtask> getAllChildrenList() {
    return this.childList;
  }

  public void updateChildList(int subtaskId, Subtask subtask) {
    this.childList.put(subtaskId, subtask);
  }

  public void clearChildSubtasks() {
    this.childList.clear();
  }

  @Override
  public String toString() {
    String result = this.getId() + "," + this.getTaskType() + "," + this.getTitle() + "," + this.getStatus() + "," + this.getDescription() + ",null" + "," + this.getStartTimeFormatted() + "," + this.getEndTimeFormatted();

    return result + "," + this.getAllChildrenList().toString() + "";
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
