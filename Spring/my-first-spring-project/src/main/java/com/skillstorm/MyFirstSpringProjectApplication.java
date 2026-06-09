package com.skillstorm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @SpringBootApplication is an annotation that combines @SpringBootConfiguration, @EnableAutoConfiguration, and @ComponentScan.
 * It lives atop the main class with our singular main method in it.
 * @SpringBootConfiguration allows this class to generate other config Beans if needed
 * @EnableAutoConfiguration enables Spring Boot's auto-configuration feature
 * @ComponentScan looks in this package and ALL subpackages for components/Beans to add to the context
 * -- you MUST place ALL your components/beans in this package or a subpackage of it
 */
@SpringBootApplication
public class MyFirstSpringProjectApplication {

	/**
	 * THE SPRING FRAMEWORK
	 * 
	 * Like many frameworks, Spring abstracts many things away for us and reduces boilerplate code.
	 * 
	 * Beans are little components within our application. They get drawn into the application context.
	 * We can easily swap out different beans for different functionalities and situations.
	 * The user or caller DOES NOT control which bean is used, as the framework handles the selection.
	 * If you want, say, a DataSource bean, the context gives you WHICHEVER ONE IT HAS AVAILABLE.
	 * 
	 * Spring uses "dependency" in two ways:
	 * 1. A piece of software on which our application depends
	 * 2. A Bean on which our class/other Bean depends -- this is where dependency injection comes in
	 * 
	 * Beans are singletons by default!! There ARE other scopes we can use, which we'll discuss later.
	 * 
	 * The Application Context holds all "live" beans and injects them wherever they're asked for.
	 * 
	 */

	public static void main(String[] args) {
		SpringApplication.run(MyFirstSpringProjectApplication.class, args);
	}

}
