import java.util.ArrayList;

import algorithms.CustomerInsertion;
import algorithms.CustomerRemoval;
import algorithms.StationInsertion;
import algorithms.StationRemoval;
import model.EVRPTWInstance;
import model.EVRPTWInstance2;
import model.Node;
import model.Route;


public class LNS {
	EVRPTWInstance2 bestInstance;
	
	public LNS(EVRPTWInstance instance){
		bestInstance = new EVRPTWInstance2(instance);
		outputBestInstance();
		//checkInstance = bestInstance;
		//System.out.println(instance.validate());
		for(int i=0; i<20000; i++){
			applyRemovalInsertion(new EVRPTWInstance(bestInstance));
		}
		//System.out.println(instance.getLength());
		outputBestInstance();
	}

	private void applyRemovalInsertion(EVRPTWInstance instance){
		EVRPTWInstance newInstance = instance;
		CustomerRemoval cr = new CustomerRemoval(instance);
		newInstance = cr.getNewInstance();
		//System.out.println(initialInstance!=bestInstance);
		//outputBestInstance();
		ArrayList<Node> customersRemoved = cr.getRemovedCustomers();
		StationRemoval sr = new StationRemoval(newInstance);
		newInstance = sr.getNewInstance();
		CustomerInsertion ci = new CustomerInsertion(customersRemoved, newInstance);
		newInstance = ci.getNewInstance();
		StationInsertion si = new StationInsertion(newInstance);
		newInstance = si.getNewInstance();
		//System.out.println(newInstance);
		ArrayList<Route> routes = newInstance.getRoutes();
		double routesLength=0;
		for(int i=0; i<routes.size(); i++){
			//System.out.println(routes.get(i));
			routesLength +=routes.get(i).getLength();
		}
		newInstance.setLength(routesLength);
		//System.out.println(initialInstance.getLength());
		//System.out.println(newInstance.getLength());
		//System.out.println(newInstance.validate());
		//System.out.println(newInstance.getLength());
		if(newInstance.validate() && newInstance.getLength()<bestInstance.getLength()){
			//System.out.println("in there");
			bestInstance = new EVRPTWInstance2(newInstance);
		}
	}
	
	private void outputBestInstance() {
		System.out.println(bestInstance.getLength());
		ArrayList<Route> routes = bestInstance.getRoutes();
		for(int i=0; i<routes.size(); i++){
			System.out.println(routes.get(i));
		}
		
	}
}
