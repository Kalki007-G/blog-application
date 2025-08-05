package com.kalki.blog.payloads;

import com.kalki.blog.entities.Category;
import com.kalki.blog.entities.Comment;
import com.kalki.blog.entities.User;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
public class PostDto {

    private Integer postId;

    @NotBlank(message = "Post title cannot be blank")
    @Size(min = 4, message = "Post title must be at least 4 characters")
    private String title;

    @NotBlank(message = "Post content cannot be blank")
    private String content;

    private String imageName;

    private Date addedDate;

    private CategoryDto category;

    private UserDto user;

    private Set<CommentDto> comments=new HashSet<>();
}
