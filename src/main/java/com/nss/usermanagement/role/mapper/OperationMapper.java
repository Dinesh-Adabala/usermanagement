package com.nss.usermanagement.role.mapper;

import com.nss.usermanagement.role.entity.Operation;
import com.nss.usermanagement.role.model.OperationDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OperationMapper {

    public OperationDTO toDTO(Operation operation) {
        if (operation == null) {
            return null;
        }
        OperationDTO dto = new OperationDTO();
        dto.setId(operation.getId());
        dto.setOperationName(operation.getName());
        // Optionally, you can set other fields here if needed
        return dto;
    }

    public Operation toEntity(OperationDTO dto) {
        if (dto == null) {
            return null;
        }
        Operation operation = new Operation();
        operation.setId(dto.getId());
        operation.setName(dto.getOperationName());

        return operation;
    }

    public List<OperationDTO> toDTOList(List<Operation> operations) {
        if (operations == null) {
            return null;
        }
        return operations.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Operation> toEntityList(List<OperationDTO> dtos) {
        if (dtos == null) {
            return null;
        }
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
