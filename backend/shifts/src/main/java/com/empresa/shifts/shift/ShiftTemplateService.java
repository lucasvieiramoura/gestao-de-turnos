package com.empresa.shifts.shift;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.empresa.shifts.shared.RecordStatus;
import com.empresa.shifts.shared.exception.BusinessRuleException;
import com.empresa.shifts.shared.exception.ResourceNotFoundException;
import com.empresa.shifts.shift.dto.ShiftTemplateResponse;
import com.empresa.shifts.shift.dto.ShiftTemplateRequest;


@Service 
public class ShiftTemplateService {
    
    private static final int MINUTES_PER_DAY = 24 * 60;
    private static final int MAX_SHIFT_MINUTES = MINUTES_PER_DAY; // Regra 02: nenhum turno acima de 24h

    private final ShiftTemplateRepository repository;

    // Injeção de dependência via construtor: o Spring entrega o repository pronto
    public ShiftTemplateService(ShiftTemplateRepository repository) {
        this.repository = repository;
    }

    @Transactional (readOnly = true)
    public List<ShiftTemplateResponse> findAll() {
        return repository.findAll().stream()
            .map(ShiftTemplateResponse::from)
            .toList();
    }

    @Transactional (readOnly = true)
    public ShiftTemplateResponse findById(Long id) {
        return ShiftTemplateResponse.from(getOrThrow(id));
    }

    @Transactional 
    public ShiftTemplateResponse create(ShiftTemplateRequest request) {
        ShiftTemplate template = new ShiftTemplate(request.name());
        applySchedule(template, request.startTime(), request.endTime());
        return  ShiftTemplateResponse.from(repository.save(template));
    }

    @Transactional 
    public ShiftTemplateResponse update(Long id, ShiftTemplateRequest request) {
        ShiftTemplate template = getOrThrow(id);
        template.setName(request.name());
        applySchedule(template, request.startTime(), request.endTime());
        return ShiftTemplateResponse.from(template); // sem o save(): o JPA detecta a mudança (dirty checking)
    }

    @Transactional 
    public void deactivate(Long id) {
        getOrThrow(id).setStatus(RecordStatus.INACTIVE); // Exclusão lógica (soft delete)
    }

    private ShiftTemplate getOrThrow(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Turno "+id+" não encontrado"));
    }

    private void applySchedule(ShiftTemplate template, LocalTime start, LocalTime end) {
        if (start.equals(end)) {
            throw new BusinessRuleException("Início e fim do turno nõa podem ser iguais");
        }

        boolean crossesMidnight = end.isBefore(start);
        long minutes = Duration.between(start, end).toMinutes();
        if (crossesMidnight) {
            minutes += MINUTES_PER_DAY; // 19:00 -> 07:00 dá -720; somando 1440 chegando ao 720
        }

        if (minutes > MAX_SHIFT_MINUTES) {
            throw new BusinessRuleException("O turno não pode ultrapassar 24 horas");
        }

        template.setStartTime(start);
        template.setEndTime(end);
        template.setCrossesMidnight(crossesMidnight);
        template.setDurationMinutes((int) minutes);
    }
    
}
