package com.tracking.repository;

import com.tracking.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    @Modifying
    @Transactional
    @Query(value = "DBCC CHECKIDENT ('departments', RESEED, 0)", nativeQuery = true)
    void resetIdentity();
}
