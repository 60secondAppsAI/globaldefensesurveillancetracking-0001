package com.globaldefensesurveillancetracking.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class MovementDTO {

	private Integer movementId;

	private String startLocation;

	private String endLocation;

	private Date startTime;

	private Date endTime;






}
