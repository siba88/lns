package algorithms;

import java.util.ArrayList;

import model.EVRPTWInstance;
import model.Node;
import model.Route;

public class StationInsertion {

	EVRPTWInstance instance;
	ArrayList<Route> routes;
	ArrayList<Node> stations;

	public StationInsertion(EVRPTWInstance instance) {
		this.instance = instance;
		routes = instance.getRoutes();
		stations = instance.getStations();
		insertStations();
	}

	private void insertStations() {
		for (int i = 0; i < routes.size(); i++) {
			insertStation(routes.get(i));
		}
	}

	private void insertStation(Route route) {
		double currentEnergy = 0;
		double tank = instance.getTankCapacity();
		boolean searchStation = false;
		for (int i = 0; i < route.getRoute().size() - 1; i++) {
			double newTank=-1;
			if (!searchStation) {
				newTank = tank
						- calculateDistanceBetweenPoints(route.getRoute()
								.get(i), route.getRoute().get(i + 1))
						* instance.getFuelConsumption();
			}
				if (newTank > 0) {
					tank = newTank;
			} else {
				if (findNearestStation(route.getRoute().get(i), tank) == null) {
					searchStation = true;
					//System.out.println("search station "
					//		+ route.getRoute().get(i).getId());
					tank += calculateDistanceBetweenPoints(route.getRoute()
							.get(i), route.getRoute().get(i - 1))
							* instance.getFuelConsumption();
					--i;
					//System.out.println("nextNode="+route.getRoute().get(i).getId());
					i--;
				} else {
					searchStation = false;
					//System.out.println("found station "
					//		+ route.getRoute().get(i).getId());
					route.getRoute().add(i + 1,
							findNearestStation(route.getRoute().get(i), tank));
					tank = instance.getTankCapacity();
				}
			}
		}
	}

	private Node findNearestStation(Node node, double tank) {
		Node nearestStation = null;
		double minimalLength = 0;
		for (int i = 0; i < stations.size(); i++) {
			double length = calculateDistanceBetweenPoints(node,
					stations.get(i));
			if (nearestStation == null || length < minimalLength) {
				nearestStation = stations.get(i);
				minimalLength = length;
			}
		}
		if (tank
				- (calculateDistanceBetweenPoints(node, nearestStation) * instance
						.getFuelConsumption()) > 0) {
			return nearestStation;
		}
		return null;
	}

	public double calculateDistanceBetweenPoints(Node x, Node y) {
		double deltax = Math.abs(x.getxCoordinate() - y.getxCoordinate());
		double deltay = Math.abs(x.getyCoordinate() - y.getyCoordinate());
		return Math.sqrt(deltax * deltax + deltay * deltay);
	}

	public EVRPTWInstance getNewInstance() {
		return instance;
	}

}
