package ua.com.foxminded.sqljdbcschool.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.util.InputMismatchException;
import java.util.Scanner;

@Service
public class SchoolService implements ApplicationRunner {

	private Logger logger = LoggerFactory.getLogger(SchoolService.class);

	@Autowired
	private GeneratorService generatorService;
	@Autowired
	private GroupMenuDisplayService groupMenuDisplayService;
	@Autowired
	private CourseMenuDisplayService courseMenuDisplayService;
	@Autowired
	private StudentMenuDisplayService studentMenuDisplayService;
	private final Scanner scanner = new Scanner(System.in);

	@Override
	public void run(ApplicationArguments args) throws Exception {
		int operation;
		printMenu();
		System.out.print("\n");
		while (true) {
			System.out.print("Enter the operation: ");
			try {
				operation = scanner.nextInt();
				switch (operation) {
					case 1:
						courseMenuDisplayService.start();
						break;
					case 2:
						groupMenuDisplayService.start();
						break;
					case 3:
						studentMenuDisplayService.start();
						break;
					case 0:
						System.out.println("Exiting the program...");
						return;
					default:
						System.out.print("\n");
						System.err.println("Wrong operation!");
						System.out.print("\n");
						break;
				}
				printMenu();
				System.out.print("\n");
			} catch (InputMismatchException e) {
				logger.error("Invalid input! Please enter a number.");
				scanner.nextLine();
			}
		}
	}

	private void printMenu() {
		System.out.println("Welcome!");
		System.out.print("\n");
		System.out.println("1 - courses");
		System.out.println("2 - groups");
		System.out.println("3 - students");
		System.out.println("0 - exit");
	}


}
