package com.titsquad.SnakePractice.service;

import com.titsquad.SnakePractice.entity.PlayerRecord;
import com.titsquad.SnakePractice.repository.PlayerRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PlayerRecordService {

    private final PlayerRecordRepository playerRecordRepository;

    public PlayerRecordService(PlayerRecordRepository playerRecordRepository) {
        this.playerRecordRepository = playerRecordRepository;
    }

    // Создание или обновление записи
    public PlayerRecord saveOrUpdateRecord(PlayerRecord playerRecord) {
        return playerRecordRepository.save(playerRecord);
    }

    // Получение записи по nickname
    public PlayerRecord getRecordByNickname(String nickname) {
        return playerRecordRepository.findById(nickname)
                .orElseThrow(() -> new RuntimeException("Record not found for nickname: " + nickname));
    }

    // Получение всех записей
    public List<PlayerRecord> getAllRecords() {
        return playerRecordRepository.findAll();
    }

    // Удаление записи
    public void deleteRecord(String nickname) {
        playerRecordRepository.deleteById(nickname);
    }

    // Получение записей по уровню сложности
    public List<PlayerRecord> getRecordsByDifficulty(String difficulty) {
        return playerRecordRepository.findByDifficulty(difficulty);
    }

    // Получение топ N рекордов
    public List<PlayerRecord> getTopRecords(int limit) {
        return playerRecordRepository.findTopRecords(limit);
    }

    // Обновление рекорда
    public PlayerRecord updateRecord(String nickname, int newRecord) {
        PlayerRecord record = getRecordByNickname(nickname);
        record.setRecord(newRecord);
        return playerRecordRepository.save(record);
    }
}
