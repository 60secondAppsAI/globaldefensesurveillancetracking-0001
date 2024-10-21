package com.globaldefensesurveillancetracking.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.globaldefensesurveillancetracking.domain.Activity;
import com.globaldefensesurveillancetracking.dto.ActivityDTO;
import com.globaldefensesurveillancetracking.dto.ActivitySearchDTO;
import com.globaldefensesurveillancetracking.dto.ActivityPageDTO;
import com.globaldefensesurveillancetracking.dto.ActivityConvertCriteriaDTO;
import com.globaldefensesurveillancetracking.service.GenericService;
import com.globaldefensesurveillancetracking.dto.common.RequestDTO;
import com.globaldefensesurveillancetracking.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface ActivityService extends GenericService<Activity, Integer> {

	List<Activity> findAll();

	ResultDTO addActivity(ActivityDTO activityDTO, RequestDTO requestDTO);

	ResultDTO updateActivity(ActivityDTO activityDTO, RequestDTO requestDTO);

    Page<Activity> getAllActivitys(Pageable pageable);

    Page<Activity> getAllActivitys(Specification<Activity> spec, Pageable pageable);

	ResponseEntity<ActivityPageDTO> getActivitys(ActivitySearchDTO activitySearchDTO);
	
	List<ActivityDTO> convertActivitysToActivityDTOs(List<Activity> activitys, ActivityConvertCriteriaDTO convertCriteria);

	ActivityDTO getActivityDTOById(Integer activityId);







}





