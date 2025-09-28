package  com.tracking.controller.admin;

import com.tracking.dto.RoleDTO;
import com.tracking.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public List<RoleDTO> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping("/{id}")
    public RoleDTO getRoleById(@PathVariable Integer id) {
        return roleService.getRoleById(id);
    }

    @PostMapping
    public RoleDTO createRole(@RequestBody RoleDTO roleDTO) {
        return roleService.createRole(roleDTO);
    }

    @PutMapping("/{id}")
    public RoleDTO updateRole(@PathVariable Integer id, @RequestBody RoleDTO roleDTO) {
        return roleService.updateRole(id, roleDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable Integer id) {
        roleService.deleteRole(id);
    }
    @DeleteMapping("/reset")
    public void deleteAllAndReset() {
        roleService.deleteAllRolesAndResetId();
    }
}