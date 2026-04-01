package com.help.tap.repository;

import com.help.tap.model.Illness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IllnessRepository extends JpaRepository<Illness, Integer> {

    List<Illness> findByUserId(Integer userId);

    List<Illness> findByUserIdAndIsSensitive(Integer userId, Boolean isSensitive);
}
