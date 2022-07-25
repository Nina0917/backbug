package com.yuanning.backbug.repository;

import com.yuanning.backbug.entity.ConfirmationToken;
import com.yuanning.backbug.entity.ProjectManage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectManageRepository extends JpaRepository<ProjectManage,Long> {

    @Query("select pm.appUser_id from project_manage pm where pm.project_id = ?1")
    List<Long> findAppUserIdsByProjectId(Long id);
}
