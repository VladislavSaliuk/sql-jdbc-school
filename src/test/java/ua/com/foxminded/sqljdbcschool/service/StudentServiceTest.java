package ua.com.foxminded.sqljdbcschool.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ua.com.foxminded.sqljdbcschool.entity.*;
import ua.com.foxminded.sqljdbcschool.repository.CourseRepository;
import ua.com.foxminded.sqljdbcschool.repository.GroupRepository;
import ua.com.foxminded.sqljdbcschool.repository.ScheduleRepository;
import ua.com.foxminded.sqljdbcschool.repository.StudentRepository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = StudentService.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StudentServiceTest {
    @MockBean
    StudentRepository studentRepository;
    @MockBean
    GroupRepository groupRepository;
    @MockBean
    CourseRepository courseRepository;
    @MockBean
    ScheduleRepository scheduleRepository;
    @Autowired
    StudentService studentService;
    @Test
    void save_shouldAddStudent_whenInputContainsNotExistingStudent(){
        Student student = new Student(new Group("Test group"), "Test", "Test");
        when(groupRepository.existsById(student.getGroup().getGroupId())).thenReturn(true);
        studentService.save(student);
        verify(studentRepository).save(student);
    }

    @Test
    void save_shouldThrowIllegalArgumentException_whenInputContainsNotExistingGroupId(){
        Student student = new Student(new Group(100), "Test", "Test");
        when(groupRepository.existsById(student.getGroup().getGroupId())).thenReturn(false)
                .thenThrow(IllegalArgumentException.class);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> studentService.save(student));
        assertEquals("This group name doesn't exist!", exception.getMessage());
        verify(studentRepository, never()).save(student);
    }

    @Test
    void save_shouldThrowIllegalArgumentException_whenInputContainsNull(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> studentService.save(null));
        assertEquals("Input contains null!", exception.getMessage());
        verify(studentRepository, never()).save(null);
    }

    @Test
    void delete_shouldDeleteStudent_whenInputContainsExistingStudentId(){
        long studentId = 1;
        when(studentRepository.existsById(studentId)).thenReturn(true);
        studentService.delete(studentId);
        verify(studentRepository).deleteById(studentId);
    }

    @Test
    void delete_shouldThrowIllegalArgumentException_whenInputContainsNotExistingStudentId(){
        long studentId = 105;
        when(studentRepository.existsById(studentId)).thenReturn(false)
                .thenThrow(IllegalArgumentException.class);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> studentService.delete(studentId));
        assertEquals("Student with this student id doesn't exist!", exception.getMessage());
        verify(studentRepository, never()).deleteById(studentId);
    }

    @Test
    void addStudentToCourse_shouldAddStudentToCourse() {
        Student student = new Student(new Group(1), "Test", "Test");
        Course course = new Course("Test course", "Test description");

        when(studentRepository.existsById(student.getStudentId())).thenReturn(false);
        when(courseRepository.existsById(course.getCourseId())).thenReturn(false);
        when(studentRepository.findById(student.getStudentId())).thenReturn(Optional.of(student));
        when(courseRepository.findById(course.getCourseId())).thenReturn(Optional.of(course));
        when(scheduleRepository.existsByCourseAndStudent(course,student)).thenReturn(false);

        Schedule schedule = new Schedule(student, course);


        studentService.addStudentToCourse(student.getStudentId(), course.getCourseId());

        verify(studentRepository).existsById(student.getStudentId());
        verify(courseRepository).existsById(course.getCourseId());
        verify(studentRepository).findById(student.getStudentId());
        verify(courseRepository).findById(course.getCourseId());
        verify(scheduleRepository).existsByCourseAndStudent(course,student);
        verify(scheduleRepository).save(schedule);
    }

    @Test
    void addStudentToCourse_shouldThrowIllegalArgumentException_whenInputContainsAlreadyRelatedStudentId(){
        Student student = new Student(new Group(1), "Test", "Test");
        Course course = new Course("Test course", "Test description");

        when(studentRepository.existsById(student.getStudentId())).thenReturn(false);
        when(courseRepository.existsById(course.getCourseId())).thenReturn(false);
        when(studentRepository.findById(student.getStudentId())).thenReturn(Optional.of(student));
        when(courseRepository.findById(course.getCourseId())).thenReturn(Optional.of(course));
        when(scheduleRepository.existsByCourseAndStudent(course,student)).thenReturn(true)
                .thenThrow(IllegalArgumentException.class);

        Schedule schedule = new Schedule(student,course);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                studentService.addStudentToCourse(student.getStudentId(), course.getCourseId()));

        assertEquals("This student is already on this course!", exception.getMessage());

        verify(studentRepository).existsById(student.getStudentId());
        verify(courseRepository).existsById(course.getCourseId());
        verify(studentRepository).findById(student.getStudentId());
        verify(courseRepository).findById(course.getCourseId());
        verify(scheduleRepository).existsByCourseAndStudent(course,student);
        verify(scheduleRepository, never()).save(schedule);
    }
    @Test
    void addStudentToCourse_shouldThrowIllegalArgumentException_whenInputContainsNotExistingStudentId(){
        Student student = new Student(new Group(1), "Test", "Test");
        Course course = new Course("Test course", "Test description");

        when(studentRepository.existsById(student.getStudentId())).thenReturn(true)
                .thenThrow(IllegalArgumentException.class);
        when(courseRepository.existsById(course.getCourseId())).thenReturn(false);
        when(studentRepository.findById(student.getStudentId())).thenReturn(null);
        when(courseRepository.findById(course.getCourseId())).thenReturn(Optional.of(course));
        when(scheduleRepository.existsByCourseAndStudent(course,student)).thenReturn(false);


        Schedule schedule = new Schedule(student, course);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> studentService.addStudentToCourse(student.getStudentId(),course.getCourseId()));
        assertEquals("Student with this Id doesn't exist!", exception.getMessage());

        verify(studentRepository).existsById(student.getStudentId());
        verify(courseRepository,never()).existsById(course.getCourseId());
        verify(studentRepository,never()).findById(student.getStudentId());
        verify(courseRepository, never()).findById(course.getCourseId());
        verify(scheduleRepository, never()).existsByCourseAndStudent(course,student);
        verify(scheduleRepository, never()).save(schedule);
    }

    @Test
    void addStudentToCourse_shouldThrowIllegalArgumentException_whenInputContainsNotExistingCourseId(){
        Student student = new Student(new Group(1), "Test", "Test");
        Course course = new Course( "Test course", "Test description");

        when(studentRepository.existsById(student.getStudentId())).thenReturn(false);
        when(courseRepository.existsById(course.getCourseId())).thenReturn(true)
                .thenThrow(IllegalArgumentException.class);
        when(studentRepository.findById(student.getStudentId())).thenReturn(Optional.of(student));
        when(courseRepository.findById(course.getCourseId())).thenReturn(null);
        when(scheduleRepository.existsByCourseAndStudent(course,student)).thenReturn(false);

        Schedule schedule = new Schedule(student ,course);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> studentService.addStudentToCourse(student.getStudentId(),course.getCourseId()));
        assertEquals("Course with this Id doesn't exist!", exception.getMessage());

        verify(studentRepository).existsById(student.getStudentId());
        verify(courseRepository).existsById(course.getCourseId());
        verify(studentRepository,never()).findById(student.getStudentId());
        verify(courseRepository, never()).findById(course.getCourseId());
        verify(scheduleRepository, never()).existsByCourseAndStudent(course,student);
        verify(scheduleRepository, never()).save(schedule);
    }

    @Test
    void deleteStudentFromCourse_shouldAddStudentToCourse_whenInputContainsExistingId(){
        Student student = new Student( new Group(1), "Test", "Test");
        Course course = new Course("Test course", "Test description");
        Schedule schedule = new Schedule(student, course);

        when(studentRepository.existsById(student.getStudentId())).thenReturn(false);
        when(courseRepository.existsById(course.getCourseId())).thenReturn(false);
        when(studentRepository.findById(student.getStudentId())).thenReturn(Optional.of(student));
        when(courseRepository.findById(course.getCourseId())).thenReturn(Optional.of(course));
        when(scheduleRepository.existsByCourseAndStudent(course,student)).thenReturn(true);
        when(scheduleRepository.findByCourseAndStudent(course,student)).thenReturn(schedule);

        studentService.deleteStudentFromCourse(student.getStudentId(), course.getCourseId());

        verify(studentRepository).existsById(student.getStudentId());
        verify(courseRepository).existsById(course.getCourseId());
        verify(studentRepository).findById(student.getStudentId());
        verify(courseRepository).findById(course.getCourseId());
        verify(scheduleRepository).findByCourseAndStudent(course, student);
        verify(scheduleRepository).delete(schedule);
    }

    @Test
    void deleteStudentFromCourse_shouldThrowIllegalArgumentException_whenInputContainsNotRelatedId(){
        Student student = new Student( new Group(1), "Test", "Test");
        Course course = new Course( "Test course", "Test description");


        when(studentRepository.existsById(student.getStudentId())).thenReturn(false);
        when(courseRepository.existsById(course.getCourseId())).thenReturn(false);
        when(studentRepository.findById(student.getStudentId())).thenReturn(Optional.of(student));
        when(courseRepository.findById(course.getCourseId())).thenReturn(Optional.of(course));
        when(scheduleRepository.existsByCourseAndStudent(course,student)).thenReturn(false)
                .thenThrow(IllegalArgumentException.class);

        Schedule schedule = new Schedule(student, course);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> studentService.deleteStudentFromCourse(student.getStudentId(), course.getCourseId()));
        assertEquals("This student is not on this course!", exception.getMessage());

        verify(studentRepository).existsById(student.getStudentId());
        verify(courseRepository).existsById(course.getCourseId());
        verify(studentRepository).findById(student.getStudentId());
        verify(courseRepository).findById(course.getCourseId());
        verify(scheduleRepository).existsByCourseAndStudent(course,student);
        verify(scheduleRepository, never()).delete(schedule);
    }
    @Test
    void deleteStudentFromCourse_shouldThrowIllegalArgumentException_whenInputContainsNotExistingStudentId(){
        Student student = new Student( new Group(1), "Test", "Test");
        Course course = new Course( "Test course", "Test description");


        when(studentRepository.existsById(student.getStudentId())).thenReturn(true)
                .thenThrow(IllegalArgumentException.class);
        when(courseRepository.existsById(course.getCourseId())).thenReturn(false);
        when(studentRepository.findById(student.getStudentId())).thenReturn(null);
        when(courseRepository.findById(course.getCourseId())).thenReturn(Optional.of(course));
        when(scheduleRepository.existsByCourseAndStudent(course,student)).thenReturn(true);

        Schedule schedule = new Schedule(student, course);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> studentService.deleteStudentFromCourse(student.getStudentId(),course.getCourseId()));
        assertEquals("Student with this Id doesn't exist!", exception.getMessage());

        verify(studentRepository).existsById(student.getStudentId());
        verify(courseRepository,never()).existsById(course.getCourseId());
        verify(studentRepository,never()).findById(student.getStudentId());
        verify(courseRepository,never()).findById(course.getCourseId());
        verify(scheduleRepository, never()).existsByCourseAndStudent(course,student);
        verify(scheduleRepository, never()).delete(schedule);
    }

    @Test
    void deleteStudentFromCourse_shouldThrowIllegalArgumentException_whenInputContainsNotExistingCourseId(){
        Student student = new Student( new Group(1), "Test", "Test");
        Course course = new Course( "Test course", "Test description");


        when(studentRepository.existsById(student.getStudentId())).thenReturn(false);
        when(courseRepository.existsById(course.getCourseId())).thenReturn(true)
                .thenThrow(IllegalArgumentException.class);
        when(studentRepository.findById(student.getStudentId())).thenReturn(Optional.of(student));
        when(courseRepository.findById(course.getCourseId())).thenReturn(null);
        when(scheduleRepository.existsByCourseAndStudent(course,student)).thenReturn(true);

        Schedule schedule = new Schedule(student,course);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> studentService.deleteStudentFromCourse(student.getStudentId(),course.getCourseId()));
        assertEquals("Course with this Id doesn't exist!", exception.getMessage());

        verify(studentRepository).existsById(student.getStudentId());
        verify(courseRepository).existsById(course.getCourseId());
        verify(studentRepository,never()).findById(student.getStudentId());
        verify(courseRepository,never()).findById(course.getCourseId());
        verify(scheduleRepository,never()).existsByCourseAndStudent(course,student);
        verify(scheduleRepository,never()).delete(schedule);
    }

    @Test
    void findCoursesByStudentFirstName_shouldReturnCorrectCourseList_whenInputContainsExistingStudentFirstName() {
        String firstName = "John";
        List<Schedule> schedules = new ArrayList<>();
        Course course1 = new Course("Math", "Math course");
        Course course2 = new Course( "English", "English course");
        schedules.add(new Schedule(new Student(new Group(1), firstName, "Test last name 1"), course1));
        schedules.add(new Schedule(new Student(new Group(2),firstName, "Test last name 2"), course2));

        when(studentRepository.existsByFirstName(firstName)).thenReturn(true);
        when(scheduleRepository.findAll()).thenReturn(schedules);

        List<Course> actualCourses = studentService.findCoursesByStudentFirstName(firstName);

        List<Course> expectedCourses = schedules.stream()
                .filter(schedule -> schedule.getStudent().getFirstName().equals(firstName))
                .map(Schedule::getCourse)
                .collect(Collectors.toList());

        assertEquals(expectedCourses, actualCourses);
        verify(studentRepository).existsByFirstName(firstName);
        verify(scheduleRepository).findAll();
    }

    @Test
    void findCoursesByStudentFirstName_shouldThrowIllegalArgumentException_whenInputContainsNotExistingStudentFirstName() {
        String firstName = "Wrong test";

        when(studentRepository.existsByFirstName(firstName)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            studentService.findCoursesByStudentFirstName(firstName);
        });

        assertEquals("Student with this first name doesn't exist!", exception.getMessage());
        verify(studentRepository).existsByFirstName(firstName);
        verify(scheduleRepository, never()).findAll();
    }

    @Test
    void findCoursesByStudentFirstName_shouldThrowIllegalArgumentException_whenInputContainsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            studentService.findCoursesByStudentFirstName(null);
        });

        assertEquals("Input contains null!", exception.getMessage());
        verify(studentRepository, never()).existsByFirstName(any());
        verify(scheduleRepository, never()).findAll();
    }


    @Test
    void getAll_shouldReturnCorrectStudentList(){
        List<Student> expectedStudentList = new LinkedList<>();
        expectedStudentList.add(new Student(new Group("Test group 1"), "First name 1", "Last name 1"));
        expectedStudentList.add(new Student(new Group("Test group 2"), "First name 2", "Last name 2"));
        expectedStudentList.add(new Student(new Group("Test group 3"), "First name 3", "Last name 3"));
        when(studentRepository.findAll()).thenReturn(expectedStudentList);
        List<Student> actualStudentList = studentService.getAll();
        assertEquals(expectedStudentList,actualStudentList);
        verify(studentRepository).findAll();
    }

    @Test
    void clearAll_shouldClearAllStudents(){
        studentRepository.deleteAll();;
        verify(studentRepository).deleteAll();
    }

}