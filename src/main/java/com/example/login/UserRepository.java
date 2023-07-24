package com.example.login;

import org.springframework.data.jpa.repository.JpaRepository;
interface UserRepository extends JpaRepository<myUser, Long> {}