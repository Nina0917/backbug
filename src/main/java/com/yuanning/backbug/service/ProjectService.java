package com.yuanning.backbug.service;

import com.yuanning.backbug.entity.*;
import com.yuanning.backbug.entity.messageEnum.AppUserRole;
import com.yuanning.backbug.entity.request.AddProjectRequest;
import com.yuanning.backbug.entity.request.ProjectInfoResult;
import com.yuanning.backbug.exceptionHandler.MessageEnum;
import com.yuanning.backbug.exceptionHandler.MessageUtil;
import com.yuanning.backbug.exceptionHandler.Result;
import com.yuanning.backbug.repository.AppUserRepository;
import com.yuanning.backbug.repository.ProjectManageRepository;
import com.yuanning.backbug.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@AllArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final AppUserRepository appUserRepository;
    private final ProjectManageRepository projectManageRepository;


    // 添加新项目
    public Result<String> addProject(HttpServletRequest request, AddProjectRequest projectRequest) {

        // 判断新项目的名字和描述是否为空
        if (projectRequest.getName() == "" || projectRequest.getDescription() == "") {
            return MessageUtil.error(MessageEnum.EMPTY_FIELD.getCode(),MessageEnum.EMPTY_FIELD.getMessage());
        }


        // 添加此项目相关联的用户
        List<Long> appUserIds = projectRequest.getAppUserIds();
        // 获取当前user
        String email = (String)request.getSession().getAttribute("email");
        AppUser appUser = appUserRepository.findByEmail(email).get();
        Long user_id = appUser.getId();


        // 添加新项目
        Project project = new Project();
        project.setName(projectRequest.getName());
        project.setDescription(projectRequest.getDescription());
        project.setManager(appUser);
        // 当前项目的id
        Long project_id = projectRepository.save(project).getId();

        // 添加user_id和project_id到project_manage
        for (int i = 0; i < appUserIds.size(); i++) {
            ProjectManage projectManage = new ProjectManage();
            projectManage.setProject_id(project_id);
            projectManage.setAppUser_id(appUserIds.get(i));
            projectManage.setRole(AppUserRole.USER);
            projectManageRepository.save(projectManage);
        }
        ProjectManage projectManage = new ProjectManage();
        projectManage.setProject_id(project_id);
        projectManage.setAppUser_id(user_id);
        projectManage.setRole(AppUserRole.Project_Manager);
        projectManageRepository.save(projectManage);


        return MessageUtil.success();
    }

    public Result<List<Project>> getAllProjects(int page, HttpServletRequest request) {
        // get current user's email
        String email =  (String)request.getSession().getAttribute("email");
        // if user not login in yet
        if (email == "" || email == null) {
            return MessageUtil.error(MessageEnum.User_not_Login.getCode(), MessageEnum.User_not_Login.getMessage());
        }

        // every page contains 3 rows
        Pageable pageable = PageRequest.of(page,3);
        AppUser user = appUserRepository.findByEmail(email).get();
        List<ProjectInfoResult> allByAppUser = projectRepository.findAllByAppUser(user.getId(), pageable);


        return MessageUtil.success(allByAppUser);


    }
}
