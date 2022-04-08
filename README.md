# CSU22012-Data-Structures-and-Algorithms-Project
A document explaining the design decisions (a choice of data structures and algorithms used to implement each of the 3 main features), justifying them based on specific space/time trade-offs between alternatives, you have considered, considered in the context of the type and size of data
you are dealing with. 50

1. Finding the shortest paths between 2 bus stops (as input by the user), returning the list of stops en route as well as the associated “cost”.

When I initially saw this problem I thought that A* would be a good candidate. This is because A* takes a single source and finds a single destination. But when it came to implementing it I found that there was no admissible heuristic. This is because while I could find the distance from my current node to my end node at any time (using longitude and latitude)  The "cost" I was concerned with was correlated with the number of nodes to my goal. And while these two things may have been somewhat correlated there was no way for me to guarantee that the distance to the end node <= cost to the end node. (i.e. regardless of distance there could be a vertex between any two nodes). This means A* is not a suitable algorithm. 

Topological sort is another shortest-path algorithm, but it is not applicable in this case as bus networks are very lightly to have directed cycles. 

I also considered using the Floyd-Warshall algorithm. My idea was to run this algorithm once at runtime and then save the resulting array for user quarries. But considering the fact that Floyd-Warshall has a time complexity of O(V^3), I decided it would be better to use Dijkstra's algorithm which has a time complexity of O(E*log(V)). This is because for Floyd-Warshall to be more sensible here the user would have to make an obscene number of queries between points. 
(approximately (V^2/E*log(V)) queries which in this case is about 1.7 million vertexes from stop_times.txt and another 5k from transfers.txt giving us a total of roughly 1.7*10^6 vertexes along with 9k Edges from stops.txt. 
This gives us (V^2/E*log(V)) ≈ ((1.7 *10^6)^2 / (9*10^3 * log(1.7 *10^6))) ≈ 51539000 )

When it comes to my implementation of Dijkstra I initially considered implementing a [edge][edge] array to store my graph (This is how I wrote my Dijkstra for assignment 2) but I came to the conclusion that 


2. Searching for a bus stop by full name or by the first few characters in the name, using a ternary search tree (TST), returning the full stop information for each stop matching the search criteria (which can be zero, one or more stops)


3. Searching for all trips with a given arrival time, returning full details of all trips matching the criteria (zero, one or more), sorted by trip id
