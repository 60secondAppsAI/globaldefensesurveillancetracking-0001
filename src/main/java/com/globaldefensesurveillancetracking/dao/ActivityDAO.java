package com.globaldefensesurveillancetracking.dao;

import java.util.List;

import com.globaldefensesurveillancetracking.dao.GenericDAO;
import com.globaldefensesurveillancetracking.domain.Activity;





public interface ActivityDAO extends GenericDAO<Activity, Integer> {
  
	List<Activity> findAll();
	






}


