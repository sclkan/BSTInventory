package lazyTrees;

/**
 * An interface for tree traversal algorithms
 * @param <E>
 */
public interface Traverser<E>
{
    public void visit(E x);
}