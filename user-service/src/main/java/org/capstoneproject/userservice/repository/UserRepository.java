package org.capstoneproject.userservice.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.capstoneproject.userservice.model.User;
public interface UserRepository extends JpaRepository<User, Long> {

}
