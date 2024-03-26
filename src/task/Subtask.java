package task;
import utils.Enums;
import java.time.Duration;
import java.util.Objects;

public class Subtask extends Task {
    private Integer parentId;

    public Subtask(String title, String description, Enums.TaskStatus status, Integer parentId){
        super(title, description, status);
        this.parentId = parentId;
        super.setTaskType(Enums.TasksType.SUBTASK);
    }

    public Subtask(String title, String description, Enums.TaskStatus status, Integer parentId, Integer subtaskId){
        super(title, description, status);
        this.setId(subtaskId);
        this.parentId = parentId;
        super.setTaskType(Enums.TasksType.SUBTASK);
    }

    public Subtask(String title, String description, Enums.TaskStatus status, Integer parentId, String startTime, Duration taskEstimateDuration){
        super(title, description, status, startTime, taskEstimateDuration);
        this.parentId = parentId;
        super.setTaskType(Enums.TasksType.SUBTASK);
    }

    public Subtask(String title, String description, Enums.TaskStatus status, Integer parentId, Integer subtaskId, String startTime, Duration taskEstimateDuration){
        super(title, description, status, startTime, taskEstimateDuration);
        this.setId(subtaskId);
        this.parentId = parentId;
        super.setTaskType(Enums.TasksType.SUBTASK);
    }

    public void setParentId(int parentId){
        this.parentId = parentId;
    }

    public Integer getParentId(){
        return this.parentId;
    }

    @Override
    public String toString() {
        String result =
                this.getId() +
                ","+ this.getTaskType() +
                "," + this.getTitle() +
                "," + this.getStatus() +
                "," + this.getDescription() +
                "," + this.getParentId() +
                "," + this.getStartTimeFormatted() +
                "," + this.getEndTimeFormatted();

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        Subtask otherTask = (Subtask) obj;
        return Objects.equals(title, otherTask.title) &&
                Objects.equals(description, otherTask.description) &&
                Objects.equals(id, otherTask.id) &&
                Objects.equals(parentId, otherTask.parentId) &&
                Objects.equals(status, otherTask.status);
    }
}
