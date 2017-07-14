package com.recnation.api;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;

@RestController
public class MainController {
	
	private final double METERS_IN_A_MILE = 1609.34;
	private final double MINIMUM_DISTANCE = 0.0;
	
	MongoClient mongoClient = new MongoClient();
	MongoDatabase db = mongoClient.getDatabase("events-pickup");
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET, value = "/")
	public List<Event> closestEvents(@RequestParam Double latitude, @RequestParam Double longitude, @RequestParam String type, @RequestParam int maxDistance) {
		ArrayList<Event> events =  new ArrayList<>();		
		MongoCollection<Document> coll = db.getCollection(type); //TODO - Add 'all' feature which returns every event
		
		//Users coordinates -- must be longitude first
		Point userLocation = new Point(new Position(longitude, latitude));	
		coll.createIndex(Indexes.geo2dsphere("location"));
		
		
		FindIterable<Document> iterable = coll.find(Filters.near("location", userLocation,
				convertMilesToMeters(maxDistance), MINIMUM_DISTANCE));
						
		for (Document doc : iterable) {		
			List<Double> coordinates = (List<Double>)((Document) doc.get("location")).get("coordinates");			
			events.add(new Event(type, coordinates.get(0), coordinates.get(1) ));
		}
		
		return events;
	}
	
	
	private double convertMilesToMeters(int miles) {
		return miles * METERS_IN_A_MILE;		
	}
	
	
	
}
