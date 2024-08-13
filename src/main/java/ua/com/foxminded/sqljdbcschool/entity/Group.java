package ua.com.foxminded.sqljdbcschool.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "groups")
@AllArgsConstructor
public class Group implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private long groupId;

    @Column(name = "group_name")
    private String groupName;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    private List<Student> students = new ArrayList<>();

    public Group(long groupId) {
        this.groupId = groupId;
    }

    public Group(String groupName) {
        this.groupName = groupName;
    }

    public Group(long groupId, String groupName) {
        this.groupId = groupId;
        this.groupName = groupName;
    }
}