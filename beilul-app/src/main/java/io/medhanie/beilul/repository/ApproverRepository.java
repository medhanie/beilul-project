package io.medhanie.beilul.repository;

import io.medhanie.beilul.entity.Approver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApproverRepository extends JpaRepository<Approver, Integer> {
}
