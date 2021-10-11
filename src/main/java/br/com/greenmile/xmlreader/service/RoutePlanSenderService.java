package br.com.greenmile.xmlreader.service;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.greenmile.xmlreader.enity.Coordenate;
import br.com.greenmile.xmlreader.exception.FailToSendCoordinateException;
import lombok.extern.java.Log;

@Log
@Service
public class RoutePlanSenderService {

	@Value("${routePlan.sleepAfterSending}")
	private Long timeToSleep;
	
	@Value("${api.host}")
	private String apiHost;
	
	@Value("${api.port}")
	private String apiPort;
	
	public void routePlanSender(List<Coordenate> route, Long routePlanId) {
		
		route.forEach(coord -> {
			coord.setInstant(Instant.now());
			coord.setRouteId(routePlanId);
			try {
				sendingData(coord);
			} catch (FailToSendCoordinateException e) {
				log.info(e.getMessage());
				
			}
			finally {
				sleepAfterSending();
			}
		});
	}
	
	private void sleepAfterSending() {
		try {
			Thread.sleep(timeToSleep);
		} catch (InterruptedException e) {
			log.info("Falhei em dormir");
			e.printStackTrace();
		}
	}
	
	private void sendingData(Coordenate coordenate) throws FailToSendCoordinateException {
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		ResponseEntity<String> response;
		
		HttpEntity<Coordenate> entity = new HttpEntity<Coordenate>(coordenate, headers);
		try {
			response = restTemplate.exchange(getApiUri(), HttpMethod.POST, entity, String.class);
		}catch (Exception e) {
			throw new FailToSendCoordinateException("Falha ao se comunicar com o backend" + e.getMessage());
		}
	
		
		if(response == null || !response.getStatusCode().equals(HttpStatus.OK)) {
			throw new FailToSendCoordinateException("Falha ao se comunicar com o backend");
		}
		
	}
	
	private String getApiUri() {
		if(this.apiHost == null) {
			this.apiHost = "localhost";
		}
		if(this.apiPort == null) {
			this.apiPort = "9090";
		}
		return String.format("http://%s:%s/ReceiveCoordinate", this.apiHost, this.apiPort);
	}
}
