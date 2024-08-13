package ua.com.foxminded.sqljdbcschool.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ua.com.foxminded.sqljdbcschool.entity.Course;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
@Service
public class CourseMenuDisplayService {

	private Logger logger = LoggerFactory.getLogger(CourseMenuDisplayService.class);

	@Autowired
	private CourseService courseService;
	private Scanner scanner = new Scanner(System.in);

	public void start() {
		int operation;
		while(true) {
			printMenu();
			System.out.print("\n");
			System.out.print("Enter the operation: ");
			try {
				operation = scanner.nextInt();
				scanner.nextLine();
				System.out.print("\n");
				switch (operation) {
					case 1:
						save();
						break;
					case 2:
						delete();
						break;
					case 3:
						printAll();
						break;
					case 4:
						clearAll();
						break;
					case 0:
						return;
					default:
						System.out.print("\n");
						System.err.println("Wrong operation!");
						System.out.print("\n");
						break;
				}
			} catch (InputMismatchException e) {
				logger.error("Invalid input! Please enter a number.");
				scanner.nextLine();
			}
		}
	}
	private void printMenu() {
		System.out.print("\n");
		System.out.println("1 - save course");
		System.out.println("2 - delete course");
		System.out.println("3 - print all courses");
		System.out.println("4 - clear");
		System.out.println("0 - back to main menu");
	}

	private void save() {
		try {
			System.out.print("\n");
			System.out.print("Enter course name: ");
			String courseName = scanner.nextLine();
			System.out.print("\n");
			System.out.print("Add short description: ");
			String courseDescription = scanner.nextLine();
			Course course = new Course(courseName, courseDescription);
			courseService.save(course);
			System.out.print("\n");
			System.out.println("Course successfully created!");
			System.out.print("\n");
		} catch (IllegalArgumentException e){
			logger.error(e.getMessage());
		}
	}

	private void delete() {
		try {
			System.out.print("\n");
			System.out.print("Enter course Id: ");
			long courseId = scanner.nextInt();
			scanner.nextLine();
			System.out.print("\n");
			courseService.delete(courseId);
			System.out.println("Course successfully deleted!");
			System.out.print("\n");
		} catch (IllegalArgumentException e){
			logger.error(e.getMessage());
		} catch (InputMismatchException e) {
			logger.error("Id must be an integer!");
			scanner.nextLine();
		} catch (DataIntegrityViolationException e) {
			logger.error(e.getMessage());
		}
	}

	private void printAll() {
		System.out.print("\n");
		List<Course> courseList = courseService.getAll();
		System.out.printf("|%5s|%40s|%150s|\n", "Id", "Course name", "Course description");
		courseList.forEach(course -> System.out.printf("|%5d|%40s|%150s|\n", course.getCourseId(), course.getCourseName(), course.getCourseDescription()));
		System.out.print("\n");
	}


	private void clearAll() {
		try {
			courseService.clearAll();
			System.out.println("All Courses successfully cleared!");
			System.out.print("\n");
		} catch (DataIntegrityViolationException e) {
			logger.error(e.getMessage());
		}
	}

}
