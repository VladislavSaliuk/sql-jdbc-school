package ua.com.foxminded.sqljdbcschool.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.sqljdbcschool.entity.Course;
import ua.com.foxminded.sqljdbcschool.entity.Group;
import ua.com.foxminded.sqljdbcschool.entity.Student;
import ua.com.foxminded.sqljdbcschool.repository.CourseRepository;
import ua.com.foxminded.sqljdbcschool.repository.GroupRepository;
import ua.com.foxminded.sqljdbcschool.repository.StudentRepository;

import java.util.List;

import static org.junit.Assert.assertEquals;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
        GeneratorService.class
}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class GeneratorServiceTest {

    @Autowired
    GeneratorService generatorService;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    StudentRepository studentRepository;
    @Test
    @Sql("/sql/drop_data.sql")
    void createArbitraryGroups_shouldReturnCorrectList(){
        generatorService.createArbitraryGroups();
        List<Group> groupList = groupRepository.findAll();
        assertEquals(10, groupList.size());
    }

    @Test
    @Sql("/sql/drop_data.sql")
    void createArbitraryCourses_shouldReturnCorrectList(){
        generatorService.createArbitraryCourse();
        List<Course> courseList = courseRepository.findAll();
        assertEquals(10, courseList.size());
    }

    @Test
    @Sql(scripts = {"/sql/drop_data.sql", "/sql/insert_groups.sql"})
    void createArbitraryStudentsAndInsertToGroups_shouldReturnCorrectList(){
        generatorService.createArbitraryStudentsAndInsertToGroups();
        List<Student> studentList = studentRepository.findAll();
        assertEquals(200, studentList.size());
    }

}
