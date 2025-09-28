package com.tracking.service.impl;

import com.tracking.domain.Incident;
import com.tracking.dto.IncidentDTO;
import com.tracking.mapper.IncidentMapper;
import com.tracking.repository.IncidentRepository;
import com.tracking.service.IncidentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncidentServiceImpl implements IncidentService {

    private final IncidentRepository incidentRepository;
    private final IncidentMapper incidentMapper;

    public IncidentServiceImpl(IncidentRepository incidentRepository, IncidentMapper incidentMapper) {
        this.incidentRepository = incidentRepository;
        this.incidentMapper = incidentMapper;
    }

    @Override
    public List<IncidentDTO> getAllIncidents() {
        return incidentMapper.toDTOList(incidentRepository.findAll());
    }

    @Override
    public IncidentDTO getIncidentById(Integer id) {
        return incidentRepository.findById(id)
                .map(incidentMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Incident not found"));
    }

    @Override
    public IncidentDTO createIncident(IncidentDTO incidentDTO) {
        Incident incident = incidentMapper.toEntity(incidentDTO);
        return incidentMapper.toDTO(incidentRepository.save(incident));
    }

    @Override
    public IncidentDTO updateIncident(IncidentDTO incidentDTO) {
        Incident incident = incidentMapper.toEntity(incidentDTO);
        return incidentMapper.toDTO(incidentRepository.save(incident));
    }

    @Override
    public void deleteIncident(Integer id) {
        incidentRepository.deleteById(id);
    }
}
