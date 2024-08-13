package ua.com.foxminded.sqljdbcschool.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ua.com.foxminded.sqljdbcschool.entity.Course;
import ua.com.foxminded.sqljdbcschool.entity.Group;
import ua.com.foxminded.sqljdbcschool.entity.Student;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@Service
public class StudentMenuDisplayService {

	private Logger logger = LoggerFactory.getLogger(StudentMenuDisplayService.class);

	@Autowired
	private StudentService studentService;
	private Scanner scanner = new Scanner(System.in);
	public void start() {
		int operation;
		while(true){
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
						deleteStudentFromCourse();
						break;
					case 5:
						addStudentToCourse();
						break;
					case 6:
						printCoursesByStudentFirstName();
						break;
					case 7:
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
		System.out.println("1 - save student");
		System.out.println("2 - delete student");
		System.out.println("3 - add student to course");
		System.out.println("4 - delete student from course");
		System.out.println("5 - print all students");
		System.out.println("6 - print courses by student firstname");
		System.out.println("7 - clear");
		System.out.println("0 - back to main menu");
	}

	private void save() {
		try {
			System.out.print("\n");
			System.out.print("Enter group id: ");
			long groupId = scanner.nextInt();
			System.out.print("\n");
			System.out.print("Enter student's firstname: ");
			String firstName = scanner.nextLine();
			System.out.print("\n");
			System.out.print("Enter student's lastname: ");
			String lastName = scanner.nextLine();
			Group group = new Group();
			group.setGroupId(groupId);
			Student student = new Student(group, firstName, lastName);
			studentService.save(student);
			System.out.print("\n");
			System.out.println("Student successfully created!");
			System.out.print("\n");
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
		}
	}

	private void delete() {
		try {
			System.out.print("\n");
			System.out.print("Enter student Id: ");
			long studentId = scanner.nextInt();
			scanner.nextLine();
			System.out.print("\n");
			studentService.delete(studentId);
			System.out.println("Student successfully deleted!");
			System.out.print("\n");
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
		} catch (InputMismatchException e) {
			logger.error("Id must be an integer!");
			scanner.nextLine();
		} catch (DataIntegrityViolationException e) {
			logger.error(e.getMessage());
		}
	}

	private void addStudentToCourse() {
		try {
			System.out.print("\n");
			System.out.print("Enter student Id: ");
			long studentId = scanner.nextInt();
			scanner.nextLine();
			System.out.print("\n");
			System.out.print("Enter course Id: ");
			long courseId = scanner.nextInt();
			studentService.addStudentToCourse(studentId, courseId);
			System.out.print("\n");
			System.out.println("Student successfully added to the course!");
			System.out.print("\n");
		} catch (IllegalArgumentException e){
			logger.error(e.getMessage());
		} catch (InputMismatchException e) {
			logger.error("Id must be an integer!");
			scanner.nextLine();
		}
	}
	private void deleteStudentFromCourse() {
		try {
			System.out.print("\n");
			System.out.print("Enter student Id: ");
			long studentId = scanner.nextInt();
			scanner.nextLine();
			System.out.print("\n");
			System.out.print("Enter course Id: ");
			long courseId = scanner.nextInt();
			studentService.deleteStudentFromCourse(studentId, courseId);
			System.out.print("\n");
			System.out.println("Student successfully removed from the course!");
			System.out.print("\n");
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
		} catch (InputMismatchException e) {
			logger.error("Id must be an integer!");
			scanner.nextLine();
		}
	}
	private void printCoursesByStudentFirstName() {
		try {
			System.out.print("\n");
			System.out.print("Enter student's firstname: ");
			String firstName = scanner.nextLine();
			System.out.print("\n");
			List<Course> courseList = studentService.findCoursesByStudentFirstName(firstName);
			System.out.printf("|%5s|%40s|%150s|\n", "Id", "Course name", "Course description");
			courseList.forEach(course -> System.out.printf("|%5d|%40s|%150s|\n", course.getCourseId(), course.getCourseName(), course.getCourseDescription()));
			System.out.print("\n");
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
		}
	}
	private void printAll() {
		System.out.print("\n");
		List<Student> studentList = studentService.getAll();
		System.out.printf("|%10s|%10s|%20s|%20s|\n", "StudentId", "GroupId", "Firstname", "Lastname");
		studentList.forEach(student -> System.out.printf("|%10d|%10d|%20s|%20s|\n", student.getStudentId(),
				student.getGroup().getGroupId(),
				student.getFirstName(), student.getLastName()));
		System.out.print("\n");
	}

	private void clearAll() {
		try {
			studentService.clearAll();
			System.out.println("All students successfully cleared!");
			System.out.print("\n");
		} catch (DataIntegrityViolationException e) {
			logger.error(e.getMessage());
		}
	}

}
