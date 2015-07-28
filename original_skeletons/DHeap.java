/**
 * D-Heap
 */

public class DHeap
{
	
    private int size, max_size, d;
    private DHeap_Item[] array;

	// Constructor
	// m_d >= 2, m_size > 0
       DHeap(int m_d, int m_size) {
               max_size = m_size;
			   d = m_d;
               array = new DHeap_Item[max_size];
               size = 0;
       }
	
	// Getter for size
	public int getSize() {
		return size;
	}
	
  /**
     * public void arrayToHeap()
     *
     * The function builds a new heap from the given array.
     * Previous data of the heap should be erased.
     * preconidtion: array1.length() <= max_size
     *   postcondition: isHeap()
     *                            size = array.length()
     */
    public void arrayToHeap(DHeap_Item[] array1) 
    {
        return; // just for illustration - should be replaced by student code
    }

    /**
     * public boolean isHeap()
     *
     * The function returns true if and only if the D-ary tree rooted at array[0]
     * satisfies the heap property or size == 0.
     *   
     */
    public boolean isHeap() 
    {
        return false; // just for illustration - should be replaced by student code
    }


 /**
     * public int parent(i), child(i,k)
     * (2 methods)
     *
     * precondition: i >= 0
     *
     * The methods compute the index of the parent and the k-th child of 
     * vertex i in a complete D-ary tree stored in an array. 1 <= k <= d.
     * Note that indices of arrays in Java start from 0.
     */
    public int parent(int i) { return 999;} // just for illustration - should be replaced by student code
    public int child (int i, int k) { return 999;} // just for illustration - should be replaced by student code 

    /**
    * public void Insert(DHeap_Item item)
    *
    * precondition: item != null
    *               isHeap()
    *               size < max_size
    * 
    * postcondition: isHeap()
    */
    public void Insert(DHeap_Item item) 
    {        
    	return;// should be replaced by student code
    }

 /**
    * public void Delete_Min()
    *
    * precondition: size > 0
    *               isHeap()
    * 
    * postcondition: isHeap()
    */
    public void Delete_Min()
    {
     	// should be replaced by student code
    }


    /**
     * public String Get_Min()
     *
     * precondition: heapsize > 0
     *               isHeap()
     *		size > 0
     * 
     * postcondition: isHeap()
     */
    public DHeap_Item Get_Min()
    {
	return null;// should be replaced by student code
    }
	
  /**
     * public void Decrease_Key(DHeap_Item item, int delta)
     *
     * precondition: item.pos < size;
     *               item != null
     *               isHeap()
     * 
     * postcondition: isHeap()
     */
    public void Decrease_Key(DHeap_Item item, int delta)
    {
	return;// should be replaced by student code
    }
	
	  /**
     * public void Delete(DHeap_Item item)
     *
     * precondition: item.pos < size;
     *               item != null
     *               isHeap()
     * 
     * postcondition: isHeap()
     */
    public void Delete(DHeap_Item item)
    {
	return;// should be replaced by student code
    }
	
	/**
	Return a sorted array containing the same integers in the input array.
	Sorting should be done using the DHeap.
	*/
	public static int []DHeapSort(int[] array) {
		return;
	}
}
