package com.skillstorm.spring_data_jpa_intro;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * SpringBootTest
 * 		- denote a class as a test class, but it loads the ENTIRE application context
 * 			- can use WebMvcTest can be used to only load a single controller and test that
 * 			- DataJpaTest for testing repositories
 * 		- loading the entire app context just means it's going to be slower
 */
@SpringBootTest
class SpringDataJpaIntroApplicationTests {

	/**
	 * JUnit Annotations
	 * 		@Test - signifies a test method
	 * 			- need to return void and take in no params
	 * 		@Disabled - marks a test to be ignored when all tests are ran
	 * 			- can be used with @Test
	 * 		
	 * 		@BeforeAll - runs once before EVERY test in the class
	 * 			- setup something used in all tests
	 * 				- expensive operations like creating large files or setting up database connections
	 * 			- need to be static
	 * 		@BeforeEach - runs before each individual test method in the class
	 * 			- setup state for each test
	 * 				- setting standard mock/test data to be used in your tests
	 * 		@AfterEach - runs after each individual test method in the class
	 * 			- teardown state for each test
	 * 				- release resources created in your BeforeEach 
	 * 					- closing a stream, deleting temp files, rolling back transactions, etc.
	 * 		@AferAll - runs once after EVERY test in the class
	 * 			- teardown what was setup in your BeforeAll
	 * 				- closing database connection, deleting files, etc.
	 * 			- need to be static
	 */


	/**
	 * TEST DRIVEN DEVELOPMENT (TDD)
	 * 		- write your tests FIRST
	 * 			- THEN write your code to pass the tests
	 */


	
	@Test
	void contextLoads() {
	}

}
