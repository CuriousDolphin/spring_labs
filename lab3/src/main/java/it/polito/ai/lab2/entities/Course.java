package it.polito.ai.lab2.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Course {

    @Id
    private String name;
    private int min;
    private int max;
    private boolean enabled;

    @ManyToMany(mappedBy = "courses")
    private List<Student> students = new ArrayList<>();

    @OneToMany(mappedBy = "course")
    private List<Team> teams;

    public void addStudent(Student s){
        if(!this.students.contains(s))  this.students.add(s);
        if(!s.getCourses().contains(this))  s.addCourse(this);
    }

    public void addTeam(Team t){
        if(!this.teams.contains(t)) this.teams.add(t);
        if(!t.getCourse().equals(this)) t.setCourse(this);
    }

    public void removeTeam(Team t){
        if(this.teams.contains(t)) this.teams.remove(t);
        if(t.getCourse().equals(this)) t.setCourse(null);

    }

    @Override
    public String toString(){
        return this.name+"_"+this.min+"_"+this.max;
    }
}
