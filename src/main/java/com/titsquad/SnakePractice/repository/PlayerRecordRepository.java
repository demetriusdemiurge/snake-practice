package com.titsquad.SnakePractice.repository;

import com.titsquad.SnakePractice.entity.PlayerRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRecordRepository extends JpaRepository<PlayerRecord, String> {
    // Можно добавить кастомные методы запросов

    // Пример кастомного метода для поиска по difficulty
    List<PlayerRecord> findByDifficulty(String difficulty);

    // Пример метода для получения топ N рекордов
    @Query("SELECT p FROM PlayerRecord p ORDER BY p.record DESC LIMIT :limit")
    List<PlayerRecord> findTopRecords(@Param("limit") int limit);
}
