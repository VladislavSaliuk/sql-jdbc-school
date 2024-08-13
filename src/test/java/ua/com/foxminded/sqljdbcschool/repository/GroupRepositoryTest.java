package ua.com.foxminded.sqljdbcschool.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.sqljdbcschool.entity.Group;
import ua.com.foxminded.sqljdbcschool.repository.GroupRepository;
import ua.com.foxminded.sqljdbcschool.service.GroupService;

import java.util.List;

import static org.junit.Assert.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class GroupRepositoryTest {

    @Autowired
    private GroupRepository groupRepository;

    @Test
    @Sql(scripts = {"/sql/drop_data.sql", "/sql/insert_groups.sql"})
    void save_shouldReturnCorrectGroupCount_whenExecutesInsertQuery(){
        Group group = new Group( "Test Group");
        groupRepository.save(group);
        long count = groupRepository.findAll().size();
        assertEquals(11, count);
    }

    @Test
    @Sql(scripts = {"/sql/drop_data.sql", "/sql/insert_groups.sql"})
    void deleteById_shouldReturnCorrectGroupCount_whenExecutesDeleleQuery(){
        long groupId = 1;
        groupRepository.deleteById(groupId);
        long count = groupRepository.findAll().size();
        assertEquals(9,count);
    }

    @Test
    @Sql(scripts = {"/sql/drop_data.sql", "/sql/insert_groups.sql"})
    void existsByGroupId_shouldReturnFalse_whenGroupIdDoesntExist(){
        long groupId = 51;
        boolean isGroupIdExists = groupRepository.existsById(groupId);
        assertFalse(isGroupIdExists);
    }

    @Test
    @Sql(scripts = {"/sql/drop_data.sql", "/sql/insert_groups.sql"})
    void existsByGroupId_shouldReturnTrue_whenGroupIdExists(){
        long groupId = 1;
        boolean isGroupIdExists = groupRepository.existsById(groupId);
        assertTrue(isGroupIdExists);
    }

    @Test
    @Sql(scripts = {"/sql/drop_data.sql", "/sql/insert_groups.sql"})
    void existsByGroupName_shouldReturnFalse_whenGroupNameDoesntExist(){
        String groupName = "TV-23";
        boolean isGroupNameExists = groupRepository.existsByGroupName(groupName);
        assertFalse(isGroupNameExists);
    }

    @Test
    @Sql(scripts = {"/sql/drop_data.sql", "/sql/insert_groups.sql"})
    void existsByGroupName_shouldReturnCorrectValue_whenExecutesSelectQuery(){
        String groupName = "AB-12";
        boolean isGroupNameExists = groupRepository.existsByGroupName(groupName);
        assertTrue(isGroupNameExists);
    }

    @Test
    @Sql(scripts = {"/sql/drop_data.sql", "/sql/insert_groups.sql"})
    void findAll_shouldReturnCorrectGroupCount_whenExecutesSelectQuery(){
        List<Group> groupList = groupRepository.findAll();
        long count = groupList.size();
        assertEquals(10,count);
    }
    @Test
    @Sql(scripts = {"/sql/drop_data.sql", "/sql/insert_groups.sql"})
    void clearAll_shouldReturnZero_whenExecutesDeleteQuery(){
        groupRepository.deleteAll();
        long count = groupRepository.findAll().size();
        assertEquals(0,count);
    }

}
