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

    public PlayerRecord saveOrUpdateRecord(PlayerRecord playerRecord) {
        return playerRecordRepository.save(playerRecord);
    }

    public PlayerRecord getRecordByNickname(String nickname) {
        return playerRecordRepository.findById(nickname)
                .orElseThrow(() -> new RuntimeException("Record not found for nickname: " + nickname));
    }

    public List<PlayerRecord> getAllRecords() {
        return playerRecordRepository.findAll();
    }

    public void deleteRecord(String nickname) {
        playerRecordRepository.deleteById(nickname);
    }

    public List<PlayerRecord> getRecordsByDifficulty(String difficulty) {
        return playerRecordRepository.findByDifficulty(difficulty);
    }

    public List<PlayerRecord> getTopRecords(int limit) {
        return playerRecordRepository.findTopRecords(limit);
    }

    public PlayerRecord updateRecord(String nickname, int newRecord) {
        PlayerRecord record = getRecordByNickname(nickname);
        record.setRecord(newRecord);
        return playerRecordRepository.save(record);
    }
}
