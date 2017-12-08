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

public class MapNode {
	
	private GeographicPoint location;
	private List<MapEdge> edges; 
	
	public MapNode(GeographicPoint location) {
		this.location = location;
		edges = new ArrayList<>();
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
	
}
