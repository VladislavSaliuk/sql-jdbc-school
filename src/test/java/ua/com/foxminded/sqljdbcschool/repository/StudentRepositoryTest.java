package ua.com.foxminded.sqljdbcschool.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.sqljdbcschool.entity.Group;
import ua.com.foxminded.sqljdbcschool.entity.Student;
import ua.com.foxminded.sqljdbcschool.repository.StudentRepository;

import java.util.List;

import static org.junit.Assert.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    @Sql(scripts = {"/sql/drop_data.sql", "/sql/insert_groups.sql", "/sql/insert_students.sql"})
    void save_shouldReturnCorrectStudentCount_whenExecutesInsertQuery(){
        Student student = new Student(new Group(1,"AB-12"), "Test", "Test");
        studentRepository.save(student);
        long count = studentRepository.findAll().size();
        assertEquals(11, count);
    }

    @Test
    @Sql(scripts = {"/sql/drop_data.sql", "/sql/insert_groups.sql", "/sql/insert_students.sql"})
    void deleteById_shouldReturnCorrectStudentCount_whenExecutesDeleteQuery(){
        long studentId = 1;
        studentRepository.deleteById(studentId);
        long count = studentRepository.findAll().size();
        assertEquals(9, count);
    }
    @Test
    @Sql(scripts = {"/sql/drop_data.sql", "/sql/insert_groups.sql", "/sql/insert_students.sql"})
    void existsById_shouldReturnTrue_whenStudentIdExists(){
        long studentId =  7;
        boolean isStudentIdExists = studentRepository.existsById(studentId);
        assertTrue(isStudentIdExists);
    }

    @Test
    @Sql(scripts = {"/sql/drop_data.sql", "/sql/insert_groups.sql", "/sql/insert_students.sql"})
    void existsById_shouldReturnFalse_whenStudentIdDoesntExist(){
        long studentId = 107;
        boolean isStudentIdExists = studentRepository.existsById(studentId);
        assertFalse(isStudentIdExists);
    }

    @Test
    @Sql(scripts = {"/sql/drop_data.sql", "/sql/insert_groups.sql", "/sql/insert_students.sql"})
    void existsByFirstName_shouldReturnTrue_whenStudentFirstNameExists(){
        String firstName = "John";
        boolean isStudentIdExists = studentRepository.existsByFirstName(firstName);
        assertTrue(isStudentIdExists);
    }

    @Test
    @Sql(scripts = {"/sql/drop_data.sql", "/sql/insert_groups.sql", "/sql/insert_students.sql"})
    void existsByFirstName_shouldReturnFalse_whenStudentFirstNameDoesntExist(){
        String firstName = "Vlad";
        boolean isStudentIdExists = studentRepository.existsByFirstName(firstName);
        assertFalse(isStudentIdExists);
    }
    @Test
    @Sql(scripts = {"/sql/drop_data.sql","/sql/insert_groups.sql", "/sql/insert_students.sql"})
    void findAll_shouldReturnCorrectStudentCount_whenExecutesSelectQuery(){
        List<Student> studentList = studentRepository.findAll();
        long count = studentList.size();
        assertEquals(10, count);
    }

    @Test
    @Sql(scripts = {"/sql/drop_data.sql", "/sql/insert_groups.sql", "/sql/insert_students.sql"})
    void deleteAll_shouldReturnCorrectStudentCount_whenExecutesDeleteQuery(){
        studentRepository.deleteAll();
        long count = studentRepository.findAll().size();
        assertEquals(0, count);
    }

}
