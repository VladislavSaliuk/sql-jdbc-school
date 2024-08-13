package ua.com.foxminded.sqljdbcschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.sqljdbcschool.entity.Group;

import java.util.List;


@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    boolean existsByGroupName(String groupName);


}
