package com.globaldefensesurveillancetracking.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.globaldefensesurveillancetracking.domain.MilitaryBase;
import com.globaldefensesurveillancetracking.dto.MilitaryBaseDTO;
import com.globaldefensesurveillancetracking.dto.MilitaryBaseSearchDTO;
import com.globaldefensesurveillancetracking.dto.MilitaryBasePageDTO;
import com.globaldefensesurveillancetracking.dto.MilitaryBaseConvertCriteriaDTO;
import com.globaldefensesurveillancetracking.service.GenericService;
import com.globaldefensesurveillancetracking.dto.common.RequestDTO;
import com.globaldefensesurveillancetracking.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface MilitaryBaseService extends GenericService<MilitaryBase, Integer> {

	List<MilitaryBase> findAll();

	ResultDTO addMilitaryBase(MilitaryBaseDTO militaryBaseDTO, RequestDTO requestDTO);

	ResultDTO updateMilitaryBase(MilitaryBaseDTO militaryBaseDTO, RequestDTO requestDTO);

    Page<MilitaryBase> getAllMilitaryBases(Pageable pageable);

    Page<MilitaryBase> getAllMilitaryBases(Specification<MilitaryBase> spec, Pageable pageable);

	ResponseEntity<MilitaryBasePageDTO> getMilitaryBases(MilitaryBaseSearchDTO militaryBaseSearchDTO);
	
	List<MilitaryBaseDTO> convertMilitaryBasesToMilitaryBaseDTOs(List<MilitaryBase> militaryBases, MilitaryBaseConvertCriteriaDTO convertCriteria);

	MilitaryBaseDTO getMilitaryBaseDTOById(Integer militaryBaseId);







}





