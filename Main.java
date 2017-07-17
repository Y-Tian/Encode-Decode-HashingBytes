/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heap;

import java.io.*;

/**
 *
 * @author tony.tian
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //Comparable[] test = {2,15,11,4,8,1,9};

        //Heap heap = new Heap(test);
        //System.out.println();
        //heap.add(5);
        //heap.pop();
        //System.out.println(heap.top());
        //heap.sort();
        //heap.sort();
        //heap.print();

        //String path = new String("Test.txt");
        
        HuffmanEncode hE = new HuffmanEncode("Test.txt", "TestEncode.txt");
        hE.main();
        
        //HuffmanDecode hD = new HuffmanDecode("TestEncode.txt", "TestDecoded.txt");
        //hD.main();
        
        //System.out.println(String.format("%8s", Integer.toBinaryString((byte)98 & 0xFF)).replace(' ', '0'));
        
    }
}
