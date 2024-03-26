package task;

import utils.Enums;
import utils.Enums.TaskStatus;
import utils.Enums.TimeEnum;
import utils.Utils;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Objects;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Task extends ArrayList <Task> implements Comparable <Task> {
    public String title;
    public String description;
    public TaskStatus status = TaskStatus.NEW;
    public Integer id;
    public ZonedDateTime startTime;
    public ZonedDateTime endTime;
    public Duration taskEstimateDuration;
    private Enums.TasksType taskType = Enums.TasksType.TASK;


    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public Integer getId(){
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public TaskStatus getStatus() {
        return this.status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public void setTaskType(Enums.TasksType type) {
        this.taskType = type;
    }

    public Task(String title, String description, TaskStatus status) {
        this.description = description;
        this.title = title;
        this.status = status;
    }

    public Task(String title, String description, TaskStatus status, Integer taskId) {
        this.description = description;
        this.title = title;
        this.id = taskId;
        this.status = status;
    }

    public Task(String title, String description, TaskStatus status, String startTime, Duration taskEstimateDuration) {
        this.description = description;
        this.title = title;
        this.status = status;
        this.startTime = ZonedDateTime.of(LocalDateTime.parse(startTime, Utils.dateTimeFormatter(Enums.TimeEnum.DATE_MASK)), ZoneId.of(TimeEnum.TIMEZONE_MOSCOW.toString()));
        this.taskEstimateDuration = taskEstimateDuration;
        this.endTime = this.startTime.plus(this.taskEstimateDuration);
    }

    public Task(String title, String description, TaskStatus status, Integer taskId, String startTime, Duration taskEstimateDuration) {
        this.description = description;
        this.title = title;
        this.id = taskId;
        this.status = status;
        this.startTime = ZonedDateTime.of(LocalDateTime.parse(startTime, Utils.dateTimeFormatter(Enums.TimeEnum.DATE_MASK)), ZoneId.of(TimeEnum.TIMEZONE_MOSCOW.toString()));
        this.taskEstimateDuration = taskEstimateDuration;
        this.endTime = this.startTime.plus(this.taskEstimateDuration);
    }

    public Long getEndTimeToSeconds() {
        if(this.endTime != null){
            return this.endTime.toEpochSecond();
        }

        return null;
    }

    public Long getStartTimeToSeconds() {
        if(this.startTime != null){
            return this.startTime.toEpochSecond();
        }

        return null;
    }

    public ZonedDateTime getStartTime() {
        if(this.startTime != null){
            return this.startTime;
        }

        return null;
    }

    public ZonedDateTime getEndTime() {
        if(this.endTime != null){
            return this.endTime;
        }

        return null;
    }

    public String getStartTimeFormatted() {
        if(this.startTime != null){
            return this.startTime.format(Utils.dateTimeFormatter(Enums.TimeEnum.DATE_MASK));
        }

        return null;
    }

    public String getEndTimeFormatted() {
        if(this.endTime != null){
            return this.endTime.format(Utils.dateTimeFormatter(Enums.TimeEnum.DATE_MASK));
        }

        return null;
    }

    public String getTaskType() {
        return this.taskType.toString();
    }

    @Override
    public String toString() {
        String result =
                this.getId()+
                ","+ this.getTaskType() +
                "," + this.getTitle()+
                "," + this.getStatus() +
                "," + this.getDescription() +
                ",null" +
                "," + this.getStartTimeFormatted() +
                "," + this.getEndTimeFormatted();

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

    @Override
    public int compareTo(Task o1) {
        return (o1.getTaskType() == Enums.TasksType.EPIC.toString()) ? -1 : 1;
    }
}
