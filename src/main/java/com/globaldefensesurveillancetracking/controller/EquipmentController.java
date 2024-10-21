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

import com.globaldefensesurveillancetracking.domain.Equipment;
import com.globaldefensesurveillancetracking.dto.EquipmentDTO;
import com.globaldefensesurveillancetracking.dto.EquipmentSearchDTO;
import com.globaldefensesurveillancetracking.dto.EquipmentPageDTO;
import com.globaldefensesurveillancetracking.service.EquipmentService;
import com.globaldefensesurveillancetracking.dto.common.RequestDTO;
import com.globaldefensesurveillancetracking.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/equipment")
@RestController
public class EquipmentController {

	private final static Logger logger = LoggerFactory.getLogger(EquipmentController.class);

	@Autowired
	EquipmentService equipmentService;



	//@AllowSystem(AuthScopes.READ)
	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Equipment> getAll() {

		List<Equipment> equipments = equipmentService.findAll();
		
		return equipments;	
	}

	//@ReadAccess
	@GetMapping(value = "/{equipmentId}")
	@ResponseBody
	public EquipmentDTO getEquipment(@PathVariable Integer equipmentId) {
		
		return (equipmentService.getEquipmentDTOById(equipmentId));
	}

 	//@WriteAccess
 	@RequestMapping(value = "/addEquipment", method = RequestMethod.POST)
	public ResponseEntity<?> addEquipment(@RequestBody EquipmentDTO equipmentDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = equipmentService.addEquipment(equipmentDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}
		
		return result.asResponseEntity();
	}

	//@ReadAccess
	@GetMapping("/equipments")
	public ResponseEntity<EquipmentPageDTO> getEquipments(EquipmentSearchDTO equipmentSearchDTO) {
 
		return equipmentService.getEquipments(equipmentSearchDTO);
	}	

 	//@WriteAccess
	@RequestMapping(value = "/updateEquipment", method = RequestMethod.POST)
	public ResponseEntity<?> updateEquipment(@RequestBody EquipmentDTO equipmentDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = equipmentService.updateEquipment(equipmentDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
