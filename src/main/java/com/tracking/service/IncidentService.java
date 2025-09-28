package com.tracking.service;

import com.tracking.dto.IncidentDTO;

import java.util.List;

public interface IncidentService {
    List<IncidentDTO> getAllIncidents();
    IncidentDTO getIncidentById(Integer id);
    IncidentDTO createIncident(IncidentDTO incidentDTO);
    IncidentDTO updateIncident(IncidentDTO incidentDTO);
    void deleteIncident(Integer id);
}
