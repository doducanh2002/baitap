    package com.example.restfullapi.service.iml.iml;

    import com.example.restfullapi.exception.TeacherNotFoundException;
    import com.example.restfullapi.mapper.TeacherMapper;
    import com.example.restfullapi.dto.TeacherDto;
    import com.example.restfullapi.model.Teacher;
    import com.example.restfullapi.repository.TeacherRepository;
    import com.example.restfullapi.service.iml.TeacherService;
    import org.springframework.cache.annotation.CacheEvict;
    import org.springframework.cache.annotation.Cacheable;
    import org.springframework.stereotype.Service;

    import java.util.List;
    import java.util.stream.Collectors;

    @Service
    public class TeacherServiceIml implements TeacherService {

        private final TeacherRepository teacherRepository;

        private  final TeacherMapper teacherMapper;

        public TeacherServiceIml( TeacherRepository teacherRepository, TeacherMapper teacherMapper) {
            this.teacherRepository = teacherRepository;
            this.teacherMapper = teacherMapper;
        }

        @Override
        @Cacheable(value = "teacher")
            public TeacherDto createTeacher(TeacherDto teacherDto) {
                Teacher teacher = teacherMapper.convertToEntity(teacherDto);
                return teacherMapper.convertToDto(teacherRepository.save(teacher));
            }


        @Override
        @Cacheable(value = "teacher")
        public TeacherDto updateTeacher(int teacherId, TeacherDto teacherDto) {
            Teacher teacherReq = convertToEntity(teacherDto);
            Teacher teacherUpdate = teacherRepository.findById(teacherId)
                    .map(teacher -> {
                        teacher.setName(teacherReq.getName());
                        teacher.setGmail(teacherReq.getGmail());
                        teacher.setAge(teacherReq.getAge());
                        return teacher;
                    })
                    .map(teacherRepository::save)
                    .orElseThrow(TeacherNotFoundException::new);
            return convertToDto(teacherUpdate);
        }

        @Override
        @CacheEvict(value = "teacher")
        public void deleteTeacher(int teacherId) {
             teacherRepository.findById(teacherId)
                    .map(teacher -> {
                        teacherRepository.delete(teacher);
                        return teacher;
                    })
                    .orElseThrow(TeacherNotFoundException::new);
        }

        @Override
        public List<Teacher> listTeachers() {
            return teacherRepository.findAll();
        }

        @Override
        public TeacherDto getTeacherById(int teacherId){
            Teacher teacher = teacherRepository.findById(teacherId)
                    .orElseThrow(TeacherNotFoundException::new);
            return convertToDto(teacher);
        }

        @Override
        public List<TeacherDto> getTeacherByName(String teacherName){
            return teacherRepository.findByName(teacherName)
                    .stream().map(this::convertToDto).collect(Collectors.toList());
        }

        @Override
        public List<TeacherDto> getTeacherByAge(int teacherAge){
            return teacherRepository.findByAge(teacherAge)
                    .stream().map(this::convertToDto).collect(Collectors.toList());
        }

        @Override
        public List<TeacherDto> getTeacherByGmail(String teacherGmail){
            return teacherRepository.findByGmail(teacherGmail)
                    .stream().map(this::convertToDto).collect(Collectors.toList());
        }

        private Teacher convertToEntity(TeacherDto teacherDto){
            Teacher teacher =new Teacher();
            teacher.setId(teacherDto.getId());
            teacher.setName(teacherDto.getName());
            teacher.setAge(teacherDto.getAge());
            teacher.setGmail(teacherDto.getGmail());
            return teacher;
        }

        private TeacherDto convertToDto(Teacher teacher){
            TeacherDto teacherDto = new TeacherDto();
            teacherDto.setId(teacher.getId());
            teacherDto.setName(teacher.getName());
            teacherDto.setGmail(teacher.getGmail());
            teacherDto.setAge(teacher.getAge());
            return teacherDto;

        }
    }

