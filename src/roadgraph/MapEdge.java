package roadgraph;

import java.util.ArrayList;
import java.util.List;

import geography.GeographicPoint;

/**
 * @author Bruno G. Tavares
 * 
 * A class which represents an edge in a graph of a Geographic Locaiton
 * The edge in this case, is a road. 
 *
 */

public class MapEdge {
	private GeographicPoint start;
	private GeographicPoint goal; 
	private String roadName;
	private String roadType;
	private Double length;
	
	public MapEdge(GeographicPoint start, GeographicPoint end,
			        String roadType, String roadName, double length) {
		this.start = start;
		this.goal = end;
		this.roadName = roadName;
		this.roadType = roadType;
		this.length = length;
	}
	
	public GeographicPoint getStart() {
		return start;
	}
	
	public GeographicPoint getGoal() {
		return goal;
	}
	
	public String getRoadName() {
		return roadName;
	}
	
	public String getRoadType() {
		return roadType;
	}
	
	public Double getLength() {
		return length;
	}
}
