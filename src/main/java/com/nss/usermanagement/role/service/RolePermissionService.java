package com.nss.usermanagement.role.service;

import com.nss.usermanagement.role.entity.ModulePermission;
import com.nss.usermanagement.role.entity.Operation;
import com.nss.usermanagement.role.entity.RolePermission;
import com.nss.usermanagement.role.mapper.ModulePermissionMapper;
import com.nss.usermanagement.role.mapper.RolePermissionMapper;
import com.nss.usermanagement.role.model.ModulePermissionDTO;
import com.nss.usermanagement.role.model.RolePermissionDTO;
import com.nss.usermanagement.role.repository.ModulePermissionRepo;
import com.nss.usermanagement.role.repository.OperationRepository;
import com.nss.usermanagement.role.repository.RolePermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RolePermissionService {

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Autowired
    private ModulePermissionRepo modulePermissionRepo;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    private ModulePermissionMapper modulePermissionMapper;
    @Autowired
    private OperationRepository operationRepository;


    public RolePermission createRolePermission(RolePermission rolePermissionReq) {
        List<ModulePermission> modulePermissions = new ArrayList<>();
        for (ModulePermission modulePermission : rolePermissionReq.getModulePermissions()) {
            // Ensure all operations are attached to the session
            List<Operation> attachedOperations = modulePermission.getOperations().stream()
                    .map(operation -> operationRepository.findById(operation.getId())
                            .orElseThrow(() -> new IllegalArgumentException("Operation with ID " + operation.getId() + " not found")))
                    .collect(Collectors.toList());

            modulePermission.setOperations(attachedOperations);

            // Save modulePermission and add to the list
            ModulePermission savedModulePermission = modulePermissionRepo.save(modulePermission);
            modulePermissions.add(savedModulePermission);
        }
        rolePermissionReq.setModulePermissions(modulePermissions);
        return rolePermissionRepository.save(rolePermissionReq);
    }

    public List<RolePermission> getAllRolePermissions() {
        List<RolePermission> rolePermissions = rolePermissionRepository.findAll();
        if (rolePermissions.isEmpty()) {

            System.out.println("No RolePermissions found");
        }
        return rolePermissions;
    }

    public RolePermissionDTO    getRolePermissionById(Long id) {
        Optional<RolePermission> rolePermissionOpt = rolePermissionRepository.findById(id);
        return rolePermissionOpt.map(rolePermissionMapper::toDTO).orElse(null);
    }

    public void deleteRolePermission(Long id) {
        if (rolePermissionRepository.existsById(id)) {
            rolePermissionRepository.deleteById(id);
        } else {

            System.out.println("RolePermission with ID " + id + " does not exist");
        }
    }
}
