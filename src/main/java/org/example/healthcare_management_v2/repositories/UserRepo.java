package org.example.healthcare_management_v2.repositories;

import org.example.healthcare_management_v2.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);


    Optional<User> findByUsernameAndDeletedAtIsNotNull(String username);

    @Query(value = "SELECT * FROM users WHERE username = :username", nativeQuery = true)
    Optional<User> findByUsernameByAllStatus(@Param("username") String username);

    @Query(value = "SELECT * FROM users", nativeQuery = true)
    Page<User> allUserInDb(@NonNull Pageable pageable);

    @Query(value = "SELECT * FROM users WHERE status = :status AND deleted_at IS NULL", nativeQuery = true)
    Page<User> findByStatus(@Param("status") String status, Pageable pageable);

    @Query(value = "SELECT * FROM users WHERE deleted_at IS NOT NULL", nativeQuery = true)
    Page<User> findAllDeleted(Pageable pageable);


}
