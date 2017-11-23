## Graphs

_...in progress._

This project explore graphs using Google Maps API

### Week 2

**Method Implementations**

1. **Degree Sequence** which is an ordered list containing the degrees of each of the vertices in a graph, in non-increasing sorted order (with repetitions). The degree sequence of a graph is an invariant of the graph and can be used to distinguish between two graphs and to analyze graph properties.

2. **getDistance2** (get Distance 2) finds all vertices reachable by two hops from vertex.
  - **GraphAdjList** getDistance2 - get vertex's neighbors and loop through them adding to the new array.
  - **GraphAdjMatrix** getDistance2 - in this case can be obtained by squaring the matrix representation of the graph. Then creating a nested loop where you loop through the column of the inner squared matrix adding all values that are greater than 0 to the new array. 
