package  com.tracking.service.impl;

import lombok.RequiredArgsConstructor;
import  com.tracking.domain.Incident;
import  com.tracking.dto.IncidentDTO;
import  com.tracking.mapper.IncidentMapper;
import  com.tracking.repository.IncidentRepository;
import  com.tracking.service.IncidentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IncidentServiceImpl implements IncidentService {

    private final IncidentRepository incidentRepository;
    private final IncidentMapper incidentMapper;

    @Override
    public List<IncidentDTO> getAllIncidents() {
        return incidentRepository.findAll()
                .stream()
                .map(incidentMapper::toDTO)
                .toList();
    }

    @Override
    public List<IncidentDTO> getIncidentsByReporterId(Integer reporterId) {
        return incidentRepository.findByReporter_UserId(reporterId)
                .stream()
                .map(incidentMapper::toDTO)
                .toList();
    }

    @Override
    public IncidentDTO getIncidentById(Integer id) {
        return incidentRepository.findById(id)
                .map(incidentMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Incident not found with id: " + id));
    }

    @Override
    public IncidentDTO createIncident(IncidentDTO incidentDTO) {
        Incident incident = incidentMapper.toEntity(incidentDTO);
        incident = incidentRepository.save(incident);
        return incidentMapper.toDTO(incident);
    }

    @Override
    public IncidentDTO updateIncident(Integer id, IncidentDTO incidentDTO) {
        Incident incident = incidentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Incident not found with id: " + id));

        // cập nhật dữ liệu
        incident.setTitle(incidentDTO.getTitle());
        incident.setDescription(incidentDTO.getDescription());
        incident.setStatus(incidentDTO.getStatus());
        incident.setPriority(incidentDTO.getPriority());

        incident = incidentRepository.save(incident);
        return incidentMapper.toDTO(incident);
    }

    @Override
    public void cancelIncident(Integer id) {
        Incident incident = incidentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Incident not found with id: " + id));

        incident.setStatus("CANCELLED"); // hoặc 1 enum Status.CANCELLED
        incidentRepository.save(incident);
    }

    @Override
    public List<IncidentDTO> searchIncidents(String keyword, String status, String priority) {
        return incidentRepository.search(keyword, status, priority)
                .stream()
                .map(incidentMapper::toDTO)
                .toList();
    }
}
