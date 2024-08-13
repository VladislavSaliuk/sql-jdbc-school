package ua.com.foxminded.sqljdbcschool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.sqljdbcschool.entity.Course;
import ua.com.foxminded.sqljdbcschool.repository.CourseRepository;

import java.util.List;

@Service
public class CourseService {

	@Autowired
	private CourseRepository courseRepository;

	public void save(Course course) {

		if(course == null) {
			throw new IllegalArgumentException("Input contains null!");
		}

		if(courseRepository.existsByCourseName(course.getCourseName())) {
			throw new IllegalArgumentException("Course with this name is already exists!");
		}

		courseRepository.save(course);
	}

	public void delete(long courseId) {

		if(!courseRepository.existsById(courseId)) {
			throw new IllegalArgumentException("Course with this Id doesn't exist!");
		}

		courseRepository.deleteById(courseId);
	}

	public List<Course> getAll() {
		return courseRepository.findAll();
	}

    public void clearAll() {
		courseRepository.deleteAll();
	}

}
