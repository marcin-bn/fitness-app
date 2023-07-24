package pl.dundersztyc.fitnessapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FitnessAppApplication {

	public static void main(String[] args) {
		var ctx = SpringApplication.run(FitnessAppApplication.class, args);
		System.out.println("A");
	}

}
