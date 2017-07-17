/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heap;
import java.io.*;
import java.util.*;
import java.nio.*;
/**
 *
 * @author tony.tian
 */
public class HuffmanEncode {

    private byte[] input;
    private HuffmanB[] freq;
    private Node root;
    private HashMap<Byte, String> map = new HashMap<Byte, String>();
    private String pathIn;
    private String pathOut;
    private String encodedMsg = "";
    private byte[] result;
    
    
    public HuffmanEncode(String inputPath, String outputPath)
    {
        pathIn = inputPath;
        pathOut = outputPath;
        initializeBytes();
        initialize();
    }
    
    public void initialize()//initialize
    {
        try
        {
            FileReader fr = new FileReader(pathIn);
            Scanner sc = new Scanner(fr);
            String s = new String();
            while(sc.hasNextLine())
            {
                s += sc.nextLine();
            }
            input = s.getBytes();
        }
        catch(IOException e)
        {}   
    }
    
    public void initializeBytes()//sets all byte value frequency's to 0 
    {
        freq = new HuffmanB[256];
        int index = 0;
        for(int i = -128; i <= 127; i++)
        {            
            HuffmanB hb = new HuffmanB((byte)i, 0);
            freq[index] = hb;
            index++;
        }
    }
    
    public void setFreq()//finds the frequency of each byte
    {
        for(int i = 0; i < input.length; i++)
        {
            int temp = (int)input[i] + 128;//so the index starts at 0
            freq[temp].increment();//adds 1
        }
    }
    
    public int distinctHash()//finds the amount of distinct bytes for sortFreq() //helper
    {
        LinkedHashSet<Byte> l = new LinkedHashSet<>();
        for(Byte b : input)
        {
            l.add(b);
        } 
        return l.size();
    }
    
    public void sortFreq()//sorts the freq array after it's been set. delete 0's, then sort
    {  
        Arrays.sort(freq);
        HuffmanB[] temp = new HuffmanB[distinctHash()];//needs distinct bytes
        int index = 0;
        for(int i = 0; i < freq.length; i++)
        {
            if(freq[i].getFreq() != 0)
            {
                HuffmanB hb = new HuffmanB(freq[i].getByte(), freq[i].getFreq());//problem here with the bytes value
                temp[index] = hb;
                index++;
            }
        }
        freq = temp;
    }
    
    public void buildTree()//builds a tree of nodes
    {
        if(freq.length >= 2)
        {
            ArrayList<Node> tree = new ArrayList<Node>();
            for(int i = 0; i < freq.length; i++)
            {
                Node n = new Node();
                n.setHuffmanB(freq[i]);
                n.setFrequency();
                n.setValToFreq();
                tree.add(n);
            }
            while(tree.size() > 1)
            {
                Node n = new Node();
                n.setLeft(tree.get(1));
                n.setRight(tree.get(0));
                n.setValue();
                tree.remove(0);
                tree.remove(0);
                tree.add(n);
                Collections.sort(tree);
            }
            root = tree.get(0);
        }
        else if(freq.length >= 1)
        {
            ArrayList<Node> tree = new ArrayList<Node>();
            for(int i = 0; i < freq.length; i++)
            {
                Node n = new Node();
                n.setHuffmanB(freq[i]);
                n.setFrequency();
                tree.add(n);
            }
            while(tree.size() > 1)
            {
                Node n = new Node();
                n.setRight(tree.get(0));
                n.setValue();
                tree.remove(0);
                tree.add(n);
            }
            root = tree.get(0);
        }
        else
        {
            System.out.println("No data");
        }
    }
  
    public void makeBinaryTree()//makes the children either 0 or 1; start at root
    {        
        String binary = "";
        recurBinaryHelper(root, map, binary);
    }
    
    public void recurBinaryHelper(Node n, HashMap map, String binary)//helper
    {
        if(n.getLeft() != null || n.getRight() != null)
        {
            if(n.getRight() != null)
            {
                recurBinaryHelper(n.getRight(), map, binary + "1");
            }
            if(n.getLeft() != null)
            {
                recurBinaryHelper(n.getLeft(), map, binary + "0");
            }
        }
        else
        {
            //add node's binary info
            n.setBinary(binary);
            byte b = n.getByte();
            map.put(b, binary);
        }
    }
    
    public String printBinaryTree(Node n)//substring 1 because it goes all the way to the bottom for the left side;;minus root
    {
        if(n != null)
        {
            return "1" + printBinaryTree(n.getLeft()) + printBinaryTree(n.getRight());
        }
        return "0";
    }
    
    public String printLetters(Node n, byte b)//preorder; prints out the letters from left to right; children nodes
    {
        if(n != null)
        {
            if(n.getLeft() != null || n.getRight() != null)
            {
                return printLetters(n.getLeft(), (byte)0) + printLetters(n.getRight(), (byte)0);
            }
            return printLetters(n.getLeft(), n.getByte());
        }
        return String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
    }
    
    public String printBinaryToText()//prints input as binary to text file
    {
        String str = "";
        for(byte b : input)
        {
            if(map.containsKey(b))
            {
                String s = (String)map.get(b);
                str += s;
            }
        }
        return str;
    }
    
    public void addPadding(Node n)//turns the file into a multiple of 8
    {
        String s = printBinaryTree(n).substring(1) + printLetters(n, (byte)0) + printBinaryToText();
        encodedMsg = s;
        s = "";
        int padding = encodedMsg.length()%8;
        if(padding != 0)
        {
            for(int i = 0; i < padding - 1; i++)
            {
               s += "0";
            }
            s += "1";
        }
        else
        {
            s = "00000001";
        }
        s += encodedMsg;
        encodedMsg = s;
        //System.out.println(encodedMsg);
    }
    
    public void turnIntoBytes()
    {
        int len = encodedMsg.length();
        result = new byte[len/8];
        for(int i = 0; i < result.length; i++)
        {
            Integer byteVal = Integer.parseInt(encodedMsg.substring(i * 8, i * 8 + 8), 2);
            result[i] = byteVal.byteValue();
        }
    }
    
    public void printToFile()
    {
        try
        {
            //FileWriter fw = new FileWriter(pathOut);
            //fw.write(encodedMsg);
            //fw.close();
            FileOutputStream f = new FileOutputStream(pathOut);
            f.write(result);
            f.close();
        }
        catch(IOException e){}
    }

    public void main()//main method
    {
        setFreq();
        sortFreq();
        buildTree();
        makeBinaryTree();
        addPadding(root);
        turnIntoBytes();
        printToFile();
        //printTreeMap(root);//viewing only
        //printLetterBinary(root);//viewing only
        //printBinaryTree(root);//viewing only
        //printLetters(root); //testing
    }
    
    public void printTree()//test
    {
        preorder(root, "");
    }
    
    private void preorder(Node n, String code)//test helper
    {
        System.out.println(n.getValue() + " = " + code);
        if(n.getLeft() != null)
        {
            preorder(n.getLeft(), code + "0");
        }
        if(n.getRight() != null)
        {
            preorder(n.getRight(), code + "1");
        }
    }
    
    public void printLetterBinary(Node n)//testing
    {
        if(n.getLeft() != null || n.getRight() != null)
        {
            if(n.getRight() != null)
            {
                printLetterBinary(n.getRight());
            }
            if(n.getLeft() != null)
            {
                printLetterBinary(n.getLeft());
            }
        }
        else
        {
            String s = Byte.toString(n.getByte());
            int a = Integer.parseInt(s);
            char c = (char)a;
            System.out.println(Character.toString(c) + " " + n.getBinary());
        }
    }
    
    public void printTreeMap(Node n)//testing
    {
        printTreeMapHelper(n);
        if(n.getLeft() != null)
        {
            printTreeMap(n.getLeft());
        }
        if(n.getRight() != null)
        {
            printTreeMap(n.getRight());
        }    
    }
    
    public void printTreeMapHelper(Node n)//testing helper
    {
        System.out.print(n.getValue() + " ");
        if(n.getLeft() != null)
        {
            System.out.print(n.getLeft().getValue() + " ");
        }
        if(n.getRight() != null)
        {
            System.out.print(n.getRight().getValue() + " ");
        }
        System.out.println();
    }
}
