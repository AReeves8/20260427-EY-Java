package com.skillstorm.actuator;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import com.skillstorm.repositories.StudentRepository;

// Spring Actuator provides a number of useful endpoints for API management/monitoring
// you can check on health, beans, sessions, memory use, etc.
// you can also set up custom endpoints to take administrative action, like we're doing here
// YOU MUST include the actuator dependency and configure things in your properties file

// this is not a repo/service/controller, but it IS a bean, so we need to use @Component to get it scanned
@Component
// this designates the URL of the actuator endpoint, which will be /actuator/student-count
@Endpoint(id = "student-count")
public class StudentCounter {

    // we have full access to the application context and all beans/objects
    private final StudentRepository repo;

    public StudentCounter(StudentRepository repo) {
        this.repo = repo;
    }

    // we annotate the handler method for this endpoint with either @ReadOperation, @WriteOperation or @DeleteOperation
    @ReadOperation
    public Map<String, Integer> countStudents() {

        Map<String, Integer> response = new HashMap<>();

        response.put("student-count", (int)repo.count());

        return response;
        
    }

}
