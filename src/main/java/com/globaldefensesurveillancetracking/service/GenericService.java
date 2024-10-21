package com.globaldefensesurveillancetracking.service;

import com.globaldefensesurveillancetracking.dao.GenericDAO;

public interface GenericService<T, ID> {

    abstract GenericDAO<T, ID> getDAO();

    T getById(Integer id) ;

}