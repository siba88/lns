package algorithms;

import java.util.ArrayList;

import model.EVRPTWInstance;
import model.Route;

public class StationRemoval {
	
	EVRPTWInstance newInstance;
	ArrayList<Route> routes = new ArrayList<Route>();
	
	public StationRemoval(EVRPTWInstance instance){
		newInstance=instance;
		routes=instance.getRoutes();
		removeStations();
	}

	private void removeStations() {
		for(int i=0; i<routes.size(); i++){
			for(int j=0; j< routes.get(i).getRoute().size(); j++){
				if(routes.get(i).getRoute().get(j).getType().equals("f")){
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

}
