package ua.com.foxminded.sqljdbcschool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.sqljdbcschool.entity.Group;
import ua.com.foxminded.sqljdbcschool.entity.Student;
import ua.com.foxminded.sqljdbcschool.repository.GroupRepository;
import ua.com.foxminded.sqljdbcschool.repository.StudentRepository;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GroupService{

	@Autowired
	private GroupRepository groupRepository;

	public void save(Group group) {

		if(group == null){
			throw new IllegalArgumentException("Input contains null!");
		}

		if(groupRepository.existsByGroupName(group.getGroupName())){
			throw new IllegalArgumentException("This group name is already exists!");
		}

		groupRepository.save(group);
	}

	public void delete(long groupId) {

		if(!groupRepository.existsById(groupId)) {
			throw new IllegalArgumentException("This group id doesn't exist!");
		}

		groupRepository.deleteById(groupId);
	}

	public List<Group> findGroupsByStudentCount(long studentCount) {

		if(studentCount < 0){
			throw new IllegalArgumentException("Student number can not be negative!");
		}

        return groupRepository.findAll().stream()
				.filter(group -> group.getStudents().size() <= studentCount)
				.collect(Collectors.toList());
	}

	public List<Group> getAll() {
		return groupRepository.findAll();
	}
	public void clearAll() {
		groupRepository.deleteAll();
	}


}
