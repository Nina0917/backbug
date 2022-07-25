package com.yuanning.backbug.controller;

import com.yuanning.backbug.entity.AppUser;
import com.yuanning.backbug.entity.Project;
import com.yuanning.backbug.entity.request.AddProjectRequest;
import com.yuanning.backbug.exceptionHandler.Result;
import com.yuanning.backbug.service.ProjectService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/project")
@AllArgsConstructor
@CrossOrigin
//@NoArgsConstructor
public class ProjectController {

    // 私有成员变量必须在initialize时赋值，使用@NoArgsConstructor时报错是因为the constructor does not initialize any fields
    private final ProjectService projectService;

    @PostMapping("add")
    public Result<String> addProject(HttpServletRequest request, @RequestBody AddProjectRequest projectRequest) {
        return projectService.addProject(request, projectRequest);
    }

    @GetMapping("getAll")
    public Result<List<Project>> getAllProjects(@RequestParam int page, HttpServletRequest request) {
        return projectService.getAllProjects(page, request);
    }

    /**
     * Get the corresponding members under the project
     */
    @GetMapping("getMembers")
    public Result<List<AppUser>> getMembers(@RequestParam Long projectId) {
        return projectService.getMembers(projectId);
    }



}
