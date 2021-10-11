package com.springBoot.ConnectGlobe;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo1 extends JpaRepository<UserModel, Integer>{

	//UserModel findByEmail(String email);
	boolean existsByEmail(String email);
}
