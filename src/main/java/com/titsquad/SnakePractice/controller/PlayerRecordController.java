package com.titsquad.SnakePractice.controller;

import com.titsquad.SnakePractice.entity.PlayerRecord;
import com.titsquad.SnakePractice.service.PlayerRecordService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/records")
public class PlayerRecordController {

    private final PlayerRecordService playerRecordService;

    public PlayerRecordController(PlayerRecordService playerRecordService) {
        this.playerRecordService = playerRecordService;
    }

    @PostMapping
    public PlayerRecord createOrUpdateRecord(@RequestBody PlayerRecord playerRecord) {
        return playerRecordService.saveOrUpdateRecord(playerRecord);
    }

    @GetMapping("/{nickname}")
    public PlayerRecord getRecord(@PathVariable String nickname) {
        return playerRecordService.getRecordByNickname(nickname);
    }

    @GetMapping
    public List<PlayerRecord> getAllRecords() {
        return playerRecordService.getAllRecords();
    }

    @DeleteMapping("/{nickname}")
    public void deleteRecord(@PathVariable String nickname) {
        playerRecordService.deleteRecord(nickname);
    }

    @GetMapping("/difficulty/{difficulty}")
    public List<PlayerRecord> getRecordsByDifficulty(@PathVariable String difficulty) {
        return playerRecordService.getRecordsByDifficulty(difficulty);
    }

    @GetMapping("/top/{limit}")
    public List<PlayerRecord> getTopRecords(@PathVariable int limit) {
        return playerRecordService.getTopRecords(limit);
    }

    @PatchMapping("/{nickname}/record")
    public PlayerRecord updateRecord(@PathVariable String nickname, @RequestParam int newRecord) {
        return playerRecordService.updateRecord(nickname, newRecord);
    }
}
