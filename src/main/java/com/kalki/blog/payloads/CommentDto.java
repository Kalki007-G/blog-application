package com.kalki.blog.payloads;

import com.kalki.blog.entities.Post;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CommentDto {
    private int id;
    private String content;
}
