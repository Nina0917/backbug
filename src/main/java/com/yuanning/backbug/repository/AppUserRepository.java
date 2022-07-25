package com.yuanning.backbug.repository;


import com.yuanning.backbug.entity.AppUser;
import com.yuanning.backbug.entity.request.AppUserResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AppUserRepository extends JpaRepository<AppUser,Long> {
    Optional<AppUser> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE AppUser a SET a.enabled = TRUE where a.email = ?1")
    int enableAppUser(String email);

   @Query("select new com.yuanning.backbug.entity.request.AppUserResult(a.id, a.appUserRole, a.email, a.firstName, a.lastName) from AppUser a where a.id = ?1")
    List<AppUserResult> findAppUserInfoById(Long id);
}
