package com.springboot.MyTodoList.messageModel;

import java.util.List;

import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.springboot.MyTodoList.controller.ProjectController;
import com.springboot.MyTodoList.controller.ProjectMemberController;
import com.springboot.MyTodoList.controller.SubtaskController;
import com.springboot.MyTodoList.model.Project;
import com.springboot.MyTodoList.model.ProjectMember;
import com.springboot.MyTodoList.model.Subtask;
import com.springboot.MyTodoList.model.Task;
import com.springboot.MyTodoList.model.User;

@Service
public class SubtaskMessageModel implements MessageModel<Subtask> {
    private SubtaskController subtaskController;

    @Autowired
    private ProjectMemberController projectMemberController;

    @Autowired
    private ProjectController projectController;

    public SubtaskMessageModel(SubtaskController subtaskController) {
        this.subtaskController = subtaskController;
    }

    @Override
    public String reportSingle(int id, User user) {
        ResponseEntity<Subtask> subtaskEntity = subtaskController.getSubtaskById(id);
        if (subtaskEntity.getStatusCodeValue() == 200) {
            Subtask exisitngSubtask = subtaskEntity.getBody();
            return exisitngSubtask.publicDescription();
        } else {
            return "La subtarea no fue encontrado";
        }
    }

    @Override
    public String reportAll(User user) {
        List<ProjectMember> projectMembers = projectMemberController.getProjectMembersByUserId(user.getID());
        if (projectMembers.size() == 0) {
            return "El usuario no tiene tareas";
        }

        StringBuilder sb = new StringBuilder();

        for (ProjectMember projectMember : projectMembers) {
            int project_id = projectMember.getProject_id();
            ResponseEntity<Project> projectEntity = projectController.getProjectById(project_id);
            if (projectEntity.getStatusCodeValue() != 200) {
                continue;
            }
            Project project = projectEntity.getBody();
            for (Task task : project.getTasks()) {
                sb.append(task.quickDescription());
                sb.append("\n");
                for (Subtask subtask : task.getSubtasks()) {
                    sb.append(subtask.publicDescription());
                    sb.append("\n");
                }
            }
        }

        return sb.toString();
    }

    @Override
    public String reportSpecific(int id) {
        ResponseEntity<Subtask> subtaskEntity = subtaskController.getSubtaskById(id);
        if (subtaskEntity.getStatusCodeValue() == 200) {
            Subtask task = subtaskEntity.getBody();
            return task.publicDescription();
        } else {
            return "La subtarea no fue encontrada";
        }
    }

    @Override
    public String update(int id, Subtask subtask) {
        ResponseEntity<Subtask> subtaskEntity = subtaskController.updateSubtask(subtask, id);
        if (subtaskEntity.getStatusCodeValue() == 200) {
            return "Subtarea actualizada";
        } else {
            return "No se pudo actualizar la subtarea";
        }
    }

    @Override
    public String delete(int id) {
        ResponseEntity<Boolean> subtaskEntity = subtaskController.deleteSubtask(id);
        if (subtaskEntity.getStatusCodeValue() == 200) {
            return "Subtarea eliminada";
        } else {
            return "No se pudo eliminar la tarea";
        }
    }
}
