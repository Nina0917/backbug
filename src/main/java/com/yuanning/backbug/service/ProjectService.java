package com.yuanning.backbug.service;

import com.yuanning.backbug.entity.*;
import com.yuanning.backbug.entity.request.AddProjectRequest;
import com.yuanning.backbug.entity.request.AppUserResult;
import com.yuanning.backbug.entity.request.ProjectInfoResult;
import com.yuanning.backbug.exceptionHandler.MessageEnum;
import com.yuanning.backbug.exceptionHandler.MessageUtil;
import com.yuanning.backbug.exceptionHandler.Result;
import com.yuanning.backbug.repository.AppUserRepository;
import com.yuanning.backbug.repository.ProjectRepository;
import com.yuanning.backbug.security.common.utils.CurrentUserUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final AppUserRepository appUserRepository;
    private final CurrentUserUtils currentUserUtils;



    // 添加新项目
    @Transactional(rollbackFor = Exception.class)
    public Result<String> addProject(AddProjectRequest projectRequest) {

        // 判断新项目的名字和描述是否为空
        if (projectRequest.getName() == "" || projectRequest.getDescription() == "") {
            return MessageUtil.error(MessageEnum.EMPTY_FIELD.getResultCode(),MessageEnum.EMPTY_FIELD.getResultMsg());
        }
        // 获取当前user, as the manager of this project
        User appUser = currentUserUtils.getCurrentUser();

        // add new project to the repository
        Project project = new Project();
        project.setName(projectRequest.getName());
        project.setDescription(projectRequest.getDescription());
        project.setManager(appUser);
        Project save = projectRepository.save(project);

        // 添加此项目相关联的用户
        List<Long> appUserIds = projectRequest.getAppUserIds();
        appUserIds.add(appUser.getId());
        for (Long id : appUserIds) {
            User user = appUserRepository.findById(id).get();
            user.addProject(save);
            appUserRepository.save(user);
        }


        return MessageUtil.success("add project successfully!");
    }

    public Result<List<Project>> getAllProjects(int page) {
        // every page contains 3 rows
        Pageable pageable = PageRequest.of(page,3);
        User user = currentUserUtils.getCurrentUser();
        List<ProjectInfoResult> allByAppUser = projectRepository.findProjectsByAppUserId(user.getId(), pageable);

        return MessageUtil.success(allByAppUser);


    }

    public Result<List<User>> getMembers(Long projectId) {
        List<User> users = appUserRepository.findAppUsersByProjectId(projectId);
        List<AppUserResult> projectMembers = new ArrayList<>();

        // current project do not have any members yet, return empty list
        if (users == null || users.size() == 0){
            return MessageUtil.success(projectMembers);
        }


        for (User user : users) {
            // Long id, AppUserRole appUserRole, String email, String firstName, String lastName
            AppUserResult result = new AppUserResult(user.getId(),user.getEmail(), user.getFirstName(), user.getLastName());
            projectMembers.add(result);
        }
        return MessageUtil.success(projectMembers);

    }
}
