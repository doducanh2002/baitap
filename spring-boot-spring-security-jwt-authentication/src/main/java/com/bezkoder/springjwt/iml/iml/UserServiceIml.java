package com.bezkoder.springjwt.iml.iml;


import com.bezkoder.springjwt.iml.UserService;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceIml implements UserService {

    @Autowired
    private final UserRepository userRepository;


    public UserServiceIml(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public User updateUser(int userId, User userReq) {
            User user1 = userRepository.findById(userId)
                    .map(result -> {

                        result.setPassword(userReq.getPassword());
                        result.setEmail(userReq.getEmail());

                        return result;
                    })
                    .map(userRepository::save)
                    .orElse(null);
            return user1;
        }


    @Override
    public User deleteUser(int userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    userRepository.delete(user);
                    return user;
                })
                .orElse(null);
    }

//    private Teacher convertToEntity(TeacherDto teacherDto){
//        Teacher teacher =new Teacher();
//        teacher.setId(teacherDto.getId());
//        teacher.setName(teacherDto.getName());
//        teacher.setAge(teacherDto.getAge());
//        teacher.setGmail(teacherDto.getGmail());
//
//        return teacher;
//    }
//
//    private TeacherDto convertToDto(Teacher teacher){
//        TeacherDto teacherDto = new TeacherDto();
//        teacherDto.setId(teacher.getId());
//        teacherDto.setName(teacher.getName());
//        teacherDto.setGmail(teacher.getGmail());
//        teacherDto.setAge(teacher.getAge());
//
//        return teacherDto;
//    }
}

