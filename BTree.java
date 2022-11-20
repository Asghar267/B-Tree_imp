//package com.jwetherell.algorithms.data_structures;

import java.util.ArrayDeque;
import java.util.Deque;

//import BTree.Node;

//import com.jwetherell.algorithms.data_structures.interfaces.ITree;

/**
 * B-tree is a tree data structure that keeps data sorted and allows searches,
 * sequential access, insertions, and deletions in logarithmic time. The B-tree
 * is a generalization of a binary search tree in that a node can have more than
 * two children. Unlike self-balancing binary search trees, the B-tree is
 * optimized for systems that read and write large blocks of data. It is
 * commonly used in databases and file-systems.
 * <p>
  
 */
 


@SuppressWarnings("unchecked")


//public class BTree<E extends Comparable<E>> implements ITree<E> {
public class BTree<E extends Comparable<E>> implements AbstractBTree<E>{
	private static <E extends Comparable<E>> void addAllInThisOrder(BTree<E> theTree, E... items) {
		for (E item : items)
		theTree.add(item);
		}
public static void main(String[] args) {
	BTree<Integer> integerBTree = new BTree<>(3);
	addAllInThisOrder(integerBTree, 10, 20, 30, 40, 50);
	System.out.println(integerBTree);	
 		System.out.println("BTree");
// 		integerBTree.add(10);
// 		integerBTree.add(20);
// 		integerBTree.add(30);
// 		integerBTree.add(40);
// 		integerBTree.add(50);

	integerBTree.add(60);
	integerBTree.add(70);
	integerBTree.add(80);
	integerBTree.add(90);
	integerBTree.add(95);
	integerBTree.add(85);
	integerBTree.add(65);
	integerBTree.add(75);
	integerBTree.add(55);
	integerBTree.add(190);
	integerBTree.add(13);
	integerBTree.add(200);
	integerBTree.add(110);
	integerBTree.add(120);
	integerBTree.add(210);
	integerBTree.add(310);
	integerBTree.add(410);
	integerBTree.add(230);
	integerBTree.add(240);
	System.out.println("di "+ integerBTree.validate());
integerBTree.validate();





//	System.out.println(integerBTree);

//
		System.out.println(integerBTree);
		System.out.println( integerBTree.toString());
//		System.out.println("print: "+integerBTree.contains(10));
		
		
		NodeIndexPair<Integer> foundNumber = integerBTree.contains(80);
		System.out.printf("Element %d found at index %d of the following node:\n%s%n",
		80,
		foundNumber.index,
		foundNumber.nodeLocation
		);
		
	}
	


    // Default to 2-3 Tree
    private int minKeySize = 1;
    private int minChildrenSize = minKeySize + 1; // 2
    private int maxKeySize = 2 * minKeySize; // 2
    private int maxChildrenSize = maxKeySize + 1; // 3
    public  NodeIndexPair<E> nodeindexpair;
    Node<E> root = null;
    private int size = 0;

     
    /**
     * Constructor for B-Tree which defaults to a 2-3 B-Tree.
     */
    public BTree() { }

    /**
     * Constructor for B-Tree of minimumDegree parameter. minimumDegree here means minimum 
     * number of keys in a non-root node. 
     * 
     * @param minimumDegree
     *            of the B-Tree.
     */
    public BTree(int minimumDegree) {
//        this.minKeySize = minimumDegree; // before
        this.minKeySize = minimumDegree-1; // now
        this.minChildrenSize = minKeySize + 1;
        this.maxKeySize = 2 * minKeySize-1;
        this.maxChildrenSize = maxKeySize + 1;
 
    }
   

    
 

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(E value) {
//    	if(contains(value)) {
//    		System.out.println("Already Exist");
//    		return false;
//    	}
        if (root == null) {
            root = new Node<E>(null, maxKeySize, maxChildrenSize);
            root.addKey(value);
        } else {
            Node<E> node = root;
            while (node != null) {
                if (node.numberOfChildren() == 0) {
                    node.addKey(value);
                    if (node.numberOfKeys() <= maxKeySize) {
                        // A-OK
                        break;
                    }                         
                    // Need to split up
                    split(node);
                    break;
                }
                // Navigate

                // Lesser or equal
                E lesser = node.getKey(0);
                if (value.compareTo(lesser) <= 0) {
                    node = node.getChild(0);
                    continue;
                }

                // Greater
                int numberOfKeys = node.numberOfKeys();
                int last = numberOfKeys - 1;
                E greater = node.getKey(last);
                if (value.compareTo(greater) > 0) {
                    node = node.getChild(numberOfKeys);
                    continue;
                }

                // Search internal nodes
                for (int i = 1; i < node.numberOfKeys(); i++) {
                    E prev = node.getKey(i - 1);
                    E next = node.getKey(i);
                    if (value.compareTo(prev) > 0 && value.compareTo(next) <= 0) {
                        node = node.getChild(i);
                        break;
                    }
                }
            }
        }

        size++;
//        root.toString();
        return true;
    }

    /**
     * The node's key size is greater than maxKeySize, split down the middle.
     * 
     * @param nodeToSplit
     *            to split.
     */
    private void split(Node<E> nodeToSplit) {
        Node<E> node = nodeToSplit;
        int numberOfKeys = node.numberOfKeys();
        int medianIndex = numberOfKeys / 2;
        E medianValue = node.getKey(medianIndex);

        Node<E> left = new Node<E>(null, maxKeySize, maxChildrenSize);
        for (int i = 0; i < medianIndex; i++) {
            left.addKey(node.getKey(i));
        }
        if (node.numberOfChildren() > 0) {
            for (int j = 0; j <= medianIndex; j++) {
                Node<E> c = node.getChild(j);
                left.addChild(c);
            }
        }

        Node<E> right = new Node<E>(null, maxKeySize, maxChildrenSize);
        for (int i = medianIndex + 1; i < numberOfKeys; i++) {
            right.addKey(node.getKey(i));
        }
        if (node.numberOfChildren() > 0) {
            for (int j = medianIndex + 1; j < node.numberOfChildren(); j++) {
                Node<E> c = node.getChild(j);
                right.addChild(c);
            }
        }

        if (node.parent == null) {
            // new root, height of tree is increased
            Node<E> newRoot = new Node<E>(null, maxKeySize, maxChildrenSize);
            newRoot.addKey(medianValue);
            node.parent = newRoot;
            root = newRoot;
            node = root;
            node.addChild(left);
            node.addChild(right);
        } else {
            // Move the median value up to the parent
            Node<E> parent = node.parent;
            parent.addKey(medianValue);
            parent.removeChild(node);
            parent.addChild(left);
            parent.addChild(right);

            if (parent.numberOfKeys() > maxKeySize) split(parent);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E remove(E value) {
        E removed = null;
        Node<E> node = this.getNode(value);
        removed = remove(value,node);
        return removed;
    }

    /**
     * Remove the value from the Node and check invariants
     * 
     * @param value
     *            E to remove from the tree
     * @param node
     *            Node to remove value from
     * @return True if value was removed from the tree.
     */
    private E remove(E value, Node<E> node) {
        if (node == null) return null;

        E removed = null;
        int index = node.indexOf(value);
        removed = node.removeKey(value);
        if (node.numberOfChildren() == 0) {
            // leaf node
            if (node.parent != null && node.numberOfKeys() < minKeySize) {
                this.combined(node);
            } else if (node.parent == null && node.numberOfKeys() == 0) {
                // Removing root node with no keys or children
                root = null;
            }
        } else {
            // internal node
            Node<E> lesser = node.getChild(index);
            Node<E> greatest = this.getGreatestNode(lesser);
            E replaceValue = this.removeGreatestValue(greatest);
            node.addKey(replaceValue);
            if (greatest.parent != null && greatest.numberOfKeys() < minKeySize) {
                this.combined(greatest);
            }
            if (greatest.numberOfChildren() > maxChildrenSize) {
                this.split(greatest);
            }
        }

        size--;

        return removed;
    }

    /**
     * Remove greatest valued key from node.
     * 
     * @param node
     *            to remove greatest value from.
     * @return value removed;
     */
    private E removeGreatestValue(Node<E> node) {
        E value = null;
        if (node.numberOfKeys() > 0) {
            value = node.removeKey(node.numberOfKeys() - 1);
        }
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * {@inheritDoc}
     */
    
    @Override
    public NodeIndexPair<E> contains(E value) {
        Node<E> node = getNode(value);
        NodeIndexPair<E> nodpair = new NodeIndexPair<E>();
        if (node != null) { 
           node.nodeLocation = node.indexOf(value);
           node.index = node.indexOf(value);
           nodpair.index = node.index;
           nodpair.nodeLocation = node; 
           return nodpair;
       }	
       
        else {
        	nodpair.index = -1;
    	   return nodpair;
       }
    }
    
    /**
     * Get the node with value.
     * 
     * @param value
     *            to find in the tree.
     * @return Node<E> with value.
     */
    private Node<E> getNode(E value) {
        Node<E> node = root;
        while (node != null) {
            E lesser = node.getKey(0);
            if (value.compareTo(lesser) < 0) {
                 if (node.numberOfChildren() > 0) {
                     node = node.getChild(0);
                }
                else
                    node = null;
                continue;
            }

            int numberOfKeys = node.numberOfKeys();
            int last = numberOfKeys - 1;
            E greater = node.getKey(last);
            if (value.compareTo(greater) > 0) {
                if (node.numberOfChildren() > numberOfKeys)
                    node = node.getChild(numberOfKeys);
                else
                    node = null;
                continue;
            }

            for (int i = 0; i < numberOfKeys; i++) {
                E currentValue = node.getKey(i);
                if (currentValue.compareTo(value) == 0) {
                    return node;
                }

                int next = i + 1;
                if (next <= last) {
                    E nextValue = node.getKey(next);
                    if (currentValue.compareTo(value) < 0 && nextValue.compareTo(value) > 0) {
                        if (next < node.numberOfChildren()) {
                            node = node.getChild(next);
                            break;
                        }
                        return null;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Get the greatest valued child from node.
     * 
     * @param nodeToGet
     *            child with the greatest value.
     * @return Node<E> child with greatest value.
     */
    private Node<E> getGreatestNode(Node<E> nodeToGet) {
        Node<E> node = nodeToGet;
        while (node.numberOfChildren() > 0) {
            node = node.getChild(node.numberOfChildren() - 1);
        }
        return node;
    }

    /**
     * Combined children keys with parent when size is less than minKeySize.
     * 
     * @param node
     *            with children to combined.
     * @return True if combined successfully.
     */
    private boolean combined(Node<E> node) {
        Node<E> parent = node.parent;
        int index = parent.indexOf(node);
        int indexOfLeftNeighbor = index - 1;
        int indexOfRightNeighbor = index + 1;

        Node<E> rightNeighbor = null;
        int rightNeighborSize = -minChildrenSize;
        if (indexOfRightNeighbor < parent.numberOfChildren()) {
            rightNeighbor = parent.getChild(indexOfRightNeighbor);
            rightNeighborSize = rightNeighbor.numberOfKeys();
        }

        // Try to borrow neighbor
        if (rightNeighbor != null && rightNeighborSize > minKeySize) {
            // Try to borrow from right neighbor
            E removeValue = rightNeighbor.getKey(0);
            int prev = getIndexOfPreviousValue(parent, removeValue);
            E parentValue = parent.removeKey(prev);
            E neighborValue = rightNeighbor.removeKey(0);
            node.addKey(parentValue);
            parent.addKey(neighborValue);
            if (rightNeighbor.numberOfChildren() > 0) {
                node.addChild(rightNeighbor.removeChild(0));
            }
        } else {
            Node<E> leftNeighbor = null;
            int leftNeighborSize = -minChildrenSize;
            if (indexOfLeftNeighbor >= 0) {
                leftNeighbor = parent.getChild(indexOfLeftNeighbor);
                leftNeighborSize = leftNeighbor.numberOfKeys();
            }

            if (leftNeighbor != null && leftNeighborSize > minKeySize) {
                // Try to borrow from left neighbor
                E removeValue = leftNeighbor.getKey(leftNeighbor.numberOfKeys() - 1);
                int prev = getIndexOfNextValue(parent, removeValue);
                E parentValue = parent.removeKey(prev);
                E neighborValue = leftNeighbor.removeKey(leftNeighbor.numberOfKeys() - 1);
                node.addKey(parentValue);
                parent.addKey(neighborValue);
                if (leftNeighbor.numberOfChildren() > 0) {
                    node.addChild(leftNeighbor.removeChild(leftNeighbor.numberOfChildren() - 1));
                }
            } else if (rightNeighbor != null && parent.numberOfKeys() > 0) {
                // Can't borrow from neighbors, try to combined with right neighbor
                E removeValue = rightNeighbor.getKey(0);
                int prev = getIndexOfPreviousValue(parent, removeValue);
                E parentValue = parent.removeKey(prev);
                parent.removeChild(rightNeighbor);
                node.addKey(parentValue);
                for (int i = 0; i < rightNeighbor.keysSize; i++) {
                    E v = rightNeighbor.getKey(i);
                    node.addKey(v);
                }
                for (int i = 0; i < rightNeighbor.childrenSize; i++) {
                    Node<E> c = rightNeighbor.getChild(i);
                    node.addChild(c);
                }

                if (parent.parent != null && parent.numberOfKeys() < minKeySize) {
                    // removing key made parent too small, combined up tree
                    this.combined(parent);
                } else if (parent.numberOfKeys() == 0) {
                    // parent no longer has keys, make this node the new root
                    // which decreases the height of the tree
                    node.parent = null;
                    root = node;
                }
            } else if (leftNeighbor != null && parent.numberOfKeys() > 0) {
                // Can't borrow from neighbors, try to combined with left neighbor
                E removeValue = leftNeighbor.getKey(leftNeighbor.numberOfKeys() - 1);
                int prev = getIndexOfNextValue(parent, removeValue);
                E parentValue = parent.removeKey(prev);
                parent.removeChild(leftNeighbor);
                node.addKey(parentValue);
                for (int i = 0; i < leftNeighbor.keysSize; i++) {
                    E v = leftNeighbor.getKey(i);
                    node.addKey(v);
                }
                for (int i = 0; i < leftNeighbor.childrenSize; i++) {
                    Node<E> c = leftNeighbor.getChild(i);
                    node.addChild(c);
                }

                if (parent.parent != null && parent.numberOfKeys() < minKeySize) {
                    // removing key made parent too small, combined up tree
                    this.combined(parent);
                } else if (parent.numberOfKeys() == 0) {
                    // parent no longer has keys, make this node the new root
                    // which decreases the height of the tree
                    node.parent = null;
                    root = node;
                }
            }
        }

        return true;
    }

    /**
     * Get the index of previous key in node.
     * 
     * @param node
     *            to find the previous key in.
     * @param value
     *            to find a previous value for.
     * @return index of previous key or -1 if not found.
     */
    private int getIndexOfPreviousValue(Node<E> node, E value) {
        for (int i = 1; i < node.numberOfKeys(); i++) {
            E t = node.getKey(i);
            if (t.compareTo(value) >= 0)
                return i - 1;
        }
        return node.numberOfKeys() - 1;
    }

    /**
     * Get the index of next key in node.
     * 
     * @param node
     *            to find the next key in.
     * @param value
     *            to find a next value for.
     * @return index of next key or -1 if not found.
     */
    private int getIndexOfNextValue(Node<E> node, E value) {
        for (int i = 0; i < node.numberOfKeys(); i++) {
            E t = node.getKey(i);
            if (t.compareTo(value) >= 0)
                return i;
        }
        return node.numberOfKeys() - 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validate() {
        if (root == null) return true;
        return validateNode(root);
    }

    /**
     * Validate the node according to the B-Tree invariants.
     * 
     * @param node
     *            to validate.
     * @return True if valid.
     */
    private boolean validateNode(Node<E> node) {
        int keySize = node.numberOfKeys();
        if (keySize > 1) {
            // Make sure the keys are sorted
            for (int i = 1; i < keySize; i++) {
                E p = node.getKey(i - 1);
                E n = node.getKey(i);
                if (p.compareTo(n) > 0)
                    return false;
            }
        }
        int childrenSize = node.numberOfChildren();
        if (node.parent == null) {
            // root
            if (keySize > maxKeySize) {
                // check max key size. root does not have a min key size
                return false;
            } else if (childrenSize == 0) {
                // if root, no children, and keys are valid
                return true;
            } else if (childrenSize < 2) {
                // root should have zero or at least two children
                return false;
            } else if (childrenSize > maxChildrenSize) {
                return false;
            }
        } else {
            // non-root
            if (keySize < minKeySize) {
                return false;
            } else if (keySize > maxKeySize) {
                return false;
            } else if (childrenSize == 0) {
                return true;
            } else if (keySize != (childrenSize - 1)) {
                // If there are chilren, there should be one more child then
                // keys
                return false;
            } else if (childrenSize < minChildrenSize) {
                return false;
            } else if (childrenSize > maxChildrenSize) {
                return false;
            }
        }

        Node<E> first = node.getChild(0);
        // The first child's last key should be less than the node's first key
        if (first.getKey(first.numberOfKeys() - 1).compareTo(node.getKey(0)) > 0)
            return false;

        Node<E> last = node.getChild(node.numberOfChildren() - 1);
        // The last child's first key should be greater than the node's last key
        if (last.getKey(0).compareTo(node.getKey(node.numberOfKeys() - 1)) < 0)
            return false;

        // Check that each node's first and last key holds it's invariance
        for (int i = 1; i < node.numberOfKeys(); i++) {
            E p = node.getKey(i - 1);
            E n = node.getKey(i);
            Node<E> c = node.getChild(i);
            if (p.compareTo(c.getKey(0)) > 0)
                return false;
            if (n.compareTo(c.getKey(c.numberOfKeys() - 1)) < 0)
                return false;
        }

        for (int i = 0; i < node.childrenSize; i++) {
            Node<E> c = node.getChild(i);
            boolean valid = this.validateNode(c);
            if (!valid)
                return false;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public java.util.Collection<E> toCollection() {
        return (new JavaCompatibleBTree<E>(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return TreePrinter.getString(this);
        
    }
    
    
//    public String toStringg() {
//    	
//    	return root.toString();
//    	}
//    	// based on what toString() does, think about what ‘elements’ and ‘children’ can be
//    	private String toString(int depth) {
//    	StringBuilder builder = new StringBuilder();
//    	String blankPrefix = new String(new char[depth]).replace("\0", "\t");
//    	List<String> printedElements = new LinkedList<>();
//    	
//    	
//    	for (E e : root.keys) printedElements.add(e.toString());
//    	String eString = String.join(" :: ", printedElements);
//    	builder.append(blankPrefix).append(eString).append("\n");
////    	children.forEach(c -> builder.append(c.toString(depth + 1)));
//		((Iterable<Integer>) root).forEach(c -> builder.append(c.toString(depth + 1)));
//
//    	return builder.toString();
//    	}
//    
    

    private static class TreePrinter {

        public static <T extends Comparable<T>> String getString(BTree<T> tree) {
            if (tree.root == null) return "Tree has no nodes.";
            return getString(tree.root, "", true);
        }

        private static <T extends Comparable<T>> String getString(Node<T> node, String prefix, boolean isTail) {
            StringBuilder builder = new StringBuilder();

//            builder.append(prefix).append((isTail ? "" : ""));
//            builder.append(prefix).append((isTail ? " " : " "));
            builder.append(prefix).append(isTail ? " " : " ");


            for (int i = 0; i < node.numberOfKeys(); i++) {
                T value = node.getKey(i);
                builder.append(value);
                if (i < node.numberOfKeys() - 1)
                    builder.append(" :: ");
            }
            builder.append("\n");

            if (node.children != null) {
                for (int i = 0; i < node.numberOfChildren() - 1; i++) {
                    Node<T> obj = node.getChild(i);
                    builder.append(getString(obj, prefix + (isTail ? "    " : "    "), false));
 
                }
                if (node.numberOfChildren() >= 1) {
                    Node<T> obj = node.getChild(node.numberOfChildren() - 1);
                    builder.append(getString(obj, prefix + (isTail ? "    " : "    "), true));
                }
            }

            return builder.toString();
        }
    }

       
    public static class JavaCompatibleBTree<E extends Comparable<E>> extends java.util.AbstractCollection<E> {

        private BTree<E> tree = null;

        public JavaCompatibleBTree(BTree<E> tree) {
            this.tree = tree;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(E value) {
            return tree.add(value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(Object value) {
            return (tree.remove((E)value)!=null);
        }

        /**
         * {@inheritDoc}
         */
        public boolean containss(Object value) {
            return tree.containss((E)value);
        }

        /**
         * {@inheritDoc}
          */
        @Override
        public int size() {
            return tree.size();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public java.util.Iterator<E> iterator() {
            return (new BTreeIterator<E>(this.tree));
        }

        private static class BTreeIterator<C extends Comparable<C>> implements java.util.Iterator<C> {

            private BTree<C> tree = null;
            private Node<C> lastNode = null;
            private C lastValue = null;
            private int index = 0;
            private Deque<Node<C>> toVisit = new ArrayDeque<Node<C>>();

            protected BTreeIterator(BTree<C> tree) {
                this.tree = tree;
                if (tree.root!=null && tree.root.keysSize>0) {
                    toVisit.add(tree.root);
                }
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean hasNext() {
                if ((lastNode!=null && index<lastNode.keysSize)||(toVisit.size()>0)) return true; 
                return false;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public C next() {
                if (lastNode!=null && (index < lastNode.keysSize)) {
                    lastValue = lastNode.getKey(index++);
                    return lastValue;
                }
                while (toVisit.size()>0) {
                    // Go thru the current nodes
                    Node<C> n = toVisit.pop();

                    // Add non-null children
                    for (int i=0; i<n.childrenSize; i++) {
                        toVisit.add(n.getChild(i));
                    }

                    // Update last node (used in remove method)
                    index = 0;
                    lastNode = n;
                    lastValue = lastNode.getKey(index++);
                    return lastValue;
                }
                return null;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void remove() {
                if (lastNode!=null && lastValue!=null) {
                    // On remove, reset the iterator (very inefficient, I know)
                    tree.remove(lastValue,lastNode);

                    lastNode = null;
                    lastValue = null;
                    index = 0;
                    toVisit.clear();
                    if (tree.root!=null && tree.root.keysSize>0) {
                        toVisit.add(tree.root);
                    }
                }
            }
        }
    }


	@Override
	public boolean containss(E value) {
		// TODO Auto-generated method stub
		return false;
	}}

	 

