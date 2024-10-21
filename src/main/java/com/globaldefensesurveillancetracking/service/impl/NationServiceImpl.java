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
import com.globaldefensesurveillancetracking.dao.NationDAO;
import com.globaldefensesurveillancetracking.domain.Nation;
import com.globaldefensesurveillancetracking.dto.NationDTO;
import com.globaldefensesurveillancetracking.dto.NationSearchDTO;
import com.globaldefensesurveillancetracking.dto.NationPageDTO;
import com.globaldefensesurveillancetracking.dto.NationConvertCriteriaDTO;
import com.globaldefensesurveillancetracking.dto.common.RequestDTO;
import com.globaldefensesurveillancetracking.dto.common.ResultDTO;
import com.globaldefensesurveillancetracking.service.NationService;
import com.globaldefensesurveillancetracking.util.ControllerUtils;





@Service
public class NationServiceImpl extends GenericServiceImpl<Nation, Integer> implements NationService {

    private final static Logger logger = LoggerFactory.getLogger(NationServiceImpl.class);

	@Autowired
	NationDAO nationDao;

	


	@Override
	public GenericDAO<Nation, Integer> getDAO() {
		return (GenericDAO<Nation, Integer>) nationDao;
	}
	
	public List<Nation> findAll () {
		List<Nation> nations = nationDao.findAll();
		
		return nations;	
		
	}

	public ResultDTO addNation(NationDTO nationDTO, RequestDTO requestDTO) {

		Nation nation = new Nation();

		nation.setNationId(nationDTO.getNationId());


		nation.setName(nationDTO.getName());


		nation.setContinent(nationDTO.getContinent());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		nation = nationDao.save(nation);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Nation> getAllNations(Pageable pageable) {
		return nationDao.findAll(pageable);
	}

	public Page<Nation> getAllNations(Specification<Nation> spec, Pageable pageable) {
		return nationDao.findAll(spec, pageable);
	}

	public ResponseEntity<NationPageDTO> getNations(NationSearchDTO nationSearchDTO) {
	
			Integer nationId = nationSearchDTO.getNationId(); 
 			String name = nationSearchDTO.getName(); 
 			String continent = nationSearchDTO.getContinent(); 
 			String sortBy = nationSearchDTO.getSortBy();
			String sortOrder = nationSearchDTO.getSortOrder();
			String searchQuery = nationSearchDTO.getSearchQuery();
			Integer page = nationSearchDTO.getPage();
			Integer size = nationSearchDTO.getSize();

	        Specification<Nation> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, nationId, "nationId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, name, "name"); 
			
			spec = ControllerUtils.andIfNecessary(spec, continent, "continent"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("name")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("continent")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Nation> nations = this.getAllNations(spec, pageable);
		
		//System.out.println(String.valueOf(nations.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(nations.getTotalPages()));
		
		List<Nation> nationsList = nations.getContent();
		
		NationConvertCriteriaDTO convertCriteria = new NationConvertCriteriaDTO();
		List<NationDTO> nationDTOs = this.convertNationsToNationDTOs(nationsList,convertCriteria);
		
		NationPageDTO nationPageDTO = new NationPageDTO();
		nationPageDTO.setNations(nationDTOs);
		nationPageDTO.setTotalElements(nations.getTotalElements());
		return ResponseEntity.ok(nationPageDTO);
	}

	public List<NationDTO> convertNationsToNationDTOs(List<Nation> nations, NationConvertCriteriaDTO convertCriteria) {
		
		List<NationDTO> nationDTOs = new ArrayList<NationDTO>();
		
		for (Nation nation : nations) {
			nationDTOs.add(convertNationToNationDTO(nation,convertCriteria));
		}
		
		return nationDTOs;

	}
	
	public NationDTO convertNationToNationDTO(Nation nation, NationConvertCriteriaDTO convertCriteria) {
		
		NationDTO nationDTO = new NationDTO();
		
		nationDTO.setNationId(nation.getNationId());

	
		nationDTO.setName(nation.getName());

	
		nationDTO.setContinent(nation.getContinent());

	

		
		return nationDTO;
	}

	public ResultDTO updateNation(NationDTO nationDTO, RequestDTO requestDTO) {
		
		Nation nation = nationDao.getById(nationDTO.getNationId());

		nation.setNationId(ControllerUtils.setValue(nation.getNationId(), nationDTO.getNationId()));

		nation.setName(ControllerUtils.setValue(nation.getName(), nationDTO.getName()));

		nation.setContinent(ControllerUtils.setValue(nation.getContinent(), nationDTO.getContinent()));



        nation = nationDao.save(nation);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public NationDTO getNationDTOById(Integer nationId) {
	
		Nation nation = nationDao.getById(nationId);
			
		
		NationConvertCriteriaDTO convertCriteria = new NationConvertCriteriaDTO();
		return(this.convertNationToNationDTO(nation,convertCriteria));
	}







}
