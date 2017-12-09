package roadgraph;

import java.util.ArrayList;
import java.util.List;

import geography.GeographicPoint;

/**
 * @author UCSD MOOC development team and Bruno G. Tavares
 * 
 * A class which represents a node (vertex) in a graph of geographic locations
 * Nodes in the graph are intersections between roads (edges).
 *
 *
 **/

public class MapNode implements Comparable<MapNode> {
	
	private GeographicPoint location;
	private List<MapEdge> edges;
	private Double predictedDistance;
	private Double distance;
	
	public MapNode(GeographicPoint location) {
		this.location = location;
		edges = new ArrayList<>();
		distance = 0.0;
		predictedDistance = 0.0;
	}
	
	public void addEdge(MapEdge mapEdge) {
		edges.add(mapEdge);
	}
	
	public List<MapEdge> getEdges() {
		return edges;
	}
	
	public List<GeographicPoint> getNeighbors() {
		List<GeographicPoint> neighbors = new ArrayList<>();
		
		for (MapEdge edge : edges) {
			neighbors.add(edge.getGoal());
		}
		
		return neighbors;
	}
	
	public GeographicPoint getLocation() {
		return location;
	}
	
	public Double getPredictedDistance() {
		return predictedDistance;
	}
	
	public void setPredictedDistance(Double predictedDistance) {
		this.predictedDistance = predictedDistance;
	}
	
	public Double getDistance() {
		return distance;
	}
	
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	
	public Double getNextNodeDistance(MapNode nextNode) {
		GeographicPoint nextLocation = nextNode.getLocation();
		return location.distance(nextLocation);
	}
	@Override
	public int compareTo(MapNode otherNode) {
		Double thisNodeDistance = this.getPredictedDistance() + this.getDistance();
		Double otherNodeDistance = otherNode.getPredictedDistance() + otherNode.getDistance();
		return thisNodeDistance.compareTo(otherNodeDistance);
	}
	
}
