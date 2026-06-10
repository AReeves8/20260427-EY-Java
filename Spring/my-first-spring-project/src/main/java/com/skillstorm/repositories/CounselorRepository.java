package com.skillstorm.repositories;

// import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.skillstorm.models.Counselor;

// this annotation designates this interface as a Spring Data JPA repository
// it will be automatically detected and registered as a bean in the Spring context
// a Repository is a more specific type of Spring component
// repositories come with all kinds of pre-baked methods, and we can add our own custom ones as well
@Repository
// we can extend various types of repositories to add more functionality
// the typing for this repository is <EntityClass, PrimaryKeyType>
public interface CounselorRepository extends CrudRepository<Counselor, Integer> {

    // unless we need custom methods, we don't have to add ANYTHING to this interface!
    Iterable<Counselor> findByLastNameStartsWith(String lastNameStartsWith);

    // this is a custom method with our own included specific query
    // @NativeQuery(value = "SELECT * FROM counselor WHERE first_name = ?1")
    // @Query(value = "SELECT * FROM counselor WHERE first_name = ?1", nativeQuery = true) // this is equivalent to the above
    // Iterable<Counselor> findByFirstNameExactly(String firstName);

    // the above works but is not best practices much of the time
    // if we use native queries, we are stuck with the version of SQL we're using now
    // with JPQL like below, we can swap databases without having to change these queries

    @Query("SELECT c FROM Counselor c WHERE c.firstName = :firstName")
    Iterable<Counselor> findByFirstNameExactly(@Param("firstName") String firstName);

}
