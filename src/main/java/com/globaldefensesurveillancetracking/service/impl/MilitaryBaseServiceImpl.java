package com.globaldefensesurveillancetracking.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;



import com.globaldefensesurveillancetracking.dao.GenericDAO;
import com.globaldefensesurveillancetracking.service.GenericService;
import com.globaldefensesurveillancetracking.service.impl.GenericServiceImpl;
import com.globaldefensesurveillancetracking.dao.MilitaryBaseDAO;
import com.globaldefensesurveillancetracking.domain.MilitaryBase;
import com.globaldefensesurveillancetracking.dto.MilitaryBaseDTO;
import com.globaldefensesurveillancetracking.dto.MilitaryBaseSearchDTO;
import com.globaldefensesurveillancetracking.dto.MilitaryBasePageDTO;
import com.globaldefensesurveillancetracking.dto.MilitaryBaseConvertCriteriaDTO;
import com.globaldefensesurveillancetracking.dto.common.RequestDTO;
import com.globaldefensesurveillancetracking.dto.common.ResultDTO;
import com.globaldefensesurveillancetracking.service.MilitaryBaseService;
import com.globaldefensesurveillancetracking.util.ControllerUtils;





@Service
public class MilitaryBaseServiceImpl extends GenericServiceImpl<MilitaryBase, Integer> implements MilitaryBaseService {

    private final static Logger logger = LoggerFactory.getLogger(MilitaryBaseServiceImpl.class);

	@Autowired
	MilitaryBaseDAO militaryBaseDao;

	


	@Override
	public GenericDAO<MilitaryBase, Integer> getDAO() {
		return (GenericDAO<MilitaryBase, Integer>) militaryBaseDao;
	}
	
	public List<MilitaryBase> findAll () {
		List<MilitaryBase> militaryBases = militaryBaseDao.findAll();
		
		return militaryBases;	
		
	}

	public ResultDTO addMilitaryBase(MilitaryBaseDTO militaryBaseDTO, RequestDTO requestDTO) {

		MilitaryBase militaryBase = new MilitaryBase();

		militaryBase.setMilitaryBaseId(militaryBaseDTO.getMilitaryBaseId());


		militaryBase.setName(militaryBaseDTO.getName());


		militaryBase.setLocation(militaryBaseDTO.getLocation());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		militaryBase = militaryBaseDao.save(militaryBase);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<MilitaryBase> getAllMilitaryBases(Pageable pageable) {
		return militaryBaseDao.findAll(pageable);
	}

	public Page<MilitaryBase> getAllMilitaryBases(Specification<MilitaryBase> spec, Pageable pageable) {
		return militaryBaseDao.findAll(spec, pageable);
	}

	public ResponseEntity<MilitaryBasePageDTO> getMilitaryBases(MilitaryBaseSearchDTO militaryBaseSearchDTO) {
	
			Integer militaryBaseId = militaryBaseSearchDTO.getMilitaryBaseId(); 
 			String name = militaryBaseSearchDTO.getName(); 
 			String location = militaryBaseSearchDTO.getLocation(); 
 			String sortBy = militaryBaseSearchDTO.getSortBy();
			String sortOrder = militaryBaseSearchDTO.getSortOrder();
			String searchQuery = militaryBaseSearchDTO.getSearchQuery();
			Integer page = militaryBaseSearchDTO.getPage();
			Integer size = militaryBaseSearchDTO.getSize();

	        Specification<MilitaryBase> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, militaryBaseId, "militaryBaseId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, name, "name"); 
			
			spec = ControllerUtils.andIfNecessary(spec, location, "location"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("name")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("location")), "%" + searchQuery.toLowerCase() + "%") 
		));}
		
		Sort sort = Sort.unsorted();
		if (sortBy != null && !sortBy.isEmpty() && sortOrder != null && !sortOrder.isEmpty()) {
			if (sortOrder.equalsIgnoreCase("asc")) {
				sort = Sort.by(sortBy).ascending();
			} else if (sortOrder.equalsIgnoreCase("desc")) {
				sort = Sort.by(sortBy).descending();
			}
		}
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<MilitaryBase> militaryBases = this.getAllMilitaryBases(spec, pageable);
		
		//System.out.println(String.valueOf(militaryBases.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(militaryBases.getTotalPages()));
		
		List<MilitaryBase> militaryBasesList = militaryBases.getContent();
		
		MilitaryBaseConvertCriteriaDTO convertCriteria = new MilitaryBaseConvertCriteriaDTO();
		List<MilitaryBaseDTO> militaryBaseDTOs = this.convertMilitaryBasesToMilitaryBaseDTOs(militaryBasesList,convertCriteria);
		
		MilitaryBasePageDTO militaryBasePageDTO = new MilitaryBasePageDTO();
		militaryBasePageDTO.setMilitaryBases(militaryBaseDTOs);
		militaryBasePageDTO.setTotalElements(militaryBases.getTotalElements());
		return ResponseEntity.ok(militaryBasePageDTO);
	}

	public List<MilitaryBaseDTO> convertMilitaryBasesToMilitaryBaseDTOs(List<MilitaryBase> militaryBases, MilitaryBaseConvertCriteriaDTO convertCriteria) {
		
		List<MilitaryBaseDTO> militaryBaseDTOs = new ArrayList<MilitaryBaseDTO>();
		
		for (MilitaryBase militaryBase : militaryBases) {
			militaryBaseDTOs.add(convertMilitaryBaseToMilitaryBaseDTO(militaryBase,convertCriteria));
		}
		
		return militaryBaseDTOs;

	}
	
	public MilitaryBaseDTO convertMilitaryBaseToMilitaryBaseDTO(MilitaryBase militaryBase, MilitaryBaseConvertCriteriaDTO convertCriteria) {
		
		MilitaryBaseDTO militaryBaseDTO = new MilitaryBaseDTO();
		
		militaryBaseDTO.setMilitaryBaseId(militaryBase.getMilitaryBaseId());

	
		militaryBaseDTO.setName(militaryBase.getName());

	
		militaryBaseDTO.setLocation(militaryBase.getLocation());

	

		
		return militaryBaseDTO;
	}

	public ResultDTO updateMilitaryBase(MilitaryBaseDTO militaryBaseDTO, RequestDTO requestDTO) {
		
		MilitaryBase militaryBase = militaryBaseDao.getById(militaryBaseDTO.getMilitaryBaseId());

		militaryBase.setMilitaryBaseId(ControllerUtils.setValue(militaryBase.getMilitaryBaseId(), militaryBaseDTO.getMilitaryBaseId()));

		militaryBase.setName(ControllerUtils.setValue(militaryBase.getName(), militaryBaseDTO.getName()));

		militaryBase.setLocation(ControllerUtils.setValue(militaryBase.getLocation(), militaryBaseDTO.getLocation()));



        militaryBase = militaryBaseDao.save(militaryBase);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public MilitaryBaseDTO getMilitaryBaseDTOById(Integer militaryBaseId) {
	
		MilitaryBase militaryBase = militaryBaseDao.getById(militaryBaseId);
			
		
		MilitaryBaseConvertCriteriaDTO convertCriteria = new MilitaryBaseConvertCriteriaDTO();
		return(this.convertMilitaryBaseToMilitaryBaseDTO(militaryBase,convertCriteria));
	}







}
