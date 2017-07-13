package com.recnation.api;

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
	MongoClient mongoClient = new MongoClient();
	MongoDatabase db = mongoClient.getDatabase("events-pickup");
	MongoCollection<Document> coll = db.getCollection("basketball");
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String test(@RequestParam String latitude, @RequestParam String longitude) {
		if (latitude == null || longitude == null) {
			return "No user location passed, returns all events!";
		}
		
		Point refPoint = new Point(new Position(-87.9813723, 42.0846753));	//Users coordinates
		coll.createIndex(Indexes.geo2dsphere("location"));
		
		
		FindIterable<Document> iterable = coll.find(Filters.near("location", refPoint, 1000.0, 1.0));
		Document doc = iterable.first();
		
		
		return "Your location is " + latitude + " " + longitude + " found event: " +
				doc.toString();
	}
	
	
	
}
