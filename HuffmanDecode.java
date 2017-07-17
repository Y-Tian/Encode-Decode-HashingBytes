/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heap;
import java.io.*;
import java.util.*;
/**
 *
 * @author tony.tian
 */
public class HuffmanDecode {
    
    private String input;
    private String pathOut;
    private String message = "";
    private Node root;
    private ArrayList<String> letters = new ArrayList();
    private HashMap<String, Byte> map = new HashMap<String, Byte>();
    
    public HuffmanDecode(String path, String pathOut)
    {
        this.pathOut = pathOut;     
        getInput(path);
        input = input.substring(input.indexOf("1") + 1);//removes padding at the front
        root = getMapHelper();
    }
    
    public void getInput(String s)//initialize
    {
        try
        {
            FileReader fr = new FileReader(s);
            Scanner sc = new Scanner(fr);
            input = sc.nextLine();
            fr.close();
        }
        catch(IOException e){}
    }
    
    public Node getMapHelper()//makes the structure, setting all nodes to zero
    {
        Node n = new Node();
        if(input.charAt(0) == '1')
        {
            input = input.substring(1);
            n.setLeft(getMapHelper());
        }
        else
        {
            input = input.substring(1);
        }
        if(input.charAt(0) == '1')
        {
            input = input.substring(1);
            n.setRight(getMapHelper());
        }
        else
        {
            input = input.substring(1);
        }
        return n;
    }
    
    public void setLetters()//sets the children nodes to their specified letters
    {      
        String binary = "";
        setLettersHelper(root, binary);
    }
    
    public void setLettersHelper(Node n, String binary)//helper
    {
        if(n.getLeft() != null || n.getRight() != null)
        {
            if(n.getLeft() != null)
            {
                setLettersHelper(n.getLeft(), binary + "0");
            }
            if(n.getRight() != null)
            {
                setLettersHelper(n.getRight(), binary + "1");
            }
        }
        else
        {    
            int i = Integer.parseInt(input.substring(0, 8), 2);
            //System.out.println(input.substring(0, 8));//testing
            byte b = (byte)i;
            map.put(binary, b);//key is path, data is the letter in bytes
            letters.add(binary);
            //System.out.println(binary);//testing
            //System.out.println(b);//testing
            input = input.substring(8);//TEST with maps at home
        }
    }
    
    public void decodeMessage()//decodes the message by referring to the hashmap
    {
        int length = input.length();
        String s = "";
        for(int i = 0; i < length; i++)
        {
            s += input.substring(0, 1);
            input = input.substring(1);
            if(map.containsKey(s))
            {
                message += (char)(map.get(s) & 0xFF);
                System.out.print((char)(map.get(s) & 0xFF));
                s = "";
            }
        }
        decodeMessageHelper();//prints to file
    }
    
    public void decodeMessageHelper()//helper
    {
        try
        {
            FileWriter fw = new FileWriter(pathOut);
            fw.write(message);
            fw.close();
        }
        catch(IOException e){}
    }
    
    public void main()//main
    {
        printTreeMap(root);
        setLetters();
        decodeMessage();
        //printMap();//testing     
    }
    
    public void printMap()//testing
    {
        for(String s: letters)
        {
            if(map.containsKey(s))
            {
                System.out.println(map.get(s));
            }
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
