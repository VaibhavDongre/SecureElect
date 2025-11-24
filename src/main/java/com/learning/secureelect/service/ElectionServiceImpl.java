package com.learning.secureelect.service;

import com.learning.secureelect.entity.Election;
import com.learning.secureelect.repository.ElectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElectionServiceImpl implements ElectionService{

    @Autowired
    private ElectionRepository electionRepository;

    @Override
    public Election createElection(Election election) {
        //to check if title already exists
        if (electionRepository.findByTitle(election.getTitle()).isPresent()) {
            throw new RuntimeException("Election with this title already exist");
        }

        election.setActive(false);
        election.setCompleted(false);

        return electionRepository.save(election);
    }

    @Override
    public List<Election> getAllElection() {
        return electionRepository.findAll();
    }

    @Override
    public Election getElectionById(Long id) {
        return electionRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Election not found"));
    }

    @Override
    public Election startElection(Long id) {
        Election election = getElectionById(id);
        election.setActive(true);
        return electionRepository.save(election);
    }

    @Override
    public Election endElection(Long id) {
        Election election = getElectionById(id);
        election.setActive(false);
        election.setCompleted(true);
        return electionRepository.save(election);
    }
}
