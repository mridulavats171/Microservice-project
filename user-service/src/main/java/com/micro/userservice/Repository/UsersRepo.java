package com.micro.userservice.Repository;

import com.micro.userservice.Domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepo extends JpaRepository<Users, String> {
}
