package com.globaldefensesurveillancetracking.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.ArrayList;


import com.globaldefensesurveillancetracking.util.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.Date;

import com.globaldefensesurveillancetracking.domain.Activity;
import com.globaldefensesurveillancetracking.dto.ActivityDTO;
import com.globaldefensesurveillancetracking.dto.ActivitySearchDTO;
import com.globaldefensesurveillancetracking.dto.ActivityPageDTO;
import com.globaldefensesurveillancetracking.service.ActivityService;
import com.globaldefensesurveillancetracking.dto.common.RequestDTO;
import com.globaldefensesurveillancetracking.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/activity")
@RestController
public class ActivityController {

	private final static Logger logger = LoggerFactory.getLogger(ActivityController.class);

	@Autowired
	ActivityService activityService;



	//@AllowSystem(AuthScopes.READ)
	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Activity> getAll() {

		List<Activity> activitys = activityService.findAll();
		
		return activitys;	
	}

	//@ReadAccess
	@GetMapping(value = "/{activityId}")
	@ResponseBody
	public ActivityDTO getActivity(@PathVariable Integer activityId) {
		
		return (activityService.getActivityDTOById(activityId));
	}

 	//@WriteAccess
 	@RequestMapping(value = "/addActivity", method = RequestMethod.POST)
	public ResponseEntity<?> addActivity(@RequestBody ActivityDTO activityDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = activityService.addActivity(activityDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}
		
		return result.asResponseEntity();
	}

	//@ReadAccess
	@GetMapping("/activitys")
	public ResponseEntity<ActivityPageDTO> getActivitys(ActivitySearchDTO activitySearchDTO) {
 
		return activityService.getActivitys(activitySearchDTO);
	}	

 	//@WriteAccess
	@RequestMapping(value = "/updateActivity", method = RequestMethod.POST)
	public ResponseEntity<?> updateActivity(@RequestBody ActivityDTO activityDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = activityService.updateActivity(activityDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
