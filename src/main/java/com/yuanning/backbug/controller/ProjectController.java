package com.yuanning.backbug.controller;

import com.yuanning.backbug.entity.User;
import com.yuanning.backbug.entity.Project;
import com.yuanning.backbug.entity.request.AddProjectRequest;
import com.yuanning.backbug.exceptionHandler.Result;
import com.yuanning.backbug.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(path = "api/projects")
@AllArgsConstructor
@CrossOrigin
//@NoArgsConstructor
public class ProjectController {

    // 私有成员变量必须在initialize时赋值，使用@NoArgsConstructor时报错是因为the constructor does not initialize any fields
    private final ProjectService projectService;

    @PostMapping("add-project")
    public Result<String> addProject(@RequestBody AddProjectRequest projectRequest) {
        return projectService.addProject(projectRequest);
    }

    @GetMapping("get-all-projects")
    public Result<List<Project>> getAllProjects(@RequestParam(defaultValue = "0") int page) {
        return projectService.getAllProjects(page);
    }

    /**
     * Get the corresponding members under the project
     */
    @GetMapping("get-members")
    public Result<List<User>> getMembers(@RequestParam Long projectId) {
        return projectService.getMembers(projectId);
    }



}
