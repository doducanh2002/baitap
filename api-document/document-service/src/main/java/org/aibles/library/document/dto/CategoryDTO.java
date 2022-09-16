package org.aibles.library.document.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CategoryDTO {

    private int id;

    @NotBlank
    private String name;
}
