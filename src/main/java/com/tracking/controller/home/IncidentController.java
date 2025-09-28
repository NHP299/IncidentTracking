package  com.tracking.controller.home;

import com.tracking.dto.IncidentDTO;
import com.tracking.service.IncidentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incidents")
@CrossOrigin(origins = "http://localhost:3000") // Cho phép frontend React/Angular truy cập
public class IncidentController {

    private final IncidentService incidentService;

    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    @GetMapping
    public List<IncidentDTO> getAllIncidents() {
        return incidentService.getAllIncidents();
    }

    @GetMapping("/{id}")
    public IncidentDTO getIncidentById(@PathVariable Integer id) {
        return incidentService.getIncidentById(id);
    }

    @PostMapping
    public IncidentDTO createIncident(@RequestBody IncidentDTO incidentDTO) {
        return incidentService.createIncident(incidentDTO);
    }

    @PutMapping("/{id}")
    public IncidentDTO updateIncident(@PathVariable Integer id, @RequestBody IncidentDTO incidentDTO) {
        incidentDTO.setIncidentId(id);
        return incidentService.updateIncident(incidentDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteIncident(@PathVariable Integer id) {
        incidentService.deleteIncident(id);
    }
}