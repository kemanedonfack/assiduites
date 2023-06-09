package com.uic.assiduite.controller;

import com.uic.assiduite.model.Request;
import com.uic.assiduite.service.RequestService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author engome
 */
@RestController
@RequestMapping("/api/request")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @GetMapping
    public List<Request> getAllRequest() {
        return requestService.getAllRequest();
    }

    @GetMapping("/{id}")
    public Optional<Request> getById(@PathVariable int id) {
        return requestService.findById(id);
    }

    @PostMapping("/add")
    public Request createRequest(@RequestBody Request juste) {
        return requestService.createRequest(juste);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Optional<Request>> update(@PathVariable int id, @RequestBody Request juste) {
        return ResponseEntity.ok(requestService.updateRequest(id, juste));
    }
}
