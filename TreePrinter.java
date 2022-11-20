public class TreePrinter {

	 public static <E extends Comparable<E>> String getString(BTree<E> tree) {
            if (tree.root == null) return "Tree has no nodes.";
            return getString(tree.root, "", true);
        }

        @SuppressWarnings("null")
		private static <E extends Comparable<E>> String getString(Node<E> node, String prefix, boolean isTail) {
            StringBuilder builder = new StringBuilder();
         	Node<E>[] children = null;


            builder.append(prefix).append((isTail ? "" : ""));
            for (int i = 0; i < node.numberOfKeys(); i++) {
                E value = node.getKey(i);
                 System.out.println("IndexOfNode: "+node.indexOf(node) +" indexofValue: "+node.indexOf(value) + " getkey: "+node.getKey(i));
                builder.append(value);
                if (i < node.numberOfKeys() - 1)
                    builder.append(" :: ");
            }
            builder.append("\n");

            if (node.children != null) {
                for (int i = 0; i < node.numberOfChildren() - 1; i++) {
                    Node<E> obj = node.getChild(i);
                    children[i] = obj; 
                    builder.append(getString(obj, prefix + (isTail ? "    " : "│   "), false));
                }
                if (node.numberOfChildren() >= 1) {
                    Node<E> obj = node.getChild(node.numberOfChildren() - 1);
                    builder.append(getString(obj, prefix + (isTail ? "    " : "│   "), true));
                }
            }
 
            return builder.toString();
        }
        
	
    }