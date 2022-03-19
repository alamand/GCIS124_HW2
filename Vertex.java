import java.util.HashSet;
import java.util.Set;

public class Vertex<E> {
    private E value;
    private Set<Vertex<E>> neighbors;    

    public Vertex(E value) {
        this.value = value;
        neighbors = new HashSet<>();
    }

    public E getValue()
    {
        return value;
    }

    public void connect(Vertex<E> neighbour)
    {
        neighbors.add(neighbour);
    }

    public boolean connected(Vertex<E> neighbour)
    {
        if(neighbors.contains(neighbour))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public Set<Vertex<E>> getNeighbors()
    {
        return neighbors;
    }

    @Override
    public String toString() {
        return value + "";
    }
}
