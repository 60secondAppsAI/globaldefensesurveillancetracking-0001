package com.globaldefensesurveillancetracking.dao;

import java.util.List;

import com.globaldefensesurveillancetracking.dao.GenericDAO;
import com.globaldefensesurveillancetracking.domain.Movement;





public interface MovementDAO extends GenericDAO<Movement, Integer> {
  
	List<Movement> findAll();
	






}


