package com.ramsey.coronavirustracker.service;

import com.ramsey.coronavirustracker.model.LocationStatus;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronaVirusDataService {
	
	private static final String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
	private final List<LocationStatus> statuses;
	
	{
		
		statuses = new ArrayList<>();
		
	}
	
	@PostConstruct
	@Scheduled(fixedDelay = 600000)
	public void fetchVirusData() throws IOException, InterruptedException {

		statuses.clear();
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(VIRUS_DATA_URL))
				.build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		StringReader reader = new StringReader(response.body());
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
		
		for(CSVRecord record : records) {
			
			LocationStatus status = new LocationStatus(
					record.get(0),
					record.get(1),
					Integer.parseInt(record.get(record.size() - 1))
			);
			statuses.add(status);
			
		}
		
	}
	
}
