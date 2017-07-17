/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heap;

/**
 *
 * @author tony.tian
 */
public class HuffmanB implements Comparable<HuffmanB>{
    //stores frequency
    
    private int frequency;
    private byte value;
    
    public HuffmanB(byte v, int freq)
    {
        frequency = freq;
        value = v;
    }
    
    public void increment()
    {
        frequency++;
    }
    
    public int getFreq()
    {
        return frequency;
    }
    
    public byte getByte()
    {
        return value;
    }
    
    public int compareTo(HuffmanB other)
    {
        return frequency - other.frequency;        
    }
}
