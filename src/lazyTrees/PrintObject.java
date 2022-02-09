package lazyTrees;

/**
 * Traverses and prints out data from LazySearchTree
 * @param <E>
 */
class PrintObject<E> implements Traverser<E>
{
    /**
     * A functor that prints out the content of node
     * @param x   Content of LazySTNode
     */
    public void visit(E x)
    {
        System.out.print( x + " ");
    }
}