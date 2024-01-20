package task;

import utils.Enums.TaskStatus;
import java.util.Objects;

public class Task {
    public String title;
    public String description;
    public Integer id;
    public TaskStatus status = TaskStatus.NEW;

    public String getTitle(){
        return this.title;
    }

    public String getDescription(){
        return this.description;
    }

    public Integer getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }
    public TaskStatus getStatus(){
        return this.status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStatus(TaskStatus status){
        this.status = status;
    }

    public Task(String title, String description, TaskStatus status){
        this.description = description;
        this.title = title;
        this.status = status;
    }

    public Task(String title, String description, TaskStatus status, Integer taskId){
        this.description = description;
        this.title = title;
        this.id = taskId;
        this.status = status;
    }

    @Override
    public String toString() {
        String result = "Subtask{" +
                "id='" + this.getId() + '\'' +
                "status='" + this.getStatus() + '\'' +
                ",title='" + this.getTitle() + '\'' +
                ",description=" + this.getDescription() + '}';

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        Task otherTask = (Task) obj;
        return Objects.equals(title, otherTask.title) &&
                Objects.equals(description, otherTask.description) &&
                Objects.equals(id, otherTask.id) &&
                Objects.equals(status, otherTask.status);
    }
}
