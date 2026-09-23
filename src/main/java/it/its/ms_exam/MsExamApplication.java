package it.its.ms_exam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients 
public class MsExamApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsExamApplication.class, args);
	}

}
