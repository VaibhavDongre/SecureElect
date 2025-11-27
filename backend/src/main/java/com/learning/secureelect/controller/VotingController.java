package com.learning.secureelect.controller;

import com.learning.secureelect.dto.VoteRequest;
import com.learning.secureelect.entity.User;
import com.learning.secureelect.repository.UserRepository;
import com.learning.secureelect.service.VotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class VotingController {

    @Autowired
    private VotingService votingService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/vote")
    public ResponseEntity<?> castVote(@RequestBody VoteRequest request) {
        //get current user email from security context(we stored email as principal)
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || auth.getPrincipal() == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        String email = (String) auth.getPrincipal();
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("User not found"));

        votingService.castVote(user.getId(), request.getElectionId(), request.getCandidateId());

        return ResponseEntity.ok("Vote cast successfully");
    }
}
