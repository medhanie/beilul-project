package io.medhanie.beilul.repository;

import io.medhanie.beilul.entity.AdminZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminZoneRepository extends JpaRepository<AdminZone, Short> {
}
