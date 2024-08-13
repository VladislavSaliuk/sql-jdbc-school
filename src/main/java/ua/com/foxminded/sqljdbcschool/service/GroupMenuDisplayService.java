package ua.com.foxminded.sqljdbcschool.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ua.com.foxminded.sqljdbcschool.entity.Group;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


@Service
public class GroupMenuDisplayService {

	private Logger logger = LoggerFactory.getLogger(GroupMenuDisplayService.class);

	@Autowired
	private GroupService groupService;
	private Scanner scanner = new Scanner(System.in);


	public void start() {
		int operation;
		while (true) {
			printMenu();
			System.out.print("\n");
			System.out.print("Enter the operation: ");
			try {
				operation = scanner.nextInt();
				scanner.nextLine();
				System.out.println();
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
						printGroupsByStudentCount();
						break;
					case 5:
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
		System.out.println("1 - create group");
		System.out.println("2 - delete group");
		System.out.println("3 - print all groups");
		System.out.println("4 - print groups by student number");
		System.out.println("5 - clear");
		System.out.println("0 - back to main menu");
	}

	private void save() {
		try {
			System.out.print("\n");
			System.out.print("Enter group name: ");
			String groupName = scanner.nextLine();
			Group group = new Group(groupName);
			groupService.save(group);
			System.out.print("\n");
			System.out.println("Group successfully created!");
			System.out.print("\n");
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
		}
	}

	private void delete() {
		try {
			System.out.print("\n");
			System.out.print("Enter group Id: ");
			long groupId = scanner.nextInt();
			scanner.nextLine();
			System.out.print("\n");
			groupService.delete(groupId);
			System.out.println("Group successfully deleted!");
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

	private void printGroupsByStudentCount() {
		try {
			System.out.print("\n");
			System.out.print("Enter student number: ");
			long studentNumber = scanner.nextInt();
			scanner.nextLine();
			System.out.print("\n");
			List<Group> groupList = groupService.findGroupsByStudentCount(studentNumber);
			System.out.printf("|%5s|%10s|\n", "Id", "Group name");
			groupList.forEach(group -> System.out.printf("|%5d|%10s|\n", group.getGroupId(), group.getGroupName()));
			System.out.print("\n");
		} catch (IllegalArgumentException e){
			logger.error(e.getMessage());
		} catch (InputMismatchException e) {
			logger.error("Student number must be an integer!");
			scanner.nextLine();
		}
	}

	private void printAll() {
		System.out.print("\n");
		List<Group> groupList = groupService.getAll();
		System.out.printf("|%5s|%10s|\n", "Id", "Group name");
		groupList.forEach(group -> System.out.printf("|%5d|%10s|\n", group.getGroupId(), group.getGroupName()));
		System.out.print("\n");
	}

	private void clearAll() {
		try {
			groupService.clearAll();
			System.out.println("All Groups successfully cleared!");
			System.out.print("\n");
		} catch (DataIntegrityViolationException e) {
			logger.error(e.getMessage());
		}
	}

}
