package com.empresa.shifts.profession;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.empresa.shifts.profession.dto.ProfessionRequest;
import com.empresa.shifts.profession.dto.ProfessionResponse;
import com.empresa.shifts.shared.RecordStatus;
import com.empresa.shifts.shared.exception.BusinessRuleException;
import com.empresa.shifts.shared.exception.ResourceNotFoundException;
import com.empresa.shifts.shift.ShiftTemplate;
import com.empresa.shifts.shift.ShiftTemplateRepository;
import com.empresa.shifts.shift.ShiftTemplateService;


@Service 
public class ProfessionService {
    
    private final ProfessionRepository repository;
    private final ShiftTemplateRepository shiftTemplateRepository;

    // Injeção de dependência via construtor: o Spring estrega o repository pronto
    public ProfessionService(ProfessionRepository repository, ShiftTemplateRepository shiftTemplateRepository) {
        this.repository = repository;
        this.shiftTemplateRepository = shiftTemplateRepository;
    }


    @Transactional (readOnly = true)
    public List<ProfessionResponse> findAll() {
        return repository.findAll().stream()
            .map(ProfessionResponse::from)
            .toList();
    }

    @Transactional (readOnly = true)
    public ProfessionResponse findById(Long id){
        return ProfessionResponse.from(getOrThrow(id));
    }

    @Transactional 
    public ProfessionResponse create(ProfessionRequest request) {
        ShiftTemplate template = getActiveShiftTemplate(request.defaultShiftTemplateId());
        Profession profession = new Profession(request.name(), template);
        return ProfessionResponse.from(repository.save(profession));
    }

    @Transactional 
    public ProfessionResponse update(Long id, ProfessionRequest request) {
        Profession profession = getOrThrow(id);
        profession.setName(request.name());
        profession.setDefaultShiftTemplate(getActiveShiftTemplate(request.defaultShiftTemplateId()));
        return ProfessionResponse.from(profession); // dirty checking: sem save()
   }

    @Transactional 
    public void deactivate(Long id){
        getOrThrow(id).setStatus(RecordStatus.INACTIVE);
    }

    private Profession getOrThrow(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Profisional "+id+" não encontrado"));
    }

    private  ShiftTemplate getActiveShiftTemplate( Long shiftTemplateId ) {
        ShiftTemplate template = shiftTemplateRepository.findById(shiftTemplateId)
            .orElseThrow(() -> new BusinessRuleException("Turno "+shiftTemplateId+ " não existe"));

        if(template.getStatus() != RecordStatus.ACTIVE) {
            throw new BusinessRuleException("Turno "+shiftTemplateId+" está inativo");
        }
        return template;
    }

}
