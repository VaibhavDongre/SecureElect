package com.learning.secureelect.service;

import com.learning.secureelect.entity.Election;

import java.util.List;

public interface ElectionService {

    Election createElection(Election election);

    List<Election> getAllElection();

    Election getElectionById(Long id);

    Election startElection(Long id);

    Election endElection(Long id);
}
