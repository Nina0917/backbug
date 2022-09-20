package com.yuanning.backbug.repository;

import com.yuanning.backbug.entity.User;
import com.yuanning.backbug.entity.Follow;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;


import java.util.List;
import java.util.Optional;

public interface FollowRepository extends PagingAndSortingRepository<Follow,Long> {

    Optional<Follow> findByUserAndFollowUser(User user, User followUser);

    List<Follow> findAllByUser(User user, Pageable pageable);

    @Query("select f.followUser.id from Follow f where f.user.id = ?1")
    List<Long> findFollowUserIdsByUserId(Long user_id);

}
