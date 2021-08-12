package com.example.restfullapi.repository;

import com.example.restfullapi.dto.TeacherDto;
import com.example.restfullapi.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository  extends JpaRepository<Teacher, Integer>{
    Optional<Teacher> findById(int id);

    List<Teacher> findByName(String name );

    List<Teacher> findByAge(int age);

    List<Teacher> findByGmail(String gmail);
}
