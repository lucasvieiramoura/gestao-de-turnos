package com.empresa.shifts.shift;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.empresa.shifts.shift.dto.ShiftTemplateRequest;
import com.empresa.shifts.shift.dto.ShiftTemplateResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/shift-templates")
public class ShiftTemplateController {
    
    private final ShiftTemplateService service;

    public ShiftTemplateController(ShiftTemplateService service) {
        this.service = service;
    }

    @GetMapping 
    public List<ShiftTemplateResponse> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ShiftTemplateResponse get(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @PreAuthorize ("hasAnyRole('ADMIN','COORDINATOR')")
    public ResponseEntity<ShiftTemplateResponse> create(@Valid @RequestBody ShiftTemplateRequest request) {
        ShiftTemplateResponse created = service.create(request);
        URI location = URI.create("/api/shift-templates/"+created.id());
        return ResponseEntity.created(location).body(created); // 201 + header Location
    }

    @PutMapping("/{id}")
    @PreAuthorize ("hasAnyRole('ADMIN','COORDINATOR')")
    public ShiftTemplateResponse update(@PathVariable Long id, @Valid @RequestBody ShiftTemplateRequest request){
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize ("hasAnyRole('ADMIN','COORDINATOR')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactive(@PathVariable Long id) {
        service.deactivate(id);
    }

}
