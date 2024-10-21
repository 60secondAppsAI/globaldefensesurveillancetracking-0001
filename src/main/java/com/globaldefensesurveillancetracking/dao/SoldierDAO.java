package com.globaldefensesurveillancetracking.dao;

import java.util.List;

import com.globaldefensesurveillancetracking.dao.GenericDAO;
import com.globaldefensesurveillancetracking.domain.Soldier;





public interface SoldierDAO extends GenericDAO<Soldier, Integer> {
  
	List<Soldier> findAll();
	






}


