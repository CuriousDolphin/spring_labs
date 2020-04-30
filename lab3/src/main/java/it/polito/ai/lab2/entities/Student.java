package it.polito.ai.lab2.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
public class Student {
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name="student_course",
                joinColumns = @JoinColumn(name="student_id"),
                inverseJoinColumns = @JoinColumn(name="course_name") )
    List<Course> courses = new ArrayList<>();
    @ManyToMany(mappedBy = "members")
    List<Team> teams= new ArrayList<>();
    @Id
    private String id;
    private String name;
    private String firstName;

    @Override
    public String toString(){
        return this.id+"_"+this.name+"_"+this.firstName;
    }


    public void addCourse(Course course){
        if(!this.courses.contains(course)) this.courses.add(course);
        if(!course.getStudents().contains(this)) course.addStudent(this);

    }

    public void addTeam(Team t){
        if(!this.teams.contains(t)) this.teams.add(t);
        if(!t.getMembers().contains(this))  t.addMembers(this);
    }

    public void removeTeam(Team t){
        if(this.teams.contains(t)) this.teams.remove(t);
        if(t.getMembers().contains(this))  t.removeMembers(this);
    }
}
