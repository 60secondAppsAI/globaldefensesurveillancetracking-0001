package com.globaldefensesurveillancetracking.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.globaldefensesurveillancetracking.domain.Soldier;
import com.globaldefensesurveillancetracking.dto.SoldierDTO;
import com.globaldefensesurveillancetracking.dto.SoldierSearchDTO;
import com.globaldefensesurveillancetracking.dto.SoldierPageDTO;
import com.globaldefensesurveillancetracking.dto.SoldierConvertCriteriaDTO;
import com.globaldefensesurveillancetracking.service.GenericService;
import com.globaldefensesurveillancetracking.dto.common.RequestDTO;
import com.globaldefensesurveillancetracking.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface SoldierService extends GenericService<Soldier, Integer> {

	List<Soldier> findAll();

	ResultDTO addSoldier(SoldierDTO soldierDTO, RequestDTO requestDTO);

	ResultDTO updateSoldier(SoldierDTO soldierDTO, RequestDTO requestDTO);

    Page<Soldier> getAllSoldiers(Pageable pageable);

    Page<Soldier> getAllSoldiers(Specification<Soldier> spec, Pageable pageable);

	ResponseEntity<SoldierPageDTO> getSoldiers(SoldierSearchDTO soldierSearchDTO);
	
	List<SoldierDTO> convertSoldiersToSoldierDTOs(List<Soldier> soldiers, SoldierConvertCriteriaDTO convertCriteria);

	SoldierDTO getSoldierDTOById(Integer soldierId);







}





