package task;

import utils.Enums;

public class Subtask extends Task {
    private Integer parentId;

    public Subtask(String title, String description, Enums.TaskStatus status, int parentId){
        super(title, description, status);
        this.parentId = parentId;
    }

    public Subtask(String title, String description, Enums.TaskStatus status){
        super(title, description, status);
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
