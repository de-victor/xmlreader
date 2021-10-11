package br.com.greenmile.xmlreader.enity;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class Coordenate {

	private String lat;
	
	private String lng;
	
	@JsonIgnore
	private String step;
	
	private Instant instant;
	
	private Long routeId;
	
	public Coordenate(String lat, String lng, String step) {
		this.lat = lat;
		this.lng = lng;
		this.step = step;
	}
}
