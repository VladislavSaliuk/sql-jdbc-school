package ua.com.foxminded.sqljdbcschool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.sqljdbcschool.entity.Course;
import ua.com.foxminded.sqljdbcschool.entity.Schedule;
import ua.com.foxminded.sqljdbcschool.entity.ScheduleId;
import ua.com.foxminded.sqljdbcschool.entity.Student;
import ua.com.foxminded.sqljdbcschool.repository.CourseRepository;
import ua.com.foxminded.sqljdbcschool.repository.GroupRepository;
import ua.com.foxminded.sqljdbcschool.repository.ScheduleRepository;
import ua.com.foxminded.sqljdbcschool.repository.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;



@Service
public class StudentService{

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private ScheduleRepository scheduleRepository;

	public void save(Student student) {

		if(student == null) {
			throw new IllegalArgumentException("Input contains null!");
		}

		if(!groupRepository.existsById(student.getGroup().getGroupId())) {
			throw new IllegalArgumentException("This group name doesn't exist!");
		}

		studentRepository.save(student);
	}

	public void delete(long studentId){
		if(!studentRepository.existsById(studentId)) {
			throw new IllegalArgumentException("Student with this student id doesn't exist!");
		}
		studentRepository.deleteById(studentId);
	}
	public void addStudentToCourse(long studentId, long courseId) {
		if(studentRepository.existsById(studentId)) {
			throw new IllegalArgumentException("Student with this Id doesn't exist!");
		}
		if(courseRepository.existsById(courseId)) {
			throw new IllegalArgumentException("Course with this Id doesn't exist!");
		}

		Student student = studentRepository.findById(studentId).get();
		Course course = courseRepository.findById(courseId).get();

		if(scheduleRepository.existsByCourseAndStudent(course,student)) {
			throw new IllegalArgumentException("This student is already on this course!");
		}


		Schedule schedule = new Schedule(student, course);

		scheduleRepository.save(schedule);
	}
	public void deleteStudentFromCourse(long studentId, long courseId) {
		if(studentRepository.existsById(studentId)){
			throw new IllegalArgumentException("Student with this Id doesn't exist!");
		}
		if(courseRepository.existsById(courseId)){
			throw new IllegalArgumentException("Course with this Id doesn't exist!");
		}

		Student student = studentRepository.findById(studentId).get();
		Course course = courseRepository.findById(courseId).get();

		if(!scheduleRepository.existsByCourseAndStudent(course,student)) {
			throw new IllegalArgumentException("This student is not on this course!");
		}


		Schedule schedule = scheduleRepository.findByCourseAndStudent(course, student);

		scheduleRepository.delete(schedule);
	}
	public List<Course> findCoursesByStudentFirstName(String firstName) {

		if(firstName == null){
			throw new IllegalArgumentException("Input contains null!");
		}

		if(!studentRepository.existsByFirstName(firstName)){
			throw new IllegalArgumentException("Student with this first name doesn't exist!");
		}

		return scheduleRepository.findAll().stream()
				.filter(schedule -> schedule.getStudent().getFirstName().equals(firstName))
				.map(Schedule::getCourse)
				.collect(Collectors.toList());
	}

	public List<Student> getAll() {
		return studentRepository.findAll();
	}

	public void clearAll(){
		studentRepository.deleteAll();
	}

}
