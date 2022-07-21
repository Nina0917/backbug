package com.yuanning.backbug.repository;

import com.yuanning.backbug.entity.ConfirmationToken;
import com.yuanning.backbug.entity.ProjectManage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectManageRepository extends JpaRepository<ProjectManage,Long> {
}
