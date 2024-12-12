package com.example.T_F_E;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    User findByUsername(String username);

    User findByEmail(String email);

    void deleteById(int user_Id);
}
