package com.app.elect.auth.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.elect.auth.database.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
