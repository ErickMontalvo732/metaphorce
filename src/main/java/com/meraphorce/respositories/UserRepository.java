package com.meraphorce.respositories;

import com.meraphorce.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String>{

    boolean existsByNameOrEmail(String name, String email);

}
