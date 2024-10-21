package com.globaldefensesurveillancetracking.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.globaldefensesurveillancetracking.domain.Movement;
import com.globaldefensesurveillancetracking.dto.MovementDTO;
import com.globaldefensesurveillancetracking.dto.MovementSearchDTO;
import com.globaldefensesurveillancetracking.dto.MovementPageDTO;
import com.globaldefensesurveillancetracking.dto.MovementConvertCriteriaDTO;
import com.globaldefensesurveillancetracking.service.GenericService;
import com.globaldefensesurveillancetracking.dto.common.RequestDTO;
import com.globaldefensesurveillancetracking.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface MovementService extends GenericService<Movement, Integer> {

	List<Movement> findAll();

	ResultDTO addMovement(MovementDTO movementDTO, RequestDTO requestDTO);

	ResultDTO updateMovement(MovementDTO movementDTO, RequestDTO requestDTO);

    Page<Movement> getAllMovements(Pageable pageable);

    Page<Movement> getAllMovements(Specification<Movement> spec, Pageable pageable);

	ResponseEntity<MovementPageDTO> getMovements(MovementSearchDTO movementSearchDTO);
	
	List<MovementDTO> convertMovementsToMovementDTOs(List<Movement> movements, MovementConvertCriteriaDTO convertCriteria);

	MovementDTO getMovementDTOById(Integer movementId);







}





