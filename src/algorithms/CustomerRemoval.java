package algorithms;

import java.util.ArrayList;
import java.util.Random;

import model.EVRPTWInstance;
import model.Node;
import model.Route;

public class CustomerRemoval {
	
	ArrayList<Route> routes;
	ArrayList<Node> customerRemoval = new ArrayList<Node>();
	EVRPTWInstance newInstance = new EVRPTWInstance();
	
	public CustomerRemoval(EVRPTWInstance instance){
		newInstance=instance;
		routes=instance.getRoutes();
		randomCustomers();
		removeCustomers();
	}
	
	private void randomCustomers(){
		ArrayList<Node> customers = newInstance.getCustomers();
		int counter = 0;
		while(counter<customers.size()*1.5/10){
			Random r = new Random();
			int rIndex = r.nextInt(customers.size()-1);
			if(!customerRemoval.contains(customers.get(rIndex))){
				customerRemoval.add(customers.get(rIndex));
				counter++;
			}
		}
	}
	
	private void removeCustomers(){
		for(int i=0; i<routes.size(); i++){
			for(int j=0; j< routes.get(i).getRoute().size(); j++){
				if(customerRemoval.contains(routes.get(i).getRoute().get(j))){
					//customerRemoval.remove(routes.get(i).getRoute().get(j));
					routes.get(i).getRoute().remove(j);
				}
			}
		}
		newInstance.setRoutes(routes);
	}
	
	public EVRPTWInstance getNewInstance(){
		return newInstance;
	}
	
	public ArrayList<Node> getRemovedCustomers(){
		return customerRemoval;
	}

}
