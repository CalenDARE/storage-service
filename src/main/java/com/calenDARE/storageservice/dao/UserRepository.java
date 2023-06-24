package com.calenDARE.storageservice.dao;

import com.calenDARE.storageservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
