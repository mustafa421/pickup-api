package com.recnation.api;

public class Event {
	
	private String type;
	private double latitude;
	private double longitude;
	private double distanceFromUser;
	// TODO - Add number of games, number of players, name of place, description, etc
	// TODO - Possible make this a super class, w/ individual sports extending this
	
	
	public Event() {
		//Dummy constructor
	}
	
	public Event(String type, double longitude, double latitude) {
		this.type = type;
		this.longitude = longitude;
		this.latitude = latitude;		
	}
	
	public String getType() {
		return type;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public double getDistanceFromUser() {
		return distanceFromUser;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public void setDistanceFromUser(double distanceFromUser) {
		this.distanceFromUser = distanceFromUser;
	}
	
	
	

}
