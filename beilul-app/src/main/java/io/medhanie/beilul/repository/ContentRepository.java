package io.medhanie.beilul.repository;

import io.medhanie.beilul.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentRepository extends JpaRepository<Comment, Integer> {
}
