package ua.com.foxminded.sqljdbcschool.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ua.com.foxminded.sqljdbcschool.entity.Course;
import ua.com.foxminded.sqljdbcschool.repository.CourseRepository;
import ua.com.foxminded.sqljdbcschool.repository.StudentRepository;


import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {CourseService.class})
public class CourseServiceTest {

    @MockBean
    CourseRepository courseRepository;

    @MockBean
    StudentRepository studentRepository;

    @Autowired
    CourseService courseService;
    @Test
    void save_shouldAddCourse_whenInputContainsNotExistingCourse(){
        Course course = new Course("test", "test");
        when(courseRepository.existsByCourseName(course.getCourseName())).thenReturn(false);
        courseService.save(course);
        verify(courseRepository).save(course);
    }

    @Test
    void save_shouldThrowIllegalArgumentException_whenInputContainsExistingCourse(){
        Course course = new Course("Math", "test");
        when(courseRepository.existsByCourseName(course.getCourseName())).thenReturn(true)
                .thenThrow(IllegalArgumentException.class);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> courseService.save(course));
        assertEquals("Course with this name is already exists!", exception.getMessage());
        verify(courseRepository,never()).save(course);
    }

    @Test
    void save_shouldThrowIllegalArgumentException_whenInputContainsNull(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> courseService.save(null));
        assertEquals("Input contains null!", exception.getMessage());
        verify(courseRepository,never()).save(null);
    }

    @Test
    void delete_shouldDeleteCourse_whenInputContainsExistingCourseId(){
        long courseId = 1;
        when(courseRepository.existsById(courseId)).thenReturn(true);
        courseService.delete(courseId);
        verify(courseRepository).deleteById(courseId);
    }

    @Test
    void delete_shouldThrowIllegalArgumentException_whenInputContainsNotExistingCourseId() {
        long courseId = 100;
        when(courseRepository.existsById(courseId)).thenReturn(false)
                .thenThrow(IllegalArgumentException.class);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> courseService.delete(courseId));
        assertEquals("Course with this Id doesn't exist!", exception.getMessage());
        verify(courseRepository,never()).deleteById(courseId);
    }

    @Test
    void getAll_shouldReturnCorrectCourseList(){
        List<Course> expectedCourseList = new LinkedList<>();
        expectedCourseList.add(new Course( "Test course 1", "description"));
        expectedCourseList.add(new Course( "Test course 2", "description"));
        expectedCourseList.add(new Course( "Test course 3", "description"));
        when(courseRepository.findAll()).thenReturn(expectedCourseList);
        List<Course> actualCourseList = courseService.getAll();
        assertEquals(expectedCourseList,actualCourseList);
        verify(courseRepository).findAll();
    }

    @Test
    void clearAll_shouldClearAllCourses(){
        courseService.clearAll();
        verify(courseRepository).deleteAll();
    }

}
