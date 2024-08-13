package ua.com.foxminded.sqljdbcschool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;
import ua.com.foxminded.sqljdbcschool.entity.Course;
import ua.com.foxminded.sqljdbcschool.entity.Group;
import ua.com.foxminded.sqljdbcschool.entity.Student;
import ua.com.foxminded.sqljdbcschool.repository.CourseRepository;
import ua.com.foxminded.sqljdbcschool.repository.GroupRepository;
import ua.com.foxminded.sqljdbcschool.repository.StudentRepository;

import java.util.*;

@Service
public class GeneratorService implements ApplicationRunner {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(groupRepository.findAll().isEmpty()) {
           this.createArbitraryGroups();
        }
        if(courseRepository.findAll().isEmpty()) {
            this.createArbitraryCourse();
        }
        if(studentRepository.findAll().isEmpty()) {
            this.createArbitraryStudentsAndInsertToGroups();
        }
    }

    public void createArbitraryGroups() {
        List<Group> groupList = new ArrayList<>();
        groupList.add(new Group("AB-12"));
        groupList.add(new Group("CD-34"));
        groupList.add(new Group("EF-56"));
        groupList.add(new Group("GH-78"));
        groupList.add(new Group("IJ-90"));
        groupList.add(new Group("KL-12"));
        groupList.add(new Group("MN-34"));
        groupList.add(new Group("OP-56"));
        groupList.add(new Group("QR-78"));
        groupList.add(new Group("ST-90"));
        groupRepository.saveAll(groupList);
    }

    public void createArbitraryCourse() {
        List<Course> courseList = new ArrayList<>();
        courseList.add(new Course("Mathematics", "Study of numbers, quantity, and space"));
        courseList.add(new Course("Biology", "Study of living organisms and their interactions with each other and their environments"));
        courseList.add(new Course("Physics", "Study of matter, energy, space, and time"));
        courseList.add(new Course("Chemistry", "Study of the composition, structure, properties, and change of matter"));
        courseList.add(new Course("History", "Study of the past events, particularly in human affairs"));
        courseList.add(new Course("Literature", "Study of written works, especially those considered to have artistic or intellectual value"));
        courseList.add(new Course("Computer Science", "Study of algorithms, data structures, and computing systems"));
        courseList.add(new Course("Geography", "Study of Earths landscapes, environments, and the relationships between people and their environments"));
        courseList.add(new Course("Art", "Expression or application of human creative skill and imagination"));
        courseList.add(new Course("Music", "Art form whose medium is sound and silence"));
        courseRepository.saveAll(courseList);
    }

    public void createArbitraryStudentsAndInsertToGroups() {
        List<String> firstNames = Arrays.asList("John", "Emma", "Michael", "Sophia", "William", "Olivia", "James", "Ava", "Benjamin", "Isabella", "Daniel", "Mia", "Alexander", "Charlotte", "Henry", "Amelia", "Joseph", "Harper", "Samuel", "Evelyn");
        List<String> lastNames = Arrays.asList("Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor", "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin", "Thompson", "Garcia", "Martinez", "Robinson");

        Random random = new Random();

        for (int i = 0; i < 200; i++) {
            long groupId = random.nextInt(9) + 1;
            String firstName = firstNames.get(random.nextInt(firstNames.size()));
            String lastName = lastNames.get(random.nextInt(lastNames.size()));

            Group group = groupRepository.findById(groupId).get();

            Student student = new Student();
            student.setGroup(group);
            student.setFirstName(firstName);
            student.setLastName(lastName);

            studentRepository.save(student);
        }
    }

}