package com.DigitalSettings.thermostat.repository;

import com.DigitalSettings.thermostat.entity.UnverifiedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UnverifiedUserRepo extends JpaRepository<UnverifiedUser, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM UnverifiedUser u WHERE u.createdAt < :time")
    void deleteByCreatedAtBefore(@Param("time") LocalDateTime time);

    Optional<UnverifiedUser> findByUsername(String username);
}
