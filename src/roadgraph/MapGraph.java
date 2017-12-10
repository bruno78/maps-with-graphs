package roadgraph;


import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;

import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and Bruno G. Tavares
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {
	// Maintain both nodes and edges as you will need to
	// be able to look up nodes by lat/lon or by roads
	// that contain those nodes.
	// DONE: Add your member variables here in WEEK 3
	private HashMap<GeographicPoint,MapNode> vertices;
	private HashSet<MapEdge> edges;

	
	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph()
	{
		// DONE: Implement in this constructor in WEEK 3
		vertices = new HashMap<GeographicPoint,MapNode>();
		edges = new HashSet<MapEdge>();
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		// DONE: Implement this method in WEEK 3
		return vertices.values().size();
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		// DONE: Implement this method in WEEK 3
		return vertices.keySet();
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		// DONE: Implement this method in WEEK 3
		return edges.size();
	}

	
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{
		// DONE: Implement this method in WEEK 3
		if (location == null) {
			return false;
		}
		MapNode n = vertices.get(location);
		if (n == null) {
			n = new MapNode(location);
			vertices.put(location, n);
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {

		// DONE: Implement this method in WEEK 3
		MapNode n1 = vertices.get(from);
		MapNode n2 = vertices.get(to);

		// check nodes are valid
		if (n1 == null)
			throw new NullPointerException("addEdge: pt1:"+from+"is not in graph");
		if (n2 == null)
			throw new NullPointerException("addEdge: pt2:"+to+"is not in graph");

		MapEdge edge = new MapEdge(roadName, roadType, n1, n2, length);
		edges.add(edge);
		n1.addEdge(edge);
		
	}
		
	/** 
	 * Get a set of neighbor nodes from a mapNode
	 * @param node  The node to get the neighbors from
	 * @return A set containing the MapNode objects that are the neighbors 
	 * 	of node
	 */
	private Set<MapNode> getNeighbors(MapNode node) {
		return node.getNeighbors();
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, 
			 					     Consumer<GeographicPoint> nodeSearched)
	{
		
		/* Note that this method is a little long and we might think
		 * about refactoring it to break it into shorter methods as we 
		 * did in the Maze search code in week 2 */
		// DONE: Implement this method in WEEK 3
		
		// Setup - check validity of inputs
		if (start == null || goal == null)
			throw new NullPointerException("Cannot find route from or to null node");
		
		MapNode startNode = vertices.get(start);
		MapNode endNode = vertices.get(goal);
		
		if (startNode == null) {
			System.err.println("Start node " + start + " does not exist");
			return null;
		}
		if (endNode == null) {
			System.err.println("End node " + goal + " does not exist");
			return null;
		}

		// setup to begin BFS
		HashMap<MapNode,MapNode> parentMap = new HashMap<MapNode,MapNode>();
		Queue<MapNode> toExplore = new LinkedList<MapNode>();
		HashSet<MapNode> visited = new HashSet<MapNode>();
		toExplore.add(startNode);
		MapNode next = null;

		while (!toExplore.isEmpty()) {
			next = toExplore.remove();
			
			 // hook for visualization
			nodeSearched.accept(next.getLocation());
			
			if (next.equals(endNode)) break;
			Set<MapNode> neighbors = getNeighbors(next);
			for (MapNode neighbor : neighbors) {
				if (!visited.contains(neighbor)) {
					visited.add(neighbor);
					parentMap.put(neighbor, next);
					toExplore.add(neighbor);
				}
			}
		}
		if (!next.equals(endNode)) {
			System.out.println("No path found from " +start+ " to " + goal);
			return null;
		}
		// Reconstruct the parent path
		return reconstructPath(startNode, endNode, parentMap);
	
	}
	


	/** Reconstruct a path from start to goal using the parentMap
	 *
	 * @param parentMap the HashNode map of children and their parents
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from
	 *   start to goal (including both start and goal).
	 */
	private List<GeographicPoint>
	reconstructPath(MapNode start, MapNode goal, HashMap<MapNode,MapNode> parentMap)
	{
		LinkedList<GeographicPoint> path = new LinkedList<GeographicPoint>();
		MapNode current = goal;

		while (!current.equals(start)) {
			path.addFirst(current.getLocation());
			current = parentMap.get(current);
		}

		// add start
		path.addFirst(start.getLocation());
		return path;
	}


	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 4
		if (start == null || goal == null) 
			throw new NullPointerException("start or goal is null!");
		
		// initialize 
		PriorityQueue<MapNode> toExplore = new PriorityQueue<MapNode>();
		HashSet<MapNode> visited = new HashSet<MapNode>();
		HashMap<MapNode, MapNode> parent = new HashMap<MapNode, MapNode>();
		
		MapNode startNode = vertices.get(start);
		MapNode goalNode = vertices.get(goal);
		
		// Setting all nodes to infinity
		for (MapNode node: vertices.values()) {
			node.setDistance(Double.MAX_VALUE);
		}
		
		int count = 0;
		
		startNode.setDistance(0.0);
		toExplore.add(startNode);
		
		while(!toExplore.isEmpty()) {
			MapNode curr = toExplore.poll();
			if(!visited.contains(curr)) {
				visited.add(curr);
				nodeSearched.accept(curr.getLocation());
				count ++;			
				if(curr.equals(goalNode)) {
					System.out.println("Dijkstra num of visits: " + count);
                    return reconstructPath(startNode, goalNode, parent);
                }
				
				for (MapNode node : curr.getNeighbors()) {
					if(!visited.contains(node)) {
						double pathToNode = curr.getDistance() + curr.getOtherNodeDistance(node);
						if (pathToNode < node.getDistance()) {
							node.setDistance(pathToNode);
							parent.put(node, curr);
							toExplore.add(node);
						}
					}
				}
			}			
		}
		
		System.out.println("No path found from " +start+ " to " + goal);
		return null;
	}
		
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 4
		if (start == null || goal == null) 
			throw new NullPointerException("start or goal is null!");
		
		PriorityQueue<MapNode> toExplore = new PriorityQueue<MapNode>();
		HashSet<MapNode> visited = new HashSet<MapNode>();
		HashMap<MapNode, MapNode> parentMap = new HashMap<MapNode, MapNode>();
		
		MapNode startNode = vertices.get(start);
		MapNode goalNode = vertices.get(goal);
		
		startNode.setDistance(0.0);
		int count = 0;
		
		// Setting all the nodes to infinitity
		for (MapNode node : vertices.values()) {
			node.setDistance(Double.MAX_VALUE);
		}
		
		toExplore.add(startNode);
		
		while(!toExplore.isEmpty()) {
			MapNode next = toExplore.poll();
			
			if (!visited.contains(next)) {
				visited.add(next);
				nodeSearched.accept(next.getLocation());
				count ++;
				
				if(next.equals(goalNode)) {
                    System.out.println("aStarSearch num of visits: " + count);
                    return reconstructPath(startNode, goalNode, parentMap);
                }
				
				for (MapNode node : next.getNeighbors()) {
					if(!visited.contains(node)) {
						GeographicPoint nodeLocation = node.getLocation();
						double startToNode = next.getActualDistance() + next.getDistanceTo(node);
						double predictedDistance = startToNode + nodeLocation.distance(goal);
						if(predictedDistance < node.getDistance()) {
							node.setDistance(predictedDistance);
							node.setActualDistance(startToNode);
							parentMap.put(node, next);
							toExplore.add(node);
						}
					}
				}
			}	
		}
		
		System.out.println("No path found from " +start+ " to " + goal);
		return null;
	}

	
	// You can use this method for testing.
	public static void main(String[] args)
	{
//		/*
//		 * Here are some test cases you should try before you attempt the Week 3
//		 * End of Week Quiz, EVEN IF you score 100% on the programming
//		 * assignment.
//		 */
//		 System.out.print("Making a new map...");
//		 MapGraph simpleTestMap = new MapGraph();
//		 System.out.print("DONE. \nLoading the map...");
//		 GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);
//		 System.out.println("DONE.");
//
//		 GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
//		 GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);
//		  
//		 System.out.println(
//				 "Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5"); 
//		 List<GeographicPoint> testroute = simpleTestMap.dijkstra(testStart,testEnd); 
//		 List<GeographicPoint> testroute2 = simpleTestMap.aStarSearch(testStart,testEnd);
//		  
//		  
//		 MapGraph testMap = new MapGraph();
//		 GraphLoader.loadRoadMap("data/maps/utc.map", testMap);
//		  
//		 // A very simple test using real data 
//		 testStart = new GeographicPoint(32.869423, -117.220917); 
//		 testEnd = new GeographicPoint(32.869255, -117.216927); 
//		 System.out.println(
//				 "Test 2 using utc: Dijkstra should be 13 and AStar should be 5");
//		 testroute = testMap.dijkstra(testStart,testEnd); 
//		 testroute2 = testMap.aStarSearch(testStart,testEnd);
//		  
//		  
//		 // A slightly more complex test using real data 
//		 testStart = new GeographicPoint(32.8674388, -117.2190213); 
//		 testEnd = new GeographicPoint(32.8697828, -117.2244506); 
//		 System.out.println(
//		 "Test 3 using utc: Dijkstra should be 37 and AStar should be 10");
//		 testroute = testMap.dijkstra(testStart,testEnd); 
//		 testroute2 = testMap.aStarSearch(testStart,testEnd);
//		 
//		 
//	
		/* Use this code in Week 3 End of Week Quiz */
		
		 MapGraph theMap = new MapGraph(); 
		 System.out.print("DONE. \nLoading the map...");
		 GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		 System.out.println("DONE.");
		  
		 GeographicPoint start = new GeographicPoint(32.8648772,-117.2254046); 
		 GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		 
		 List<GeographicPoint> route = theMap.dijkstra(start,end);
		 List<GeographicPoint> route2 = theMap.aStarSearch(start,end);
		 // It should be Dijkstra 82, AStartSearch: 19;
	}

}