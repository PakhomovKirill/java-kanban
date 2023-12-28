package task;

import utils.Enums;

public class Subtask extends Task {
    private String parentId;
    public Subtask(String title, String description, Enums.TaskStatus status, String parentId){
        super(title, description, status);
        this.parentId = parentId;
    }

    public Subtask(String title, String description, Enums.TaskStatus status){
        super(title, description, status);
    }

    public void setParentId(String parentId){
        this.parentId = parentId;
    }

    public String getParentId(){
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
