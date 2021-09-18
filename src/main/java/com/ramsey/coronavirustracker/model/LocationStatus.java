package com.ramsey.coronavirustracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationStatus {

	private String state;
	private String country;
	private Integer latestTotal;
	private Integer differenceFromThePreviousDay;
	
}
