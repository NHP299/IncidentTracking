package com.tracking.repository;

import com.tracking.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Modifying
    @Transactional
    @Query(value = "DBCC CHECKIDENT ('roles', RESEED, 0)", nativeQuery = true)
    void resetIdentity();
}
