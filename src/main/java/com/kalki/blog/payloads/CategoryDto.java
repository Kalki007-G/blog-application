package com.kalki.blog.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {

    private Integer categoryId;

    @NotBlank(message = "Category title cannot be blank")
    @Size(min = 4, message = "Category title must be at least 4 characters")
    private String categoryTitle;

    @NotBlank(message = "Category description cannot be blank")
    @Size(min = 10, message = "Category description must be at least 10 characters")
    private String categoryDescription;
}
