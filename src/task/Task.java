package task;

import utils.Enums.TaskStatus;

public class Task {
    private String title;
    private String description;
    private Integer id;
    private TaskStatus status = TaskStatus.NEW;

    public Task(String title, String description, TaskStatus status){
      this.description = description;
      this.title = title;
      //this.id = getUniqueId(uuid.toString())[1];
      this.status = status;
    }

    public Task(String title, String description, TaskStatus status, Integer taskId){
        this.description = description;
        this.title = title;
        this.id = taskId;
        this.status = status;
    }

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
}
