package it.polito.ai.lab2;

import it.polito.ai.lab2.dtos.CourseDTO;
import it.polito.ai.lab2.dtos.StudentDTO;
import it.polito.ai.lab2.dtos.TeamDTO;
import it.polito.ai.lab2.entities.Student;
import it.polito.ai.lab2.repositories.CourseRepository;
import it.polito.ai.lab2.repositories.StudentRepository;
import it.polito.ai.lab2.services.TeamService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@SpringBootApplication
public class Lab2Application {
    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(Lab2Application.class, args);
    }

    @Bean
    CommandLineRunner runner(TeamService teamService) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                /* StudentDTO s = new StudentDTO();
                s.setFirstName("mario");
                s.setName("bianchi");
                s.setId("mrbn");

                CourseDTO c = new CourseDTO();
                c.setName("AppInternet");
                c.setMin(10);
                c.setMax(100);
                c.setEnabled(true);


                System.out.println("risultato inserimento "+teamService.addStudent(s)+" "+s.toString());

                System.out.println(teamService.getStudent("mrbn").toString());

                System.out.println(teamService.getStudent("notvalid").toString());

                System.out.println("All students ");
                teamService.getAllStudents().stream().forEach(studentDTO -> System.out.println(studentDTO.toString()));

                System.out.println("All courses ");
                teamService.getAllCourses().stream().forEach(courseDTO -> System.out.println(courseDTO.toString()));

                System.out.println("risultato inserimento "+teamService.addCourse(c)+" "+c.toString());
                System.out.println(teamService.getCourse("AppInternet").toString());

                Boolean res = teamService.addStudentToCourse("mrbn","AppInternet");
                System.out.println("Add student to course "+res.toString());

                Boolean res2 = teamService.addStudentToCourse("id2","ml");
                System.out.println("Add student to course "+res2.toString());

                System.out.println("enableCourse");
                teamService.enableCourse("ml");
                System.out.println("disabledCourse");
                teamService.disableCourse("appinternet");*/

                /*StudentDTO s = new StudentDTO();
                s.setFirstName("carlo");
                s.setName("verdi");
                s.setId("crlvdi");

                StudentDTO s2 = new StudentDTO();
                s2.setFirstName("carmelo");
                s2.setName("rossi");
                s2.setId("crml");
                List<StudentDTO> list = new ArrayList<>();
                list.add(s);
                list.add(s2);

                List<Boolean> ris = teamService.addAll(list);
                System.out.println("Risultato inserimento multiplo " + ris.toString());

                List<Boolean> ris2 = teamService.enrollAll(Arrays.asList("crml22", "crlvdi11", "NOTVALID"), "AppInternet");
                System.out.println("Risultato enroll multiplo " + ris2.toString());

                //load file
                File file = new ClassPathResource("static/students.csv").getFile();
                if (file.exists()) {
                    System.out.println("File Found : " + file.exists());

                    Reader reader = new BufferedReader(
                            new InputStreamReader(
                                    new ClassPathResource("static/students.csv").getInputStream()
                            )
                    );

                    List<Boolean> ris3=teamService.addAndEnroll(reader, "pds");
                    System.out.println("Risultato inserimento da csv  " + ris3.toString());

                }

                String id= "id1";
                List<CourseDTO> courses = teamService.getCourses(id);
                System.out.println("Corsi di "+id);
                courses.forEach(System.out::println);

                System.out.println("Team di "+id);
                List<TeamDTO> teams= teamService.getTeamsForStudent(id);
                teams.forEach(System.out::println);*/

                /*List<String> students=new ArrayList<>();
                students.add("id1");
                students.add("crml");
                students.add("s1");
                students.add("s1");

                TeamDTO team=teamService.proposeTeam("AppInternet","QUELLI CHE",students);

                System.out.println("TEAM CREATO "+team.toString());*/

                /*String id= "id1";
                System.out.println("Team di "+id);
                List<TeamDTO> teams= teamService.getTeamsForStudent(id);
                teams.forEach(System.out::println);*/
                /*List<String> students=new ArrayList<>();
                students.add("s5");
                students.add("crlvdi");
                //TeamDTO team=teamService.proposeTeam("AppInternet","SECONDOTEAM",students);

                String courseName="AppInternet";
                System.out.println("Team iscritti a "+courseName );
                List<TeamDTO> teams = teamService.getTeamForCourse(courseName);
                teams.forEach(System.out::println);



                System.out.println("Tutti i studenti in team di "+courseName);
                List<StudentDTO> studs = teamService.getStudentsInTeams(courseName);
                studs.forEach(System.out::println);


                System.out.println("Studenti disponibili ");
                List<StudentDTO> studs2 = teamService.getAvailableStudents(courseName);
                studs2.forEach(System.out::println);*/

            }
        };
    }
}
