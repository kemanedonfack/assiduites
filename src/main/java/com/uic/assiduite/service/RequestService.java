package com.uic.assiduite.service;

import com.uic.assiduite.model.Request;
import com.uic.assiduite.repository.RequestRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author engome
 */
@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    public List<Request> getAllRequest() {
        return requestRepository.findAll();
    }

    public Optional<Request> findById(int id) {
        return requestRepository.findById(id);
    }

    public Request createRequest(Request justif) {
        return requestRepository.save(justif);
    }

    public Optional<Request> updateRequest(int id, Request justif) {
        if (!requestRepository.existsById(id)) {
            return Optional.empty();
        }
        justif.setId(id);
        return Optional.of(requestRepository.save(justif));
    }

    public boolean deleteRequest(int id) {
        if (!requestRepository.existsById(id)) {
            return false;
        }
        requestRepository.deleteById(id);
        return true;
    }
}
