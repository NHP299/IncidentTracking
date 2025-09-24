package  com.tracking.service;

import  com.tracking.dto.IncidentDTO;

import java.util.List;

public interface IncidentService {
    List<IncidentDTO> getAllIncidents();
    List<IncidentDTO> getIncidentsByReporterId(Integer reporterId);
    IncidentDTO getIncidentById(Integer id);
    IncidentDTO createIncident(IncidentDTO incidentDTO);
    IncidentDTO updateIncident(Integer id, IncidentDTO incidentDTO);
    void cancelIncident(Integer id);
    List<IncidentDTO> searchIncidents(String keyword, String status, String priority);
}
