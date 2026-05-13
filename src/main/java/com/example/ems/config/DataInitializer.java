package com.example.ems.config;

import com.example.ems.model.Employee;
import com.example.ems.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository repository) {
        return args -> {
            if (repository.count() < 1500) {
                repository.deleteAll(); // Clear old data to start fresh with 1500
                
                String[] firstNames = {"James", "Mary", "Robert", "Patricia", "John", "Jennifer", "Michael", "Linda", "William", "Elizabeth", "David", "Barbara", "Richard", "Susan", "Joseph", "Jessica", "Thomas", "Sarah", "Charles", "Karen"};
                String[] lastNames = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez", "Martinez", "Hernandez", "Lopez", "Gonzalez", "Wilson", "Anderson", "Thomas", "Taylor", "Moore", "Jackson", "Martin"};
                String[] departments = {"Engineering", "Product", "Design", "Marketing", "Operations", "Finance", "HR", "Legal", "Sales", "Customer Success"};
                String[] roles = {"Junior", "Associate", "Senior", "Lead", "Principal", "Manager", "Director", "VP"};
                String[] titles = {"Engineer", "Analyst", "Designer", "Strategist", "Coordinator", "Specialist", "Architect"};

                List<Employee> dummyEmployees = new ArrayList<>();
                Random random = new Random();

                for (int i = 1; i <= 1500; i++) {
                    String fName = firstNames[random.nextInt(firstNames.length)];
                    String lName = lastNames[random.nextInt(lastNames.length)];
                    String dept = departments[random.nextInt(departments.length)];
                    String role = roles[random.nextInt(roles.length)];
                    String title = titles[random.nextInt(titles.length)];
                    
                    String email = fName.toLowerCase() + "." + lName.toLowerCase() + i + "@workforce.os";
                    String jobTitle = role + " " + title;
                    Double salary = 40000 + random.nextDouble() * 120000;
                    LocalDate hireDate = LocalDate.now().minusDays(random.nextInt(3650)); // Hired in last 10 years

                    dummyEmployees.add(new Employee(null, fName, lName, email, dept, jobTitle, salary, hireDate));
                    
                    // Batch save every 500 records to improve performance
                    if (i % 500 == 0) {
                        repository.saveAll(dummyEmployees);
                        dummyEmployees.clear();
                        System.out.println(">> Seeded " + i + " records...");
                    }
                }
                System.out.println(">> Database successfully seeded with 1500 employees.");
            }
        };
    }
}
