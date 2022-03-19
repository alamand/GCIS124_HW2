import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AdjacencyGraph<E> implements Graph<E> {

    private Map<E, Vertex<E>> vertices;

    public AdjacencyGraph() {
        vertices = new HashMap<E, Vertex<E>>();
    }

    @Override
    public int size() {
        return vertices.size();
    }

    @Override
    public void add(E value) {
        Vertex<E> v = new Vertex<E>(value);
        vertices.put(value, v);
    }

    @Override
    public boolean contains(E value) {
        return vertices.containsKey(value);
    }

    @Override
    public void connectDirected(E a, E b) {
        Vertex<E> value_a = vertices.get(a);
        Vertex<E> value_b = vertices.get(b);
        value_a.connect(value_b);
    }

    @Override
    public void connectUndirected(E a, E b) {
        Vertex<E> value_a = vertices.get(a);
        Vertex<E> value_b = vertices.get(b);
        value_a.connect(value_b);
        value_b.connect(value_a);    
    }

    @Override
    public boolean connected(E a, E b) {
        Vertex<E> value_a = vertices.get(a);
        Vertex<E> value_b = vertices.get(b);
        return value_a.connected(value_b);
    }

    @Override
    public boolean bfSearch(E start, E end) {
        Vertex<E> start_vertex = vertices.get(start);
        Vertex<E> end_vertex = vertices.get(end);
        LinkedList<Vertex<E>> queue = new LinkedList<>();
        HashSet<Vertex<E>> set = new HashSet<>();
        queue.add(start_vertex);
        set.add(start_vertex);
        while(!queue.isEmpty())
        {
            Vertex<E> v = queue.remove();
            if(v.equals(end_vertex))        //if we've found the destination
            {
                return true;
            }
            else    //add the neighbor
            {
                Set<Vertex<E>> neighbors = v.getNeighbors();
                for(Vertex<E> neighbor: neighbors)
                {
                    if(!set.contains(neighbor))
                    {
                        queue.add(neighbor);
                        set.add(neighbor);
                    }
                }
            }
        }
        return false;
    }

    @Override
    public List<Vertex<E>> bfPath(E start, E end) {
        Vertex<E> start_vertex = vertices.get(start);
        Vertex<E> end_vertex = vertices.get(end);
        LinkedList<Vertex<E>> queue = new LinkedList<>();
        queue.add(start_vertex);
        HashMap<Vertex<E>, Vertex<E>> map = new HashMap<>();
        map.put(start_vertex, null);
        while(!queue.isEmpty())
        {
            Vertex<E> v = queue.remove();
            if(v.equals(end_vertex))        //if we've found the destination
            {
                break;
            }
            else    //add the neighbor
            {
                Set<Vertex<E>> neighbors = v.getNeighbors();
                for(Vertex<E> neighbor: neighbors)
                {
                    if(!map.containsKey(neighbor))
                    {
                        queue.add(neighbor);
                        map.put(neighbor, v);       // v is the predecessor
                    }
                }
            }
        }
        //build the path
        return makePath(map, end_vertex);
    }

    private LinkedList<Vertex<E>> makePath(HashMap<Vertex<E>, Vertex<E>> map, Vertex<E> end_vertex)
    {
        if(!map.containsKey(end_vertex))        //the end_vertex is not in the map!
        {
            return null;
        }
        else
        {
            LinkedList<Vertex<E>> path = new LinkedList<>();
            Vertex<E> current = end_vertex;
            path.add(current);  
            while(current != null)
            {
                Vertex<E> predecessor = map.get(current);
                if(predecessor != null)     //end of the map not yet reached
                {
                    path.addFirst(predecessor);                    
                }
                current = predecessor;  
            }
            return path;
        }
    }

    @Override
    public boolean dfSearch(E start, E end) {
        Vertex<E> start_vertex = vertices.get(start);
        Vertex<E> end_vertex = vertices.get(end);
        HashSet<Vertex<E>> visited = new HashSet<>();
        visited.add(start_vertex);
        visitDFS(start_vertex, visited);
        if(visited.contains(end_vertex))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private void visitDFS(Vertex<E> v, HashSet<Vertex<E>> prev_visited)
    {
        Set<Vertex<E>> neighbors = v.getNeighbors();
        for(Vertex<E> neighbor: neighbors)
        {
            if(!prev_visited.contains(neighbor))
            {
                prev_visited.add(neighbor);
                visitDFS(neighbor, prev_visited);
            }
        }
    }

    @Override
    public List<Vertex<E>> dfPath(E start, E end) {
        Vertex<E> start_vertex = vertices.get(start);
        Vertex<E> end_vertex = vertices.get(end);
        HashSet<Vertex<E>> visited = new HashSet<>();
        visited.add(start_vertex); 
        return visitDFPath(start_vertex, end_vertex, visited);
    }

    private LinkedList<Vertex<E>> visitDFPath(Vertex<E> v, Vertex<E> e, HashSet<Vertex<E>> prev_visited) //6.3.9
    {
        LinkedList<Vertex<E>> ll;
        if(v.equals(e))
        {
            ll = new LinkedList<>();
            ll.add(e);
            return ll;
        }
        else
        {       
            Set<Vertex<E>> neighbors = v.getNeighbors();
            for(Vertex<E> neighbor: neighbors)
            {
                if(!prev_visited.contains(neighbor))
                {
                    prev_visited.add(neighbor);
                    ll = visitDFPath(neighbor, e, prev_visited);
                    if(ll != null)
                    {
                        ll.addFirst(v);
                        return ll;
                    }
                }                
            }
            return null;    
        }        
    }
}
