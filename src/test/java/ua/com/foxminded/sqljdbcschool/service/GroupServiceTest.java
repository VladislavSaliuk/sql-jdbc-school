package ua.com.foxminded.sqljdbcschool.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ua.com.foxminded.sqljdbcschool.entity.Group;
import ua.com.foxminded.sqljdbcschool.entity.Student;
import ua.com.foxminded.sqljdbcschool.repository.GroupRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;




@SpringBootTest(classes = GroupService.class)
public class GroupServiceTest {

    @MockBean
    GroupRepository groupRepository;

    @Autowired
    GroupService groupService;

    @Test
    void save_shouldAddGroup_whenInputContainsNotExistingGroup() {
        Group group = new Group("Test group");
        when(groupRepository.existsByGroupName(group.getGroupName())).thenReturn(false);
        groupService.save(group);
        verify(groupRepository).save(group);
    }

    @Test
    void save_shouldThrowIllegalArgumentException_whenInputContainsExistingGroup() {
        Group group = new Group("CD-34");
        when(groupRepository.existsByGroupName(group.getGroupName())).thenReturn(true)
                .thenThrow(IllegalArgumentException.class);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> groupService.save(group));
        assertEquals("This group name is already exists!", exception.getMessage());
        verify(groupRepository, never()).save(group);
    }

    @Test
    void save_shouldThrowIllegalArgumentException_whenInputContainsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> groupService.save(null));
        assertEquals("Input contains null!", exception.getMessage());
        verify(groupRepository,never()).save(null);
    }

    @Test
    void delete_shouldDeleteGroup_whenInputContainsNotExistingGroupId(){
        long groupId = 1;
        when(groupRepository.existsById(groupId)).thenReturn(true);
        groupService.delete(groupId);
        verify(groupRepository).deleteById(groupId);
    }

    @Test
    void delete_shouldThrowIllegalArgumentException_whenInputContainsExistingGroupId(){
        long groupId = 1;
        when(groupRepository.existsById(groupId)).thenReturn(false)
                .thenThrow(IllegalArgumentException.class);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> groupService.delete(groupId));
        assertEquals("This group id doesn't exist!", exception.getMessage());
        verify(groupRepository, never()).deleteById(groupId);
    }

    @Test
    void findGroupsByStudentCount_shouldReturnCorrectGroupList_whenInputContainsNotNegativeValue(){
        long studentCount = 3;
        Group group1 = new Group("Group 1");
        Group group2 = new Group( "Group 2");
        Group group3 = new Group( "Group 3");

        group1.setStudents(Arrays.asList(new Student(), new Student(), new Student(), new Student()));
        group2.setStudents(Arrays.asList(new Student()));
        group3.setStudents(Collections.emptyList());

        when(groupRepository.findAll()).thenReturn(Arrays.asList(group1, group2, group3));
        List<Group> result = groupService.findGroupsByStudentCount(studentCount);
        assertEquals(2, result.size());
        assertFalse(result.contains(group1));
        assertTrue(result.contains(group2));
        assertTrue(result.contains(group3));
        verify(groupRepository).findAll();
        verifyNoMoreInteractions(groupRepository);
    }

    @Test
    void findGroupsByStudentCount_shouldThrowIllegalArgumentException_whenInputContainsNegativeValue(){
        int studentCount = -11;
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> groupService.findGroupsByStudentCount(studentCount));
        assertEquals("Student number can not be negative!", exception.getMessage());
    }

    @Test
    void getAll_shouldReturnCorrectGroupList(){
        List<Group> expectedGroupList = new LinkedList<>();
        expectedGroupList.add(new Group( "Test group 1"));
        expectedGroupList.add(new Group( "Test group 2"));
        expectedGroupList.add(new Group( "Test group 3"));
        when(groupRepository.findAll()).thenReturn(expectedGroupList);
        List<Group> actualGroupList = groupService.getAll();
        assertEquals(expectedGroupList,actualGroupList);
        verify(groupRepository).findAll();
    }

    @Test
    void clearAll_shouldClearAllGroups(){
        groupService.clearAll();
        verify(groupRepository).deleteAll();
    }

}
