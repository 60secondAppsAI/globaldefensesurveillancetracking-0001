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
import com.globaldefensesurveillancetracking.dao.ActivityDAO;
import com.globaldefensesurveillancetracking.domain.Activity;
import com.globaldefensesurveillancetracking.dto.ActivityDTO;
import com.globaldefensesurveillancetracking.dto.ActivitySearchDTO;
import com.globaldefensesurveillancetracking.dto.ActivityPageDTO;
import com.globaldefensesurveillancetracking.dto.ActivityConvertCriteriaDTO;
import com.globaldefensesurveillancetracking.dto.common.RequestDTO;
import com.globaldefensesurveillancetracking.dto.common.ResultDTO;
import com.globaldefensesurveillancetracking.service.ActivityService;
import com.globaldefensesurveillancetracking.util.ControllerUtils;





@Service
public class ActivityServiceImpl extends GenericServiceImpl<Activity, Integer> implements ActivityService {

    private final static Logger logger = LoggerFactory.getLogger(ActivityServiceImpl.class);

	@Autowired
	ActivityDAO activityDao;

	


	@Override
	public GenericDAO<Activity, Integer> getDAO() {
		return (GenericDAO<Activity, Integer>) activityDao;
	}
	
	public List<Activity> findAll () {
		List<Activity> activitys = activityDao.findAll();
		
		return activitys;	
		
	}

	public ResultDTO addActivity(ActivityDTO activityDTO, RequestDTO requestDTO) {

		Activity activity = new Activity();

		activity.setActivityId(activityDTO.getActivityId());


		activity.setDescription(activityDTO.getDescription());


		activity.setTimestamp(activityDTO.getTimestamp());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		activity = activityDao.save(activity);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Activity> getAllActivitys(Pageable pageable) {
		return activityDao.findAll(pageable);
	}

	public Page<Activity> getAllActivitys(Specification<Activity> spec, Pageable pageable) {
		return activityDao.findAll(spec, pageable);
	}

	public ResponseEntity<ActivityPageDTO> getActivitys(ActivitySearchDTO activitySearchDTO) {
	
			Integer activityId = activitySearchDTO.getActivityId(); 
 			String description = activitySearchDTO.getDescription(); 
   			String sortBy = activitySearchDTO.getSortBy();
			String sortOrder = activitySearchDTO.getSortOrder();
			String searchQuery = activitySearchDTO.getSearchQuery();
			Integer page = activitySearchDTO.getPage();
			Integer size = activitySearchDTO.getSize();

	        Specification<Activity> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, activityId, "activityId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, description, "description"); 
			
 			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("description")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Activity> activitys = this.getAllActivitys(spec, pageable);
		
		//System.out.println(String.valueOf(activitys.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(activitys.getTotalPages()));
		
		List<Activity> activitysList = activitys.getContent();
		
		ActivityConvertCriteriaDTO convertCriteria = new ActivityConvertCriteriaDTO();
		List<ActivityDTO> activityDTOs = this.convertActivitysToActivityDTOs(activitysList,convertCriteria);
		
		ActivityPageDTO activityPageDTO = new ActivityPageDTO();
		activityPageDTO.setActivitys(activityDTOs);
		activityPageDTO.setTotalElements(activitys.getTotalElements());
		return ResponseEntity.ok(activityPageDTO);
	}

	public List<ActivityDTO> convertActivitysToActivityDTOs(List<Activity> activitys, ActivityConvertCriteriaDTO convertCriteria) {
		
		List<ActivityDTO> activityDTOs = new ArrayList<ActivityDTO>();
		
		for (Activity activity : activitys) {
			activityDTOs.add(convertActivityToActivityDTO(activity,convertCriteria));
		}
		
		return activityDTOs;

	}
	
	public ActivityDTO convertActivityToActivityDTO(Activity activity, ActivityConvertCriteriaDTO convertCriteria) {
		
		ActivityDTO activityDTO = new ActivityDTO();
		
		activityDTO.setActivityId(activity.getActivityId());

	
		activityDTO.setDescription(activity.getDescription());

	
		activityDTO.setTimestamp(activity.getTimestamp());

	

		
		return activityDTO;
	}

	public ResultDTO updateActivity(ActivityDTO activityDTO, RequestDTO requestDTO) {
		
		Activity activity = activityDao.getById(activityDTO.getActivityId());

		activity.setActivityId(ControllerUtils.setValue(activity.getActivityId(), activityDTO.getActivityId()));

		activity.setDescription(ControllerUtils.setValue(activity.getDescription(), activityDTO.getDescription()));

		activity.setTimestamp(ControllerUtils.setValue(activity.getTimestamp(), activityDTO.getTimestamp()));



        activity = activityDao.save(activity);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public ActivityDTO getActivityDTOById(Integer activityId) {
	
		Activity activity = activityDao.getById(activityId);
			
		
		ActivityConvertCriteriaDTO convertCriteria = new ActivityConvertCriteriaDTO();
		return(this.convertActivityToActivityDTO(activity,convertCriteria));
	}







}
