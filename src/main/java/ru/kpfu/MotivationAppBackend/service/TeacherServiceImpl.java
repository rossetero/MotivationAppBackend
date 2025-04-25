package ru.kpfu.MotivationAppBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.MotivationAppBackend.dto.TeacherProfileDTO;
import ru.kpfu.MotivationAppBackend.entity.Teacher;
import ru.kpfu.MotivationAppBackend.repository.TeacherRepository;

@Service
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;
    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository){
        this.teacherRepository=teacherRepository;
    }
    @Override
    public TeacherProfileDTO getTeacherProfile(Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(RuntimeException::new);
        return new TeacherProfileDTO(teacher.getId(),teacher.getLogin(),teacher.getName());
    }

    @Override
    public void editTeacherProfile(TeacherProfileDTO teacherProfileDTO, Long teacherId) {
        Teacher t = teacherRepository.findById(teacherId).orElseThrow();
        System.out.println(t);
        System.out.println(teacherProfileDTO);
        t.setName(teacherProfileDTO.getName());
        teacherRepository.save(t);
    }
}
