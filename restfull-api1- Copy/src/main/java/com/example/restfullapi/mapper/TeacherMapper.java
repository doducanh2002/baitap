package com.example.restfullapi.mapper;

import com.example.restfullapi.dto.TeacherDto;
import com.example.restfullapi.model.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;


@Mapper(componentModel = "spring")
public interface TeacherMapper {

    TeacherMapper INSTANCE = Mappers.getMapper(TeacherMapper.class);

    TeacherDto convertToDto(Teacher teacher);

    Teacher convertToEntity(TeacherDto teacherDto);
}
