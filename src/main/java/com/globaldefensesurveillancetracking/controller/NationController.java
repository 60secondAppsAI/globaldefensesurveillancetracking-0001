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

import com.globaldefensesurveillancetracking.domain.Nation;
import com.globaldefensesurveillancetracking.dto.NationDTO;
import com.globaldefensesurveillancetracking.dto.NationSearchDTO;
import com.globaldefensesurveillancetracking.dto.NationPageDTO;
import com.globaldefensesurveillancetracking.service.NationService;
import com.globaldefensesurveillancetracking.dto.common.RequestDTO;
import com.globaldefensesurveillancetracking.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/nation")
@RestController
public class NationController {

	private final static Logger logger = LoggerFactory.getLogger(NationController.class);

	@Autowired
	NationService nationService;



	//@AllowSystem(AuthScopes.READ)
	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Nation> getAll() {

		List<Nation> nations = nationService.findAll();
		
		return nations;	
	}

	//@ReadAccess
	@GetMapping(value = "/{nationId}")
	@ResponseBody
	public NationDTO getNation(@PathVariable Integer nationId) {
		
		return (nationService.getNationDTOById(nationId));
	}

 	//@WriteAccess
 	@RequestMapping(value = "/addNation", method = RequestMethod.POST)
	public ResponseEntity<?> addNation(@RequestBody NationDTO nationDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = nationService.addNation(nationDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}
		
		return result.asResponseEntity();
	}

	//@ReadAccess
	@GetMapping("/nations")
	public ResponseEntity<NationPageDTO> getNations(NationSearchDTO nationSearchDTO) {
 
		return nationService.getNations(nationSearchDTO);
	}	

 	//@WriteAccess
	@RequestMapping(value = "/updateNation", method = RequestMethod.POST)
	public ResponseEntity<?> updateNation(@RequestBody NationDTO nationDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = nationService.updateNation(nationDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
