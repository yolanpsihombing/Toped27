package del.ac.id.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("store")
public class Store {

	@Id
	String id;
	public String store_name;
	public String location;
	public double points;
	public String reputation;
	public int latest_online;
	
	public Store() {}

	public Store(String store_name, String location, double points, String reputation, int latest_online) {
		super();
		this.store_name = store_name;
		this.location = location;
		this.points = points;
		this.reputation = reputation;
		this.latest_online = latest_online;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public double getPoints() {
		return points;
	}

	public void setPoints(double points) {
		this.points = points;
	}

	public String getReputation() {
		return reputation;
	}

	public void setReputation(String reputation) {
		this.reputation = reputation;
	}

	public int getLatest_online() {
		return latest_online;
	}

	public void setLatest_online(int latest_online) {
		this.latest_online = latest_online;
	}
	
	
}
