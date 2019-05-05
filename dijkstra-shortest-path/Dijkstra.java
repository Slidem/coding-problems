import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Alexandru Mihai
 */
public class Dijkstra {

    private Map<Integer, Integer> parentMap;

    private PriorityQueue<Vertex> distancesTree;

    private Set<Vertex> processed;

    private Vertex source;

    private Vertex destination;

    private boolean found;

    public Dijkstra(Vertex source, Vertex destination) {
        this.source = source;
        this.destination = destination;
        this.processed = new TreeSet<>();
        this.parentMap = new HashMap<>();
        this.distancesTree = getFromSourceNode(source);
        calculate();
    }

    private PriorityQueue<Vertex> getFromSourceNode(Vertex source) {
        //BFS search the graph in order to add all vertecies to the priority queue
        //sources node is given distance 0 in order to be the first one processed from the priority queue
        source.distance = 0;
        PriorityQueue<Vertex> distanceVertexes = new PriorityQueue<>();
        Set<Vertex> visited = new HashSet<>();
        Deque<Vertex> vertexStack = new LinkedList<>();
        vertexStack.push(source);
        while(!vertexStack.isEmpty()){
            Vertex v = vertexStack.pop();
            if(visited.add(v)){
                distanceVertexes.add(v);
                List<Vertex> neighbours = v.neighbours().stream().filter(n -> !visited.contains(n)).collect(Collectors.toList());
                neighbours.forEach(vertexStack::push);
            }
        }
        return distanceVertexes;
    }

    private void calculate() {
        while(!distancesTree.isEmpty()){
            Vertex min = distancesTree.poll();
            System.out.println("Extracting min vertex: " + min);

            processed.add(min);

            if(min == destination){
                found = true;
                break;
            }


            List<Edge> edges = min.getEdges();
            for (Edge edge : edges) {
                Vertex neighbour = edge.destination;
                int totalWeight = min.distance + edge.weight;

                // skip if vertex already processed or the total weight is bigger than already present distance
                if(processed.contains(neighbour) || neighbour.distance < totalWeight){
                    continue;
                }
                neighbour.distance = totalWeight;
                //need to remove and add element to the priority queue to recompute priority
                //java's priority queue .remove method is liniar O(n), but suppose we replace this priority queue with a binary heap for O(logn) performance :)
                distancesTree.remove(neighbour);
                distancesTree.add(neighbour);
                //add to parent
                parentMap.put(neighbour.id, min.id);
            }
        }
    }

    public void printShortestPath(){
        if(!found){
            System.out.println("No path found to destination");
            return;
        }

        Integer nodeId = destination.id;
        Deque<Integer> path = new LinkedList<>();
        while (nodeId != null) {
            path.addFirst(nodeId);
            nodeId = parentMap.get(nodeId);
        }

        System.out.println("Shortest path : " + path);
    }

    public static void main(String[] args) {

        Vertex node0 = new Vertex(0);
        Vertex node1 = new Vertex(1);
        Vertex node2 = new Vertex(2);
        Vertex node3 = new Vertex(3);
        Vertex node4 = new Vertex(4);
        Vertex node5 = new Vertex(5);
        Vertex node6 = new Vertex(6);
        Vertex node7 = new Vertex(7);
        Vertex node8 = new Vertex(8);


        node0.connect(node1, 4);
        node0.connect(node7, 8);

        node1.connect(node7, 11);
        node1.connect(node2, 8);

        node7.connect(node8, 7);
        node7.connect(node6, 1);

        node2.connect(node8, 2);
        node2.connect(node3, 7);
        node2.connect(node5, 4);

        node8.connect(node6, 6);

        node6.connect(node5, 2);

        node5.connect(node3, 14);
        node5.connect(node4, 10);

        node3.connect(node4, 9);


        new Dijkstra(node0, node4).printShortestPath();

    }

    static class Vertex implements Comparable<Vertex>{

        int id;

        int distance = Integer.MAX_VALUE;

        List<Edge> edges;

        public Vertex(int id) {
            this.id = id;
            this.edges = new ArrayList<>();
        }

        void connect(Vertex to, int weight) {
            edges.add(new Edge(this, to, weight));
            to.edges.add(new Edge(to, this, weight));
        }

        List<Vertex> neighbours(){
           return edges.stream().map(e -> e.destination).collect(Collectors.toList());
        }

        List<Edge> getEdges(){
            return edges;
        }

        @Override
        public int compareTo(Vertex o) {
            return this.distance - o.distance;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Vertex vertex = (Vertex) o;
            return id == vertex.id &&
                    distance == vertex.distance &&
                    Objects.equals(edges, vertex.edges);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

        @Override
        public String toString() {
            return "Vertex{" +
                    "id=" + id +
                    ", distance=" + distance +
                    '}';
        }
    }

    static class Edge {

        int weight;

        Vertex origin;

        Vertex destination;

        Edge(Vertex origin, Vertex destination, int weight) {
            this.weight = weight;
            this.origin = origin;
            this.destination = destination;
        }
    }


}

