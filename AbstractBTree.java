/**
* This is the interface providing the public contract for any B-tree implementation. It comprises of only
* two methods:
* <ol>
* <li>contains(E element)</li>
* <li>add(E element)</li>
* </ol>
* @param <E> the type of elements in this tree; which must implement the <code>java.lang.Comparable</code>
* interface (since B-trees require a totally ordered data type).
*/
 
public interface AbstractBTree<E extends Comparable<E>> {
	
//  

/**
* Follows the search algorithm for B-trees to determine whether the specified element exists in this
* B-tree. If no no element is found, <code>null</code> is returned.
*
* @param element the specified element to be searched.
* @return a node-index pair that consists of the node in this tree where the specified element is
* found, and the index of this element in that node; <code>null</code> if the element is
* not present in this tree.
*/
//boolean contains(E element);
//public boolean contains(E value);
NodeIndexPair<E> contains(E element);

/**
* Add the specified element to this B-tree, using the insertion algorithm for B-trees. If this element
* already exists in this tree, then calling this method has no effect on the tree.
*
* @param element the specified element to be added to this tree.
*/
public boolean add(E element);
/**
 * {@inheritDoc}
 */
public E remove(E value);
/**
 * {@inheritDoc}
 */
void clear();




/**
 * Add value to the tree. Tree can contain multiple equal values.
 * 
 * @param value to add to the tree.
 * @return True if successfully added to tree.
 */
//public boolean add( value);

/**
 * Remove first occurrence of value in the tree.
 * 
 * @param value to remove from the tree.
 * @return T value removed from tree.
 */
//public T remove(T value);

/**
 * Clear the entire stack.
 */
//public void clear();

/**
 * Does the tree contain the value.
 * 
 * @param value to locate in the tree.
 * @return True if tree contains value.
 */

/**
 * Get number of nodes in the tree.
 * 
 * @return Number of nodes in the tree.
 */
public int size();

/**
 * Validate the tree according to the invariants.
 * 
 * @return True if the tree is valid.
 */
public boolean validate();

/**
 * Get Tree as a Java compatible Collection
 * 
 * @return Java compatible Collection
 */
public java.util.Collection<E> toCollection();

boolean containss(E value);

//boolean contains(E value, int i);




}
