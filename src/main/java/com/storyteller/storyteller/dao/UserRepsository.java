package com.storyteller.storyteller.dao;

import com.storyteller.storyteller.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepsository extends JpaRepository<User, Integer> {
}
