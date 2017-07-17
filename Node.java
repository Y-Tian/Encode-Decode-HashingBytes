/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heap;

/**
 *
 * @author tony.tian
 */
public class Node implements Comparable<Node>{
    
    private Node left;
    private Node right;
    private int value;
    private int freq;
    private HuffmanB huffmanB;
    private String binary;
    
    public void setHuffmanB(HuffmanB huffmanB)
    {
        this.huffmanB = huffmanB;
    }
    
    public Node getLeft()
    {
        return left;
    }
    
    public Node getRight()
    {
        return right;
    }
    
    public void setLeft(Node left)
    {
        this.left = left;
    }
    
    public void setRight(Node right)
    {
        this.right = right;
    }
    
    public void setValToFreq()
    {
        this.value = freq;
    }
    
    public int getValue()
    {
        return value;
    }
    
    public void setValue()
    {
        this.value = left.getValue() + right.getValue();
    }
    
    public void setFrequency()
    {
        this.freq = huffmanB.getFreq();
    }
    
    public int getFrequency()
    {
        return freq;
    }
    
    public byte getByte()
    {
        return  huffmanB.getByte();
    }
    
    public void setBinary(String n)
    {
        binary = n;
    }
    
    public String getBinary()
    {
        return binary;
    }
    
    public int compareTo(Node other)
    {
        return value - other.value;
    }
}
