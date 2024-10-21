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
import com.globaldefensesurveillancetracking.dao.MovementDAO;
import com.globaldefensesurveillancetracking.domain.Movement;
import com.globaldefensesurveillancetracking.dto.MovementDTO;
import com.globaldefensesurveillancetracking.dto.MovementSearchDTO;
import com.globaldefensesurveillancetracking.dto.MovementPageDTO;
import com.globaldefensesurveillancetracking.dto.MovementConvertCriteriaDTO;
import com.globaldefensesurveillancetracking.dto.common.RequestDTO;
import com.globaldefensesurveillancetracking.dto.common.ResultDTO;
import com.globaldefensesurveillancetracking.service.MovementService;
import com.globaldefensesurveillancetracking.util.ControllerUtils;





@Service
public class MovementServiceImpl extends GenericServiceImpl<Movement, Integer> implements MovementService {

    private final static Logger logger = LoggerFactory.getLogger(MovementServiceImpl.class);

	@Autowired
	MovementDAO movementDao;

	


	@Override
	public GenericDAO<Movement, Integer> getDAO() {
		return (GenericDAO<Movement, Integer>) movementDao;
	}
	
	public List<Movement> findAll () {
		List<Movement> movements = movementDao.findAll();
		
		return movements;	
		
	}

	public ResultDTO addMovement(MovementDTO movementDTO, RequestDTO requestDTO) {

		Movement movement = new Movement();

		movement.setMovementId(movementDTO.getMovementId());


		movement.setStartLocation(movementDTO.getStartLocation());


		movement.setEndLocation(movementDTO.getEndLocation());


		movement.setStartTime(movementDTO.getStartTime());


		movement.setEndTime(movementDTO.getEndTime());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		movement = movementDao.save(movement);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Movement> getAllMovements(Pageable pageable) {
		return movementDao.findAll(pageable);
	}

	public Page<Movement> getAllMovements(Specification<Movement> spec, Pageable pageable) {
		return movementDao.findAll(spec, pageable);
	}

	public ResponseEntity<MovementPageDTO> getMovements(MovementSearchDTO movementSearchDTO) {
	
			Integer movementId = movementSearchDTO.getMovementId(); 
 			String startLocation = movementSearchDTO.getStartLocation(); 
 			String endLocation = movementSearchDTO.getEndLocation(); 
     			String sortBy = movementSearchDTO.getSortBy();
			String sortOrder = movementSearchDTO.getSortOrder();
			String searchQuery = movementSearchDTO.getSearchQuery();
			Integer page = movementSearchDTO.getPage();
			Integer size = movementSearchDTO.getSize();

	        Specification<Movement> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, movementId, "movementId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, startLocation, "startLocation"); 
			
			spec = ControllerUtils.andIfNecessary(spec, endLocation, "endLocation"); 
			
 			
 			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("startLocation")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("endLocation")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Movement> movements = this.getAllMovements(spec, pageable);
		
		//System.out.println(String.valueOf(movements.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(movements.getTotalPages()));
		
		List<Movement> movementsList = movements.getContent();
		
		MovementConvertCriteriaDTO convertCriteria = new MovementConvertCriteriaDTO();
		List<MovementDTO> movementDTOs = this.convertMovementsToMovementDTOs(movementsList,convertCriteria);
		
		MovementPageDTO movementPageDTO = new MovementPageDTO();
		movementPageDTO.setMovements(movementDTOs);
		movementPageDTO.setTotalElements(movements.getTotalElements());
		return ResponseEntity.ok(movementPageDTO);
	}

	public List<MovementDTO> convertMovementsToMovementDTOs(List<Movement> movements, MovementConvertCriteriaDTO convertCriteria) {
		
		List<MovementDTO> movementDTOs = new ArrayList<MovementDTO>();
		
		for (Movement movement : movements) {
			movementDTOs.add(convertMovementToMovementDTO(movement,convertCriteria));
		}
		
		return movementDTOs;

	}
	
	public MovementDTO convertMovementToMovementDTO(Movement movement, MovementConvertCriteriaDTO convertCriteria) {
		
		MovementDTO movementDTO = new MovementDTO();
		
		movementDTO.setMovementId(movement.getMovementId());

	
		movementDTO.setStartLocation(movement.getStartLocation());

	
		movementDTO.setEndLocation(movement.getEndLocation());

	
		movementDTO.setStartTime(movement.getStartTime());

	
		movementDTO.setEndTime(movement.getEndTime());

	

		
		return movementDTO;
	}

	public ResultDTO updateMovement(MovementDTO movementDTO, RequestDTO requestDTO) {
		
		Movement movement = movementDao.getById(movementDTO.getMovementId());

		movement.setMovementId(ControllerUtils.setValue(movement.getMovementId(), movementDTO.getMovementId()));

		movement.setStartLocation(ControllerUtils.setValue(movement.getStartLocation(), movementDTO.getStartLocation()));

		movement.setEndLocation(ControllerUtils.setValue(movement.getEndLocation(), movementDTO.getEndLocation()));

		movement.setStartTime(ControllerUtils.setValue(movement.getStartTime(), movementDTO.getStartTime()));

		movement.setEndTime(ControllerUtils.setValue(movement.getEndTime(), movementDTO.getEndTime()));



        movement = movementDao.save(movement);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public MovementDTO getMovementDTOById(Integer movementId) {
	
		Movement movement = movementDao.getById(movementId);
			
		
		MovementConvertCriteriaDTO convertCriteria = new MovementConvertCriteriaDTO();
		return(this.convertMovementToMovementDTO(movement,convertCriteria));
	}







}
