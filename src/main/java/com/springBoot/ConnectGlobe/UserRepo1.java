package com.springBoot.ConnectGlobe;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo1 extends JpaRepository<UserModel, Integer>{

	boolean existsByEmail(String email);
}
