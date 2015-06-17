package model;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class Route {
	
	ArrayList<Node> route;
	
	public Route(){
		route = new ArrayList<Node>();
	}
	
	public void addNode(Node node){
		route.add(node);
	}
	
	public ArrayList<Node> getRoute(){
		return route;
	}
	
	public void removeFromRoute(Node n){
		route.remove(n);
	}
	
	public double getLength(){
		double length = 0;
		for(int i=0; i<route.size()-1; i++){
			length += calculateDistanceBetweenPoints(route.get(i), route.get(i+1));
		}
		return length;
	}
	
	public String toString(){
		removeDuplicates();
		String returnString ="";
		for(int i=0; i<route.size()-1; i++){
			returnString+=route.get(i).getId();
			returnString+=", ";
		}
		returnString+=route.get(route.size()-1).getId();
		return returnString;
	}
	
	public double getTime(EVRPTWInstance instance){
		double currentTime=0;
		double currentEnergy = instance.getTankCapacity();
		for(int i=0; i<route.size()-1; i++){
			currentTime+=calculateDistanceBetweenPoints(route.get(i), route.get(i+1))*instance.getVelocity();
			if(currentTime<route.get(i).getReadyTime()){
				currentTime=route.get(i).getReadyTime();
			}
			currentEnergy -= calculateDistanceBetweenPoints(route.get(i), route.get(i+1))*instance.getFuelConsumption();
			if(route.get(i).getType().equals("c")){
				currentTime += route.get(i).getServiceTime();
			}
			else if(route.get(i).getType().equals("f")){
				currentTime+=(instance.getTankCapacity()-currentEnergy)*instance.getRefueling();
				currentEnergy=instance.getTankCapacity();
			}
		}
		return currentTime;
	}
	
	public void removeDuplicates(){
		for (int i = 1; i < route.size() - 1; i++) {
	        // start from the next item after strings[i]
	        // since the ones before are checked
	        for (int j = i + 1; j < route.size(); j++) {
	            // no need for if ( i == j ) here
	            if (!route.get(j).getId().equals(route.get(i).getId()))
	                continue;
	            route.remove(j);
	        } // for j
	    } // for i
	}
	
	public boolean validate(EVRPTWInstance instance){
		double currentTime=0;
		double currentEnergy = instance.getTankCapacity();
		boolean validation = true;
		for(int i=0; i<route.size()-2; i++){
			currentTime+=calculateDistanceBetweenPoints(route.get(i), route.get(i+1))*instance.getVelocity();
			//System.out.println(instance.getVelocity());
			if(currentTime<route.get(i).getReadyTime()){
				currentTime=route.get(i).getReadyTime();
			}
			if(route.get(i).getType().equals("f")){
				currentTime+=(instance.getTankCapacity()-currentEnergy)*instance.getRefueling();
				currentEnergy=instance.getTankCapacity();
			}
			//System.out.println("route: "+route.get(i).getId()+" "+route.get(i+1).getId()+" time:"+currentTime+" dueDate "+route.get(i+1).getDueDate());
			if(currentTime>route.get(i+1).getDueDate()){
				//validation = false;
			}
			//System.out.println("route: "+route.get(i).getId()+" "+route.get(i+1).getId()+"tank "+currentEnergy+" after "+(currentEnergy - calculateDistanceBetweenPoints(route.get(i), route.get(i+1))*instance.getFuelConsumption()));
			currentEnergy -= calculateDistanceBetweenPoints(route.get(i), route.get(i+1))*instance.getFuelConsumption();
			if(currentTime>route.get(i+1).getDueDate()){
				//validation = false;
			}
			if(route.get(i).getType().equals("c")){
				currentTime += route.get(i+1).getServiceTime();
			}
			if(currentEnergy<0){
				//System.out.println(currentEnergy);
				//System.out.println(calculateDistanceBetweenPoints(route.get(i), route.get(i+1)));
				validation = false;
				System.out.println("energy at point "+route.get(i).getId());
			}
		}
		return validation;
	}
	
	public double calculateDistanceBetweenPoints(Node x, Node y){
		double deltax = Math.abs(x.getxCoordinate()
				- y.getxCoordinate());
		double deltay = Math.abs(x.getyCoordinate()
				- y.getyCoordinate());
		return Math.sqrt(deltax * deltax + deltay * deltay);
	}

}
