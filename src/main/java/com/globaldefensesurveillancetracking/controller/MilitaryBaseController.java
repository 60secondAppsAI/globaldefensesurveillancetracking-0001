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

import com.globaldefensesurveillancetracking.domain.MilitaryBase;
import com.globaldefensesurveillancetracking.dto.MilitaryBaseDTO;
import com.globaldefensesurveillancetracking.dto.MilitaryBaseSearchDTO;
import com.globaldefensesurveillancetracking.dto.MilitaryBasePageDTO;
import com.globaldefensesurveillancetracking.service.MilitaryBaseService;
import com.globaldefensesurveillancetracking.dto.common.RequestDTO;
import com.globaldefensesurveillancetracking.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/militaryBase")
@RestController
public class MilitaryBaseController {

	private final static Logger logger = LoggerFactory.getLogger(MilitaryBaseController.class);

	@Autowired
	MilitaryBaseService militaryBaseService;



	//@AllowSystem(AuthScopes.READ)
	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<MilitaryBase> getAll() {

		List<MilitaryBase> militaryBases = militaryBaseService.findAll();
		
		return militaryBases;	
	}

	//@ReadAccess
	@GetMapping(value = "/{militaryBaseId}")
	@ResponseBody
	public MilitaryBaseDTO getMilitaryBase(@PathVariable Integer militaryBaseId) {
		
		return (militaryBaseService.getMilitaryBaseDTOById(militaryBaseId));
	}

 	//@WriteAccess
 	@RequestMapping(value = "/addMilitaryBase", method = RequestMethod.POST)
	public ResponseEntity<?> addMilitaryBase(@RequestBody MilitaryBaseDTO militaryBaseDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = militaryBaseService.addMilitaryBase(militaryBaseDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}
		
		return result.asResponseEntity();
	}

	//@ReadAccess
	@GetMapping("/militaryBases")
	public ResponseEntity<MilitaryBasePageDTO> getMilitaryBases(MilitaryBaseSearchDTO militaryBaseSearchDTO) {
 
		return militaryBaseService.getMilitaryBases(militaryBaseSearchDTO);
	}	

 	//@WriteAccess
	@RequestMapping(value = "/updateMilitaryBase", method = RequestMethod.POST)
	public ResponseEntity<?> updateMilitaryBase(@RequestBody MilitaryBaseDTO militaryBaseDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = militaryBaseService.updateMilitaryBase(militaryBaseDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
