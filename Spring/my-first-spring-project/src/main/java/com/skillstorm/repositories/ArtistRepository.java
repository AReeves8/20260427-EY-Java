package com.skillstorm.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.skillstorm.models.Artist;

// this annotation designates this interface as a Spring Data JPA repository
// it will be automatically detected and registered as a bean in the Spring context
// a Repository is a more specific type of Spring component
// repositories come with all kinds of pre-baked methods, and we can add our own custom ones as well
@Repository
// we can extend various types of repositories to add more functionality
// the typing for this repository is <EntityClass, PrimaryKeyType>
public interface ArtistRepository extends CrudRepository<Artist, Integer> {

    // unless we need custom methods, we don't have to add ANYTHING to this interface!

}
