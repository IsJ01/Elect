package com.app.elect.auth.mapper;

import org.springframework.stereotype.Component;

import com.app.elect.auth.database.entity.Comment;
import com.app.elect.auth.dto.CommentCreateDto;

@Component
public class CommentCreateMapper implements Mapper<CommentCreateDto, Comment> {

    @Override
    public Comment map(CommentCreateDto object) {
        Comment comment = new Comment();
        comment.setText(object.getText());
        return comment;
    }

}
