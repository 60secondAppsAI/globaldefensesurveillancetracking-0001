package com.globaldefensesurveillancetracking.dao;

import java.util.List;

import com.globaldefensesurveillancetracking.dao.GenericDAO;
import com.globaldefensesurveillancetracking.domain.Equipment;





public interface EquipmentDAO extends GenericDAO<Equipment, Integer> {
  
	List<Equipment> findAll();
	






}


