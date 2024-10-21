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

import com.globaldefensesurveillancetracking.domain.Movement;
import com.globaldefensesurveillancetracking.dto.MovementDTO;
import com.globaldefensesurveillancetracking.dto.MovementSearchDTO;
import com.globaldefensesurveillancetracking.dto.MovementPageDTO;
import com.globaldefensesurveillancetracking.service.MovementService;
import com.globaldefensesurveillancetracking.dto.common.RequestDTO;
import com.globaldefensesurveillancetracking.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/movement")
@RestController
public class MovementController {

	private final static Logger logger = LoggerFactory.getLogger(MovementController.class);

	@Autowired
	MovementService movementService;



	//@AllowSystem(AuthScopes.READ)
	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Movement> getAll() {

		List<Movement> movements = movementService.findAll();
		
		return movements;	
	}

	//@ReadAccess
	@GetMapping(value = "/{movementId}")
	@ResponseBody
	public MovementDTO getMovement(@PathVariable Integer movementId) {
		
		return (movementService.getMovementDTOById(movementId));
	}

 	//@WriteAccess
 	@RequestMapping(value = "/addMovement", method = RequestMethod.POST)
	public ResponseEntity<?> addMovement(@RequestBody MovementDTO movementDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = movementService.addMovement(movementDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}
		
		return result.asResponseEntity();
	}

	//@ReadAccess
	@GetMapping("/movements")
	public ResponseEntity<MovementPageDTO> getMovements(MovementSearchDTO movementSearchDTO) {
 
		return movementService.getMovements(movementSearchDTO);
	}	

 	//@WriteAccess
	@RequestMapping(value = "/updateMovement", method = RequestMethod.POST)
	public ResponseEntity<?> updateMovement(@RequestBody MovementDTO movementDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = movementService.updateMovement(movementDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
