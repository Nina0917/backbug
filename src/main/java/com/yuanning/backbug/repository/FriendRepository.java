package com.yuanning.backbug.repository;

import com.yuanning.backbug.entity.AppUser;
import com.yuanning.backbug.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friend,Long> {
    boolean existsByFirstUserAndSecondUser(AppUser first, AppUser second);

    List<Friend> findByFirstUser(AppUser user);
    List<Friend> findBySecondUser(AppUser user);

}
