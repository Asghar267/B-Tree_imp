import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

 
public class Node<E extends Comparable<E>> {
		public  int nodeLocation;
		public  int index;
        private E[] keys = null;
        int keysSize = 0;
        public Node<E>[] children = null;

         public Node<E>[] elements;

        int childrenSize = 0;
        private Comparator<Node<E>> comparator = new Comparator<Node<E>>() {
            @Override
            public int compare(Node<E> arg0, Node<E> arg1) {
                return arg0.getKey(0).compareTo(arg1.getKey(0));
            }
        };

        protected Node<E> parent = null;

       
        Node(Node<E> parent, int maxKeySize, int maxChildrenSize) {
            this.parent = parent;
            this.keys = (E[]) new Comparable[maxKeySize + 1];
            this.keysSize = 0;
            this.children = new Node[maxChildrenSize + 1];
            this.childrenSize = 0;

        }

        E getKey(int index) {
            return keys[index];
        }

        int indexOf(E value) {
            for (int i = 0; i < keysSize; i++) {
                if (keys[i].equals(value)) return i;
            }
            return -1;
        }

        void addKey(E value) {
            keys[keysSize++] = value;
            Arrays.sort(keys, 0, keysSize);
        }

        E removeKey(E value) {
            E removed = null;
            boolean found = false;
            if (keysSize == 0) return null;
            for (int i = 0; i < keysSize; i++) {
                if (keys[i].equals(value)) {
                    found = true;
                    removed = keys[i];
                } else if (found) {
                    // shift the rest of the keys down
                    keys[i - 1] = keys[i];
                }
            }
            if (found) {
                keysSize--;
                keys[keysSize] = null;
            }
            return removed;
        }

        E removeKey(int index) {
            if (index >= keysSize)
                return null;
            E value = keys[index];
            for (int i = index + 1; i < keysSize; i++) {
                // shift the rest of the keys down
                keys[i - 1] = keys[i];
            }
            keysSize--;
            keys[keysSize] = null;
            return value;
        }

        int numberOfKeys() {
            return keysSize;
        }

        Node<E> getChild(int index) {
            if (index >= childrenSize)
                return null;
            return children[index];
        }

        int indexOf(Node<E> child) {
            for (int i = 0; i < childrenSize; i++) {
                if (children[i].equals(child))
                    return i;
            }
            return -1;
        }

        boolean addChild(Node<E> child) {
            child.parent = this;
            children[childrenSize++] = child;
            Arrays.sort(children, 0, childrenSize, comparator);
            return true;
        }

        boolean removeChild(Node<E> child) {
            boolean found = false;
            if (childrenSize == 0)
                return found;
            for (int i = 0; i < childrenSize; i++) {
                if (children[i].equals(child)) {
                    found = true;
                } else if (found) {
                    // shift the rest of the keys down
                    children[i - 1] = children[i];
                }
            }
            if (found) {
                childrenSize--;
                children[childrenSize] = null;
            }
            return found;
        }

        Node<E> removeChild(int index) {
            if (index >= childrenSize)
                return null;
            Node<E> value = children[index];
            children[index] = null;
            for (int i = index + 1; i < childrenSize; i++) {
                // shift the rest of the keys down
                children[i - 1] = children[i];
            }
            childrenSize--;
            children[childrenSize] = null;
            return value;
        }

        int numberOfChildren() {
            return childrenSize;
        }

        /**
         * {@inheritDoc}
         */
        
        
      public String toStringg() {
    	return toString(0);
    	}
//    	// based on what toString() does, think about what ‘elements’ and ‘children’ can be
    	private String toString(int depth) {
    	StringBuilder builder = new StringBuilder();
    	String blankPrefix = new String(new char[depth]).replace("\0", "\t");
    	List<String> printedElements = new LinkedList<>();
    	for (Node<E> e : elements) printedElements.add(e.toString());
        String eString = String.join(" :: ", printedElements);
        builder.append(blankPrefix).append(eString).append("\n");
        children.forEach(c ->builder.append(c.toString(depth + 1)));
//    	((Iterable<Integer>) parent).forEach(c -> builder.append(c.toString(depth + 1)));
    	return builder.toString();
    	}
    }
