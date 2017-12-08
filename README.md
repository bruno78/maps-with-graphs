## Graphs

_...in progress._

This project explore graphs using Google Maps API

### Week 2

**Method Implementations**

1. **Degree Sequence** which is an ordered list containing the degrees of each of the vertices in a graph, in non-increasing sorted order (with repetitions). The degree sequence of a graph is an invariant of the graph and can be used to distinguish between two graphs and to analyze graph properties.

2. **getDistance2** (get Distance 2) finds all vertices reachable by two hops from vertex.
  - **GraphAdjList** getDistance2 - get vertex's neighbors and loop through them adding to the new array.
  - **GraphAdjMatrix** getDistance2 - in this case can be obtained by squaring the matrix representation of the graph. Then creating a nested loop where you loop through the column of the inner squared matrix adding all values that are greater than 0 to the new array.

### Week 3

Class design and implementation.


**Class: MapGraph**

  A class which represents a graph of geographic locations.
  Nodes in the graph are intersections between roads (Edges) in the map.
  This class contains methods that return the number of vertices (nodes), and edges.
  Also allows to add a vertex and edge.

  It performs searches in different ways:
    - Breadth First Search (bfs)
    - Dijkstra Search (to be implemented in week 4)
    - A Star Search (to be implemented in week 4)

  Modifications: BFS method was too long, and it was broke into two small private methods:
    - doBFSSearch
    - constructPath

**Class: MapNode**

  A class which represents a node (vertex) in a graph of geographic locations.
  Nodes in the graph are intersections between roads (edges).
  This class contains methods that allows to add and edge, and return a list of edges, and a list of nodes around a specific edge.

**Class: MapEdge**

   A class which represents an edge in a graph of a Geographic Location.
   The edge in this case, is a road.

   It contains a list of getter methods:
    - getStart() (return GeographicPoint object)
    - getGoal() (return GeographicPoint object)
    - getRoadName() (return String object)
    - getRoadType() (return String object)
    - getLength() (return Double object)
