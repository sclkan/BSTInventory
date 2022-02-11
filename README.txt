project folder:
sclkan-cs1c-project05/


Brief description of submitted files:

src/lazyTrees/Item.java
    - Represents an inventory item with name and count

src/lazyTrees/LazySearchTree.java
    - A BST tree that adds and removes items from inventory in the supermarket
    - Keeps track of total and current inventory
    - Finds lexicographic min and max
    - Includes new functionality that cleans up the tree via hard deletions

src/lazyTrees/PrintObject.java
    - A functor that prints out the content of node
    - Traverses and prints out data from LazySearchTree

src/lazyTrees/SuperMarket.java
    - Simulates the market scenario of buying and adding items to a store's inventory
    - Implements BST with lazy deletion to keep track of total and current inventory

src/lazyTrees/Traverser.java
    - An interface for tree traversal algorithms

resources/inventory_invalid_removal.txt
    - An example of testing the boundary condition when removing an item that may not exist

resources/inventory_log.txt
    - Inventory file to test for item addition and removal

resources/inventory_short.txt
    - Short inventory file to test for removal of root node from LazySearchTree

resources/inventory_test4.txt
    - Additional file to thoroughly test the program

resources/RUN.txt
    - console output of SuperMarket.java

README.txt
    - description of submitted files