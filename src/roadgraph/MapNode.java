/**
 * A class to represent a node in the map
 */
package roadgraph;

import java.util.HashSet;
import java.util.Set;

import geography.GeographicPoint;

/**
 * @author Bruno G. Tavares
 * 
 * Class representing a vertex (or node) in our MapGraph
 *
 */
class MapNode implements Comparable
{
	/** The list of edges out of this node */
	private HashSet<MapEdge> edges;
		
	/** the latitude and longitude of this node */
	private GeographicPoint location;
	
	/** the actual distance of this node */
	private Double actualDistance;
	
	/** the predicted distance of this node from start */
	private Double distance;
	
	/** 
	 * Create a new MapNode at a given Geographic location
	 * @param loc the location of this node
	 */
	MapNode(GeographicPoint loc)
	{
		location = loc;
		edges = new HashSet<MapEdge>();
		actualDistance = 0.0;
		distance = 0.0;
	}
		
	/**
	 * Add an edge that is outgoing from this node in the graph
	 * @param edge The edge to be added
	 */
	void addEdge(MapEdge edge)
	{
		edges.add(edge);
	}
	
	/**  
	 * Return the neighbors of this MapNode 
	 * @return a set containing all the neighbors of this node
	 */
	Set<MapNode> getNeighbors()
	{
		Set<MapNode> neighbors = new HashSet<MapNode>();
		for (MapEdge edge : edges) {
			neighbors.add(edge.getOtherNode(this));
		}
		return neighbors;
	}
	
	/**
	 * Get the geographic location that this node represents
	 * @return the geographic location of this node
	 */
	GeographicPoint getLocation()
	{
		return location;
	}
	
	/**
	 * return the edges out of this node
	 * @return a set containing all the edges out of this node.
	 */
	Set<MapEdge> getEdges()
	{
		return edges;
	}
	
	public Double getActualDistance() {
		return actualDistance;
	}
	
	public void setActualDistance(Double actualdDistance) {
		this.actualDistance = actualDistance;
	}
	
	public Double getDistance() {
		return this.distance;
	}
	
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	
	public Double getOtherNodeDistance(MapNode nextNode) {
		return location.distance(nextNode.getLocation());
	}
	
	public double getDistanceTo(MapNode to) {
		Set<MapNode> neighbors = getNeighbors();
        if (!neighbors.contains(to)) throw new IllegalArgumentException("Cannot find path");
        GeographicPoint toLocation = to.getLocation();
        for (MapEdge me : edges) {
            if (me.getEndNode().equals(toLocation)) {
                return me.getLength();
            }
        }
        return 0;
    }
	
	/** Returns whether two nodes are equal.
	 * Nodes are considered equal if their locations are the same, 
	 * even if their street list is different.
	 * @param o the node to compare to
	 * @return true if these nodes are at the same location, false otherwise
	 */
	@Override
	public boolean equals(Object o)
	{
		if (!(o instanceof MapNode) || (o == null)) {
			return false;
		}
		MapNode node = (MapNode)o;
		return node.location.equals(this.location);
	}
	
	/** Because we compare nodes using their location, we also 
	 * may use their location for HashCode.
	 * @return The HashCode for this node, which is the HashCode for the 
	 * underlying point
	 */
	@Override
	public int hashCode()
	{
		return location.hashCode();
	}
	
	/** This will allow us to sort the nodes in the Priority Queue 
	 * may use their location for HashCode.
	 * @return integer -1 descending order, 0 for equal, 1 for ascending
	 */
	@Override
	public int compareTo(Object o) {
		// convert to map node, may throw exception
		MapNode m = (MapNode)o; 
		return ((Double)this.getDistance()).compareTo((Double)m.getDistance());
	}
	/** ToString to print out a MapNode object
	 *  @return the string representation of a MapNode
	 */
	@Override
	public String toString()
	{
		String toReturn = "[NODE at location (" + location + ")";
		toReturn += " intersects streets: ";
		for (MapEdge e: edges) {
			toReturn += e.getRoadName() + ", ";
		}
		toReturn += "]";
		return toReturn;
	}

	// For debugging, output roadNames as a String.
	public String roadNamesAsString()
	{
		String toReturn = "(";
		for (MapEdge e: edges) {
			toReturn += e.getRoadName() + ", ";
		}
		toReturn += ")";
		return toReturn;
	}

}