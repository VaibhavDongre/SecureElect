package com.learning.secureelect.controller;

import com.learning.secureelect.entity.Candidate;
import com.learning.secureelect.entity.Election;
import com.learning.secureelect.service.ElectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/elections")
public class ElectionController {

    @Autowired
    private ElectionService electionService;

    @PostMapping
    public ResponseEntity<?> createElection(@RequestBody Election election) {
        Election created = electionService.createElection(election);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<?> listElections() {
        return ResponseEntity.ok(electionService.getAllElection());
    }

    @PutMapping("/start/{id}")
    public ResponseEntity<?> startElection(@PathVariable Long id) {
        Election updated = electionService.startElection(id);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/end/{id}")
    public ResponseEntity<?> endElection(@PathVariable Long id) {
        Election updated = electionService.endElection(id);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}/results")
    public ResponseEntity<?> getResults(@PathVariable Long id) {
        List<Candidate> list = electionService.getResults(id);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}/stats")
    public ResponseEntity<?> getStats(@PathVariable Long id) {
        Map<String, Object> stats = electionService.getElectionStats(id);
        return ResponseEntity.ok(stats);
    }
}

