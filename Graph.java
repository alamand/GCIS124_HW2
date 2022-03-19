import java.util.List;

public interface Graph<E> {
    public void add(E value);
    public boolean contains(E value);
    public int size();
    public void connectDirected(E a, E b);
    public void connectUndirected(E a, E b);
    public boolean connected(E a, E b);
    public boolean bfSearch(E start, E end);
    public List<Vertex<E>> bfPath(E start, E end);
    public boolean dfSearch(E start, E end);
    public List<Vertex<E>> dfPath(E start, E end);
}
