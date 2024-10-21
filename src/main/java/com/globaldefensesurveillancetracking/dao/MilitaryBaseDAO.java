package com.globaldefensesurveillancetracking.dao;

import java.util.List;

import com.globaldefensesurveillancetracking.dao.GenericDAO;
import com.globaldefensesurveillancetracking.domain.MilitaryBase;





public interface MilitaryBaseDAO extends GenericDAO<MilitaryBase, Integer> {
  
	List<MilitaryBase> findAll();
	






}


