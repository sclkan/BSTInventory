package lazyTrees;

import java.util.NoSuchElementException;

/**
 * A class that creates BST to add and remove items from inventory in the supermarket
 * It also keeps track of our inventory and finds the lexicographic min and max
 * @author Sean Kan
 *
 * @param <E>   Generic object
 */
public class LazySearchTree<E extends Comparable<? super E>>
{
    protected int mSize;
    protected LazySTNode mRoot;
    protected int mSizeHard;

    /**
     * Constructor
     */
    public LazySearchTree()
    {
        clear();
    }

    /**
     * Keeps track of the number of hard nodes
     * @return    Number of total (deleted and undeleted) inventory
     */
    public int sizeHard()
    {
        return mSizeHard;
    }

    /**
     * Keeps track of size
     * @return    Number of current (undeleted) inventory
     */
    public int size()
    {
        return mSize;
    }

    /**
     * Clear size and node
     */
    public void clear()
    {
        mSizeHard = 0;
        mSize = 0;
        mRoot = null;
    }

    /**
     * Finds minimum node
     * @return   Minimum node that has not been deleted
     */
    public E findMin()
    {
        if (mRoot == null)
            throw new NoSuchElementException();
        return findMin(mRoot).data;
    }

    /**
     * Finds maximum node
     * @return    Maximum node that has not been deleted
     */
    public E findMax()
    {
        if (mRoot == null)
            throw new NoSuchElementException();
        return findMax(mRoot).data;
    }

    /**
     * Finds an item
     * @param x   Desired item
     * @return   Data of an item if exist or throws exception if null
     */
    public E find( E x )
    {
        LazySTNode resultNode;
        resultNode = find(mRoot, x);
        if (resultNode == null)
            throw new NoSuchElementException();
        return resultNode.data;
    }

    /**
     * Encapsulation of the find method
     * @param x   Desired Item
     * @return   A boolean stating whether or not item is in tree
     */
    public boolean contains(E x)
    {
        return find(mRoot, x) != null;
    }

    /**
     * Encapsulation of the insert method
     * @param x   Item to add to tree
     * @return  A boolean stating whether or not item was inserted
     */
    public boolean insert(E x)
    {
        int oldSize = mSize;
        mRoot = insert(mRoot, x);
        return (mSize != oldSize);
    }

    /**
     * Encapsulation of the remove method
     * @param x   Item to remove from tree
     * @return  A boolean stating whether or not item was removed
     */
    public boolean remove(E x)
    {
        int oldSize = mSize;
        remove(mRoot, x);
        return (mSize != oldSize);
    }

    public boolean collectGarbage()
    {
        int oldSize = mSize;
        collectGarbage(mRoot);
        return (mSize != oldSize);
    }

    /**
     * Traverses both deleted and undeleted nodes in tree
     * @param func  Function like printObject
     * @param <F>   Generic
     */
    public < F extends Traverser<? super E > > void traverseHard(F func)
    {
        traverseHard(func, mRoot);
    }

    /**
     * Traverses undeleted nodes in tree
     * @param func   Function like printObject
     * @param <F>    Generic
     */
    public < F extends Traverser<? super E > > void traverseSoft(F func)
    {
        traverseSoft(func, mRoot);
    }


    // private helper methods -------------------------------

    /**Helper method that does the actual search and finds the min node
     *
     * @param root    Tree to perform search
     * @return    Minimum node that has not been deleted
     */
    protected LazySTNode findMin(LazySTNode root)
    {
        if (root == null)
            return null;
        if (findMin(root.lftChild)!=null)
            return findMin(root.lftChild);
        if (!root.dltFlag)
            return root;
         else
            return findMin(root.rtChild);
    }

    /**
     * Helper method that does the actual search and finds the max node
     * @param root   Tree to perform search
     * @return    Maximum node that has not been deleted
     */
    protected LazySTNode findMax(LazySTNode root)
    {
        if (root == null)
            return null;
        if (findMax(root.rtChild)!=null)
            return findMax(root.rtChild);
        if (!root.dltFlag)
            return root;
        else
            return findMax(root.lftChild);
    }

    /**
     * Helper method that finds the true minimum
     * @param root    Tree to perform search
     * @return   Minimum node regardless of deletion
     */
    protected LazySTNode findMinHard(LazySTNode root)
    {
        if (root == null)
            return null;
        if (root.lftChild == null)
            return root;
        return findMin(root.lftChild);
    }

    /**
     * Helper method that finds the true maximum
     * @param root    Tree to perform search
     * @return    Maximum node regardless of deletion
     */
    protected LazySTNode findMaxHard(LazySTNode root)
    {
        if (root == null)
            return null;
        if (root.rtChild == null)
            return root;
        return findMin(root.rtChild);
    }

    /**
     * Help method that does the actually insertion
     * @param root   Tree
     * @param x    Item to add to tree
     * @return   Node
     */
    protected LazySTNode insert(LazySTNode root, E x)
    {
        int compareResult;  // avoid multiple calls to compareTo()

        if (root == null)
        {
            mSize++;
            mSizeHard++;
            return new LazySTNode(x, null, null);
        }
       compareResult = x.compareTo(root.data);
        if (compareResult < 0)
            root.lftChild = insert(root.lftChild, x);
        else if (compareResult > 0)
            root.rtChild = insert(root.rtChild, x);
        else if (root.dltFlag == true)
        {
            root.dltFlag = false;
            mSize++;
        }
        return root;
    }

    /**
     * Helper method that does the actual removal
     * @param root   Tree
     * @param x   Items to delete from tree
     */
    protected void remove(LazySTNode root, E x)
    {
        int compareResult;  // avoid multiple calls to compareTo()

        if (root == null)
            return;
        compareResult = x.compareTo(root.data);
        if ( compareResult < 0 )
            remove(root.lftChild, x);
        else if ( compareResult > 0 )
            remove(root.rtChild, x);
        // found the node
        else if (!root.dltFlag)
        {
            root.dltFlag = true;
            mSize --;
        }
        return;
    }

    protected LazySTNode removeHard(LazySTNode root, E x  ) {
        int compareResult;  // avoid multiple calls to compareTo()

        if (root == null)
            return null;

        compareResult = x.compareTo(root.data);
        if (compareResult < 0)
            root.lftChild = removeHard(root.lftChild, x);
        else if (compareResult > 0)
            root.rtChild = removeHard(root.rtChild, x);

            // found the node
        else if (root.lftChild != null && root.rtChild != null)
        {
            LazySTNode FMHresult = findMinHard(root.rtChild); //store the result of findMinHard
            root.data = FMHresult.data;
            root.dltFlag = FMHresult.dltFlag;
            root.rtChild = removeHard(root.rtChild, root.data);
        }
        else
        {
            root = (root.lftChild != null) ? root.lftChild : root.rtChild;
            mSizeHard--;
        }
        return root;
    }

    protected LazySTNode collectGarbage(LazySTNode root)
    {
        if (root == null)
            return null;

        if (root.lftChild!=null)
            root.lftChild = collectGarbage(root.lftChild);

        if (root.rtChild!=null)
            root.rtChild = collectGarbage(root.rtChild);

        if (root.dltFlag)
            root = removeHard(root, root.data);

        return root;
    }
    /**
     * Helper function that traverses both deleted and undeleted nodes in tree
     * @param func   PrintObject
     * @param treeNode   Nodes in tree
     * @param <F>   Generic
     */
    protected <F extends Traverser<? super E>> void traverseHard(F func, LazySTNode treeNode)
    {
        if (treeNode == null)
            return;
        traverseHard(func, treeNode.lftChild);
        func.visit(treeNode.data);
        traverseHard(func, treeNode.rtChild);
    }

    /**
     *
     * Helper function that traverses undeleted nodes in tree
     * @param func   PrintObject
     * @param treeNode   Nodes in tree
     * @param <F>   Generic
     */
    protected <F extends Traverser<? super E>> void traverseSoft(F func, LazySTNode treeNode)
    {
        if (treeNode == null)
            return;
        traverseSoft(func, treeNode.lftChild);
        if (!treeNode.dltFlag)
            func.visit(treeNode.data);
        traverseSoft(func, treeNode.rtChild);
    }

    /**
     * Helper function that searches for a node
     * @param root   Tree
     * @param x   Desired item
     * @return   Node
     */
    protected LazySTNode find(LazySTNode root, E x)
    {
        int compareResult;  // avoid multiple calls to compareTo()

        if (root == null)
            return null;
        compareResult = x.compareTo(root.data);
        if (compareResult < 0)
            return find(root.lftChild, x);
        if (compareResult > 0)
            return find(root.rtChild, x);
        if (root.dltFlag)
            return null;
        return root;   // found
    }

    /**
     * Private LazySTNode class
     */
    private class LazySTNode
    {
        protected LazySTNode lftChild, rtChild;
        protected E data;
        protected boolean dltFlag;

        /**
         * Constructor
         * @param d   Data
         * @param lft  Left Child
         * @param rt   Right Child
         */
        public LazySTNode(E d, LazySTNode lft, LazySTNode rt)
        {
            lftChild = lft;
            rtChild = rt;
            data = d;
            dltFlag = false;
        }
    }
}
