package com.globaldefensesurveillancetracking.dao;

import java.util.List;

import com.globaldefensesurveillancetracking.dao.GenericDAO;
import com.globaldefensesurveillancetracking.domain.Nation;





public interface NationDAO extends GenericDAO<Nation, Integer> {
  
	List<Nation> findAll();
	






}


