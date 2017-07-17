/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heap;

/**
 *
 * @author tony.tian
 */
public class Heap {
    
    private Comparable[] heap;
    
    public Heap(Comparable[] data)
    {
        heap = data;
        heapify();
    }
    
    public void heapify()
    {
        for(int i = heap.length - 1; i >= 0; i--)
        {
            heapifyHelper(i);
        }
    }
    
    public void heapifyHelper(int i)
    {
        int max = i;
        int left = 2*i + 1;
        int right = 2*i + 2;
               
        if(left < heap.length && heap[left].compareTo(heap[max]) >= 0)
        {
            max = left;
        }
        
        if(right < heap.length && heap[right].compareTo(heap[max]) >= 0)
        {
            max = right;
        }      

        if(max != i)
        { 
            swap(i, max);
            //System.out.println();
            //print();
            heapifyHelper(max);
        }
    }
    
    public void swap(int i, int largest)
    {
        Comparable temp = heap[i];
        heap[i] = heap[largest];
        heap[largest] = temp;
    }
    
    public void add(Comparable c)
    {
        Comparable[] temp = new Comparable[heap.length + 1];
        int index = 0;
        for(Comparable i: heap)
        {
            temp[index] = i;
            index++;
        }
        temp[index] = c;
        heap = temp;
        heapify();
    }
    
    public Comparable top()
    {
        return heap[0];
    }
    
    public Comparable pop()
    {
        Comparable[] temp = new Comparable[heap.length - 1];
        Comparable pop = heap[0];
        int index = 0;
        for(int i = 1; i < heap.length; i++)
        {
            temp[index] = heap[i];
            index++;
        }
        heap = temp;
        heapify();
        return pop;
    }
    
    public void sort()
    {
        Comparable[] temp = heap;
        int length = heap.length;
        for(int i = 0; i < length; i++)
        {
            temp[i] = pop();
        }
        heap = temp;
    }
    
    public void print()
    {
        for(Comparable i:heap)
        {
            System.out.print(i + " ");
        }
    }
}
