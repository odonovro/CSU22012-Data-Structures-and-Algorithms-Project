design decisions 
Notaion{
	N is the total number of elements
	V stands for the number of vertexes
	E stands for the number of edges
	L stands for the length of a search term
}
1. Finding the shortest paths between 2 bus stops (as input by the user)

When I initially saw this problem I thought that A* would be a good candidate. This is because A* takes a single source and finds a single destination. But when it came to implementing it I found that there was no admissible heuristic. This is because while I could find the distance from my current node to my end node at any time (using longitude and latitude)  The "cost" I was concerned with was correlated with the number of nodes to my goal. And while these two things may have been somewhat correlated there was no way for me to guarantee that the distance to the end node <= cost to the end node. (i.e. regardless of distance there could be a vertex between any two nodes). This means A* is not a suitable algorithm. 

I also considered using the Floyd-Warshall algorithm. My idea was to run this algorithm once at runtime and then save the resulting array for user quarries. But considering the fact that Floyd-Warshall has a time complexity of O(V^3), I decided it would be better to use Dijkstra's algorithm which has a time complexity of O(E*log(V)). This is because for Floyd-Warshall to be more sensible here the user would have to make an obscene number of queries between points. 
(approximately (V^2/E*log(V)) queries which in this case is about 1.7 million vertexes from stop_times.txt and another 5k from transfers.txt giving us a total of roughly 1.7*10^6 vertexes along with 9k Edges from stops.txt. 
This gives us (V^2/E*log(V)) ≈ ((1.7 *10^6)^2 / (9*10^3 * log(1.7 *10^6))) ≈ 51539000 )

When it comes to my implementation of Dijkstra I initially considered implementing a [edge][edge] array to store my graph (This is how I wrote my Dijkstra for assignment 2) but since there are ≈ 8000 edges this array would take around 64*10^6 integer entries. Because of this, I decided to instead implement an adjacency list graph representation. I did this using an array with E entries where each entry refers to a specific edge and points to a directed edge object. For a given entry x of the array, x would point to a directed edge object that has an entry next. This next entry will point to another directed edge object that has x as its source, and so on. 
for example 
array[0]	=	(1)
(1).next 	= 	(2)	
(2).next	=	(3)
this would mean that edge 0 has a direct edge to 1, 2 and 3
From this, the space complexity is clearly equal to 
O(E + E*(average number of vertexes from an edge))
but
(average number of vertexes from an edge) = (V/E)
therefore we get a space complexity of E + E*(V/E) = V + E. 
Since in our case E ≈ 8*10^3 and V ≈ 1.6 *10^6 therefore our complexity is around 1.6 *10^6. This is much better than 64*10^6 discussed above. 

The downside of the adjacency list representation compared to the [edge] by [edge] array is, an [edge] by [edge] array has instant access time for any specific directed edge whereas the adjacency list takes (V/E) time (as we would have to iterate over the directed edges at a specific entry of the array). 
This is not a problem however as when running Dijkstra we are not interested in specifically directed edges but rather we have to iterate overall directed edges from each edge. 

2. Searching for a bus stop by full name or by the first few characters in the name, using a ternary search tree (TST)

While I didn't get to choose what algorithm I wanted to use for this section I think that TST is still the best choice. This is because it has a run time for finding an item of O(L + ln(N)). And considering that in our case (10^1 < L < 10^2) and ln(N ≈ 10^3)≈7. This is much faster than naive search methods like linear search which has a time complexity of O(N). 

3. Searching for all trips with a given arrival time, returning full details of all trips matching the criteria (zero, one or more), sorted by trip id

For this I 
