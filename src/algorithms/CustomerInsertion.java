package algorithms;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import model.EVRPTWInstance;
import model.Node;
import model.Route;

public class CustomerInsertion {
	
	ArrayList<Node> customers;
	EVRPTWInstance newInstance;
	ArrayList<Route> routes;
	
	public CustomerInsertion(ArrayList<Node> customer, EVRPTWInstance instance){
		customers= new ArrayList<Node>(new LinkedHashSet<Node>(customer));
		newInstance=instance;
		routes = instance.getRoutes();
		addCustomers();
	}

	private void addCustomers() {
		for(int i=0; i<customers.size(); i++){
			addCustomer(customers.get(i));
		}
		newInstance.setRoutes(routes);
	}

	private void addCustomer(Node node) {
		double increaseTime = -1;
		int routeNr = 0;
		int position = 0;
		for(int i=0; i<routes.size(); i++){
			for(int j=0; j<routes.get(i).getRoute().size()-1; j++){
				Node x = routes.get(i).getRoute().get(j);
				Node y = routes.get(i).getRoute().get(j+1);
					double newTime = calculateDistanceBetweenPoints(x, node) +
							calculateDistanceBetweenPoints(y, node) - calculateDistanceBetweenPoints(x,y);
					if(increaseTime==-1 || newTime < increaseTime){
						increaseTime = newTime;
						routeNr = i;
						position = j+1;
				}
			}
		}
		Route newRoute = routes.get(routeNr);
		newRoute.getRoute().add(position, node);
		routes.set(routeNr, newRoute);
	}
	
	public double calculateDistanceBetweenPoints(Node x, Node y){
		double deltax = Math.abs(x.getxCoordinate()
				- y.getxCoordinate());
		double deltay = Math.abs(x.getyCoordinate()
				- y.getyCoordinate());
		return Math.sqrt(deltax * deltax + deltay * deltay);
	}
	
	public EVRPTWInstance getNewInstance(){
		return newInstance;
	}

}
