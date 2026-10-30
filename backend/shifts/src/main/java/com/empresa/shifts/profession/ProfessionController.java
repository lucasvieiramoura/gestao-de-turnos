package com.empresa.shifts.profession;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.empresa.shifts.profession.dto.ProfessionRequest;
import com.empresa.shifts.profession.dto.ProfessionResponse;

import jakarta.validation.Valid;

@RestController 
@RequestMapping ("/api/professions")
public class ProfessionController {
    
    private final ProfessionService service;

    public ProfessionController(ProfessionService service){
        this.service = service;
    }

    @GetMapping 
    public List<ProfessionResponse> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ProfessionResponse get(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<ProfessionResponse> create(@Valid @RequestBody ProfessionRequest request) {
        ProfessionResponse created = service.create(request);
        URI location = URI.create("/api/professions/"+ created.id());
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ProfessionResponse update(@PathVariable Long id, @Valid @RequestBody ProfessionRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivate(@PathVariable Long id) {
        service.deactivate(id);
    }

}
