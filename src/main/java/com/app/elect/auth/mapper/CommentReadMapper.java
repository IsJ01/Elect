package com.app.elect.auth.mapper;

import org.springframework.stereotype.Component;

import com.app.elect.auth.database.entity.Comment;
import com.app.elect.auth.dto.CommentReadDto;

@Component
public class CommentReadMapper implements Mapper<Comment, CommentReadDto> {

    @Override
    public CommentReadDto map(Comment object) {
        return new CommentReadDto(
            object.getId(), 
            object.getText()
        );
    }

}
