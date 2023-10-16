package com.grocery.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

import com.grocery.dto.User;
import com.grocery.enums.AccountStatus;
import com.grocery.enums.Role;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByEmail(String email);

    public Optional<User> findByPhone(String phone);

    public List<User> findAllByRoleAndStatus(Role role, AccountStatus status);
}
