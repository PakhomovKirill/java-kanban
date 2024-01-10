package task;
import utils.Enums;

public class Subtask extends Task {
    private Integer parentId;

    public Subtask(String title, String description, Enums.TaskStatus status, Integer parentId){
        super(title, description, status);
        this.parentId = parentId;
    }

    public Subtask(String title, String description, Enums.TaskStatus status, Integer parentId, Integer subtaskId){
        super(title, description, status);
        this.setId(subtaskId);
        this.parentId = parentId;
    }

    public void setParentId(int parentId){
        this.parentId = parentId;
    }

    public Integer getParentId(){
        return this.parentId;
    }

    @Override
    public String toString() {
        String result = "Subtask{" +
                "id='" + this.getId() + '\'' +
                "parentId='" + this.getParentId() + '\'' +
                "status='" + this.getStatus() + '\'' +
                ",title='" + this.getTitle() + '\'' +
                ",description=" + this.getDescription() + '}';

        return result;
    }
}
