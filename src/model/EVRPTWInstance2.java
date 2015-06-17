package model;

import java.util.ArrayList;

public class EVRPTWInstance2 {
	
	private Node depot;
	private ArrayList<Node> stations;
	private ArrayList<Node> customers;
	
	private double tankCapacity;
	private double load;
	private double fuelConsumption;
	private double refueling;
	private double velocity;
	private double length;
	
	private ArrayList<Route> routes;
	
	
	public EVRPTWInstance2(EVRPTWInstance i){
		stations=i.getStations();
		customers=i.getCustomers();
		routes = i.getRoutes();
		tankCapacity = i.getTankCapacity();
		load = i.getLoad();
		fuelConsumption=i.getFuelConsumption();
		refueling=i.getRefueling();
		velocity=i.getVelocity();
		length=i.getLength();
		
	}
	
	public void setDepot(Node depot){
		this.depot=depot;
	}
	
	public Node getDepot(){
		return depot;
	}
	
	public void addStation(Node station){
		stations.add(station);
	}
	
	public ArrayList<Node> getStations(){
		return stations;
	}
	
	public void addCustomer(Node customer){
		customers.add(customer);
	}
	
	public ArrayList<Node> getCustomers(){
		return customers;
	}

	public double getTankCapacity() {
		return tankCapacity;
	}

	public void setTankCapacity(double tankCapacity) {
		this.tankCapacity = tankCapacity;
	}

	public double getLoad() {
		return load;
	}

	public void setLoad(double load) {
		this.load = load;
	}

	public double getFuelConsumption() {
		return fuelConsumption;
	}

	public void setFuelConsumption(double fuelConsumption) {
		this.fuelConsumption = fuelConsumption;
	}

	public double getRefueling() {
		return refueling;
	}

	public void setRefueling(double refueling) {
		this.refueling = refueling;
	}

	public double getVelocity() {
		return velocity;
	}

	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public ArrayList<Route> getRoutes() {
		return routes;
	}
	
	public void addRoute(Route route){
		routes.add(route);
	}

	public void setRoutes(ArrayList<Route> routes) {
		this.routes = routes;
	}
	
	public Node getNodeById(String id){
		Node n=null;
		if(depot.getId().equals(id)){
			n=depot;
		}
		else {
			for(int i=0; i<stations.size(); i++){
				if(stations.get(i).getId().equals(id)){
					n=stations.get(i);
				}
			}
				for(int i=0; i<customers.size(); i++){
					if(customers.get(i).getId().equals(id)){
						n=customers.get(i);
					}
				}
		}
		return n;
	}
	
	/*public boolean validate(){
		boolean validation = true;
		for(int i=0; i<routes.size(); i++){
			if(!routes.get(i).validate(this)){
				validation = false;
			}
		}
		return validation;
	}*/

}
