package com.yuanning.backbug.repository;


import com.yuanning.backbug.entity.Project;

import com.yuanning.backbug.entity.request.ProjectInfoResult;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends PagingAndSortingRepository<Project,Long> {
    /*@Query("select new com.yuanning.backbug.entity.request.ProjectInfoResult(a.id,a.lastName,p.name,p.description) from Project p join project_manage pm on p.id = pm.project_id join AppUser a on a.id = p.manager.id where pm.appUser_id = ?1")
    List<ProjectInfoResult> findAllByAppUser(Long appUser_id, Pageable pageable);*/

    @Query("select new com.yuanning.backbug.entity.request.ProjectInfoResult(p.id,p.manager.lastName,p.manager.firstName,p.name,p.description) from Project p where p.manager.id = ?1")
    List<ProjectInfoResult> findProjectsByAppUserId(Long id, Pageable pageable);
}
