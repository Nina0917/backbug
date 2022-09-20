package com.yuanning.backbug.repository;


import com.yuanning.backbug.entity.User;
import com.yuanning.backbug.entity.request.AppUserResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@Transactional(readOnly = true)
public interface AppUserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User a SET a.enabled = TRUE where a.email = ?1")
    int enableAppUser(String email);

   @Query("select new com.yuanning.backbug.entity.request.AppUserResult(a.id, a.email, a.firstName, a.lastName) from User a where a.id = ?1")
    AppUserResult findAppUserInfoById(Long id);

   Set<User> findAppUsersByAssignTicketsId(Long id);

   List<User> findAppUsersByProjectId(Long id);

    @Modifying
    @Transactional(rollbackFor = Exception.class)
    void deleteByEmail(String email);

    boolean existsByEmail(String email);

    @Query("select new com.yuanning.backbug.entity.request.AppUserResult(a.id, a.email, a.firstName, a.lastName) from User a")
    List<AppUserResult> findAllAppUserInfo(Pageable page);

}
