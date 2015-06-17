import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import model.EVRPTWInstance;
import model.Node;
import model.Route;


public class MainEVRPTW {

	public static void main(String[] args) {
		String fileName = "c105";
		EVRPTWInstance instance = new EVRPTWInstance();
		instantiateProblem(instance, fileName);
		getInitialSolution(instance, fileName);
	}
	
	public static void instantiateProblem(EVRPTWInstance instance, String fileName){
		try {
			BufferedReader br = new BufferedReader(new FileReader("evrptw_otl_instances/"+fileName+".txt"));
			String line = br.readLine();
			Node depot;
			
			line = br.readLine();
			
			 while (!line.isEmpty()) {
				 String id = line.substring(0, 10).trim();
				 String type = line.substring(11, 21).trim();
				 Double xCoordinate = Double.valueOf(line.substring(21, 31).trim());
				 Double yCoordinate = Double.valueOf(line.substring(31, 41).trim());
				 Double demand = Double.valueOf(line.substring(41, 51).trim());
				 Double readyTime = Double.valueOf(line.substring(51, 61).trim());
				 Double dueDate = Double.valueOf(line.substring(61, 71).trim());
				 Double serviceTime = Double.valueOf(line.substring(72, 81).trim());
				 Node node=new Node(id, type, xCoordinate, yCoordinate, demand, readyTime, dueDate, serviceTime);
				 line = br.readLine();
				 if(node.getType().equals("d")){
					 instance.setDepot(node);
				 }
				 if(node.getType().equals("f")){
					 if(!(node.getxCoordinate().equals(instance.getDepot().getxCoordinate()) && node.getyCoordinate().equals(instance.getDepot().getyCoordinate()))){
						 instance.addStation(node);
					 }
				 }
				 if(node.getType().equals("c")){
					 instance.addCustomer(node);
				 }
			 }
			 line = br.readLine();
			 double tankCapacity = Double.valueOf(line.substring(30, line.length()-1));
			 instance.setTankCapacity(tankCapacity);
			 line = br.readLine();
			 double load= Double.valueOf(line.substring(25, line.length()-1));
			 instance.setLoad(load);
			 line = br.readLine();
			 double fuelConsumption = Double.valueOf(line.substring(25, line.length()-1));
			 instance.setFuelConsumption(fuelConsumption);
			 line = br.readLine();
			 double refueling = Double.valueOf(line.substring(26, line.length()-1));
			 instance.setRefueling(refueling);
			 line = br.readLine();
			 double velocity = Double.valueOf(line.substring(20, line.length()-1));
			 instance.setVelocity(velocity);
			 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void getInitialSolution(EVRPTWInstance instance, String fileName){
		try {
			BufferedReader br = new BufferedReader(new FileReader("output2/"+fileName+".txt_construction_result.txt"));
			String line = br.readLine();
			line = br.readLine();
			double length = Double.valueOf(line);
			instance.setLength(length);
			line = br.readLine();
			while(line!=null && !line.isEmpty()){
				Route r =new Route();
				ArrayList<String> nodes = new ArrayList<String>(Arrays.asList(line.split(" ")));
				for(int i=0; i<nodes.size(); i++){
					String nodeId = nodes.get(i).replaceAll("\\s","");
					r.addNode(instance.getNodeById(nodeId));
					//System.out.println(instance.getNodeById(nodeId));
				}
				instance.addRoute(r);
				//System.out.println(r);
				line=br.readLine();
			}
			
			LNS lns = new LNS(instance);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
