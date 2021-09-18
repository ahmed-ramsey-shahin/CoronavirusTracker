package com.ramsey.coronavirustracker.controller;

import com.ramsey.coronavirustracker.model.LocationStatus;
import com.ramsey.coronavirustracker.service.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
	
	private final CoronaVirusDataService coronaVirusDataService;
	
	@Autowired
	public HomeController(CoronaVirusDataService coronaVirusDataService) {
		
		this.coronaVirusDataService = coronaVirusDataService;
		
	}
	
	@ModelAttribute("locationStatuses")
	public List<LocationStatus> locationStatuses() {
		
		return coronaVirusDataService.getStatuses();
		
	}
	
	@ModelAttribute("totalReportedCases")
	public Long totalReportedCases() {
	
		return coronaVirusDataService.getStatuses()
				.stream()
				.mapToLong(LocationStatus::getLatestTotal)
				.sum();
	
	}

	@ModelAttribute("totalNewCases")
	public Long totalNewCases() {
		
		return coronaVirusDataService.getStatuses()
				.stream()
				.mapToLong(LocationStatus::getDifferenceFromThePreviousDay)
				.sum();
		
	}
	
	@GetMapping
	public String home() {
		
		return "home";
		
	}
	
}
