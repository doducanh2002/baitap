package org.squad3.library.documentservice.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CategoryDTO {

    private int id;

    @NotBlank
    private String name;
}
