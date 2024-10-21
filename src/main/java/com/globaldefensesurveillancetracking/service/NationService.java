package com.globaldefensesurveillancetracking.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.globaldefensesurveillancetracking.domain.Nation;
import com.globaldefensesurveillancetracking.dto.NationDTO;
import com.globaldefensesurveillancetracking.dto.NationSearchDTO;
import com.globaldefensesurveillancetracking.dto.NationPageDTO;
import com.globaldefensesurveillancetracking.dto.NationConvertCriteriaDTO;
import com.globaldefensesurveillancetracking.service.GenericService;
import com.globaldefensesurveillancetracking.dto.common.RequestDTO;
import com.globaldefensesurveillancetracking.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface NationService extends GenericService<Nation, Integer> {

	List<Nation> findAll();

	ResultDTO addNation(NationDTO nationDTO, RequestDTO requestDTO);

	ResultDTO updateNation(NationDTO nationDTO, RequestDTO requestDTO);

    Page<Nation> getAllNations(Pageable pageable);

    Page<Nation> getAllNations(Specification<Nation> spec, Pageable pageable);

	ResponseEntity<NationPageDTO> getNations(NationSearchDTO nationSearchDTO);
	
	List<NationDTO> convertNationsToNationDTOs(List<Nation> nations, NationConvertCriteriaDTO convertCriteria);

	NationDTO getNationDTOById(Integer nationId);







}





