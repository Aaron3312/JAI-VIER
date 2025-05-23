package com.springboot.MyTodoList.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "SUBTASK")
public class Subtask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int ID;
    @Column(name = "TITLE")
    String title;
    @Column(name = "DESCRIPTION")
    String description;
    @Column(name = "CREATED_AT")
    OffsetDateTime created_at;
    @Column(name = "UPDATED_AT")
    OffsetDateTime updated_at;
    @Column(name = "STATUS")
    int status;
    @JsonBackReference(value = "task-subtasks")
    @ManyToOne
    @JoinColumn(name = "TASK_ID", nullable = false)
    private Task task;
    @Transient
    private int task_id;

    public Subtask() {
    }

    public Subtask(int ID, String title, String description, OffsetDateTime created_at, OffsetDateTime updated_at,
            int status, Task task) {
        this.ID = ID;
        this.title = title;
        this.description = description;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.status = status;
        this.task = task;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OffsetDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(OffsetDateTime created_at) {
        this.created_at = created_at;
    }

    public OffsetDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(OffsetDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public int getTask_id() {
        return task != null ? task.getID() : task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "ID=" + ID +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", status=" + status +
                ", task_id=" + task_id +
                '}';
    }

    public String publicDescription() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return String.format(
                "  🔹 %s, 🆔 ID: %d\n" +
                        "     📝 Description: %s\n" +
                        "     📅 Created: %s | 🔄 Updated: %s\n" +
                        "     📌 Status: %s",
                title, ID, description, created_at.format(formatter), updated_at.format(formatter), statusText());
    }

    public String quickDescription() {
        return String.format("🆔 ID: %d | 📝 %s | 🔄 Status: %s", title, statusText());
    }

    private String statusText() {
        switch (status) {
            case 0:
                return "❌ Not Started";
            case 1:
                return "⏳ In Progress";
            case 2:
                return "✅ Completed";
            default:
                return "⚠️ Unknown";
        }
    }

}
