package ua.com.foxminded.sqljdbcschool;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ua.com.foxminded.sqljdbcschool.service.SchoolService;


@SpringBootApplication
public class SqlJdbcSchoolApplication {


	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(SqlJdbcSchoolApplication.class, args);
		SchoolService schoolService = context.getBean(SchoolService.class);
	}

}
