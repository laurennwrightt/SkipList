/*=============================================================================
|   Assignment:  HW 02 - Building and managing a SkipList
|
|       Author:  Lauren Wright
|     Language:  Java
|
|   To Compile:  javac Hw02.java
|   To Execute:  java Hw02 filename
|                     where filename is in the current directory and contains
|                           commands to insert, delete, print.
|
|        Class:  COP3503 - CS II Spring 2021
|   Instructor:  McAlpin
|     Due Date:  04/04/21
|
+=============================================================================*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.lang.Math;

public class Hw02 {
    /* 
    |   -------- READ IN FILE ----------
    |  ------ handles input error -------
    */

   public static void main (String [] args)throws IOException{
    if(args.length < 1){
        System.out.println("Error: Input file not specified");
        System.exit(0);
    }
    List<String> input = new ArrayList<String>();
    BufferedReader reader = new BufferedReader(new FileReader(args[0]));
    String temp;
    // stores file input into arraylist
    while((temp = reader.readLine()) != null){
        input.add(temp);
    }
    reader.close(); 

    /* --------- END FILE HANDLING ---------- */

    // for formating
    String filename = args[0];  

    // instance of SkipList class 
    SkipList sl = new SkipList();

    // formatting 
    System.out.println("For the input file named: " + filename);

    // checks to see if R is a parameter
    int seeding = 0;
    if (args.length == 2) {
        // value is 1 (true)
        seeding = 1;
    }

    // generates our random number
    Random rand = new Random();

    // parameter not specified automatically seeds with 42
    if (seeding == 0) {
        rand.setSeed(42);
    }
    else if(seeding == 1){
        long sd = System.currentTimeMillis();
        rand.setSeed(sd);
    }
    
    // formatting
    if (seeding == 0) {
        System.out.println("With the RNG unseeded,");
    } else {
        System.out.println("With the RNG seeded,"); 
    }          

    // call our analyze function
    sl.analyze(input, rand);

    // call function
    complexityindicator();

    } // end main

    public static void complexityindicator(){
        System.err.println("la985270; 5; 55.5");
    }
}

/********************************* End class ********************************/

class SkipList{
    public long pos = 100000000;
    public long neg = -100000000;
    
    public Node head;
    public Node tail;
    public int size;
    public int maxlvl;
    public long flip;

    public SkipList(){
        
        // initialize our nodes for inifitinies
        Node neginfinity = new Node(neg);
        Node posinfinity = new Node(pos);
        
        // connect our nodes
        neginfinity.right = posinfinity;
        posinfinity.left = neginfinity; 
        
        // set our values
        this.head = neginfinity;
        this.tail = posinfinity;
        this.maxlvl = 1;
        this.size = 0;

    }

    /* 
    | this function will analyze our inputed file
    | and call it's the matching instruction's function
    */
    public Node analyze(List<String> input, Random rand){
        SkipList list = new SkipList();
        for(int i = 0; i < input.size(); i++){

           // stores current line as string
           String currline = input.get(i);

           // temp variables
           int counter = 0;

           // keeps track of the current number
           String currint = "";

           // checks if current variable is insert
           if(currline.charAt(counter) == 'i'){
                /*
                | incriment by 2 to skip over space char to bring 
                | counter to the start of the number
                | to be inserted
                */
                counter+=2;

                // try catch to handle missing numeric parameter
                try{

                    // stores the number at counter to currint
                    currint = currint + currline.charAt(counter);
                    }
    
                    // if this errors, we know they didnt input a number
                    catch(Exception e){
                        continue;
                    }
    
                    // incriment counter to get the next possible digit of our number
                    counter++;
                    
                    // stores rest of our number to currint
                    while(counter < currline.length()){
                        currint = currint + currline.charAt(counter);
                        counter++;
                    }
    
                    // converts are int stored as a string to an int
                    int stringtoint = Integer.parseInt(currint);
    
                    // handles our insertion
                    insert(stringtoint, list, rand);
           }
           
           // checks if current instruction is delete
           else if (currline.charAt(counter) == 'd'){

            /*
            | incriment by 2 to skip over space char to bring 
            | counter to the start of the number
            | to be deleted
            */
            counter+=2;

            // try catch to handle missing numeric parameter
            try{
            
            // stores the number at counter to currint
            currint = currint + currline.charAt(counter);
            }

            // if this errors, we know they didnt input a number
            catch(Exception e){
                continue;
            }

            // incriment counter to get the next possible digit of our number
            counter++;

            // stores rest of our number to currint
            while(counter < currline.length()){
                currint = currint + currline.charAt(counter);
                counter++;
            }

            // converts are int stored as a string to an int
            int stringtoint = Integer.parseInt(currint);

            // handles deletion
            delete(list , stringtoint);
            }
            
            // handles search instruction
            else if (currline.charAt(counter) == 's'){

                /*
                | incriment by 2 to skip over space char to bring 
                | counter to the start of the number
                | to be searched
                */
                counter+=2;

                // try catch to handle missing numeric parameter
                try{

                // stores the number at counter to currint
                currint = currint + currline.charAt(counter);
                }

                // if this errors, we know they didnt input a number
                catch(Exception e){
                    continue;
                }

                 // incriment counter to get the next possible digit of our number
                counter++;

                // stores rest of our number to currint
                while(counter < currline.length()){
                    currint = currint + currline.charAt(counter);
                    counter++;
                }

                // converts are int stored as a string to an int
                int stringtoint = Integer.parseInt(currint);

                // prints not found if search returns false
                Node temp = search(list, stringtoint);
                    if (temp.value == stringtoint) {
                        System.out.println(temp.value + " found");
                    } 
                    else {
                        System.out.println(stringtoint + " NOT FOUND");  
                    }
                counter++;
            } 

           // checks if current instruction is print
           else if(currline.charAt(counter) == 'p'){
              printList(list);
              counter++;
                }
           
           // checks if current instruction is quit 
           else if (currline.charAt(counter) == 'q'){
              break;  
           }
           
           // when in doubt keep incrimenting :)
           else {
            counter++;
            }

        } // end loop
           return head;
           
    } // end analyze function
    
    // creates empty level
    public void levelup() {

        // negative & positive are refering to our infinites
        Node negative = new Node(this.neg);
        Node positive = new Node(this.pos);
        
        // links negative values
        negative.down = head;
        negative.right = positive;

        // links positive values
        positive.down = tail;
        positive.left = negative;

        // links our head and tail
        head.up = negative;
        tail.up = positive;
        
        // change our head and tail value
        head = negative;
        tail = positive;
        maxlvl++;
    }

    // function to print list 
    public void printList(SkipList list)
    {   
        // current node
        Node current = list.head;
    
        // every value is on level 1, traverse down
        while (current.down != null) {
            current = current.down;
        }
        
        // formatting 
        System.out.println("the current Skip List is shown below:");
        System.out.println("---infinity");

        // traverse level and print stack
        while (current.value != list.tail.value) {
            current = current.right;
            if (current.value != list.pos) { 
                // function to print stack
                print(current);
            }
        }

        // formatting
        System.out.println("+++infinity");
        System.out.println("---End of Skip List---");
        
    }   
    
    // prints on stack of numbers at a time
    public void print(Node node) {
        
        // formatting 
        System.out.print(" " + node.value + "; ");

        // checks if higher level is null
        if (node.up == null) {
            System.out.print("\n");
        }

        // if it not null then traverse stack and print
        while (node.up != null) {
            node = node.up;
            System.out.print(" " + node.value + "; ");
            if (node.up == null) {
                System.out.print("\n");
                break;
            }
        }
    }   
    
    // insert instruction
    public void insert(int value, SkipList list, Random rand){

        // our new node to insert
        Node insertion = new Node(value);

        // search to find our current node in our list
        Node current = search(list, value);

        // height of current stack
        int height;
        
        // already in list (don't account for)
        if (current.value != value) {
            
            // links left & right values of our current insertion
            insertion.left = current;
            insertion.right = current.right;

            // links back to current
            current.right = insertion;

            insertion.right.left = insertion;
            
            // randomly promotes level
            height = 1; 

            
            // coin toss!
            while (Math.abs(rand.nextInt() % 2) == 1) {
               
                // calls our level function
                if (height >= list.maxlvl) {
                    list.levelup();
                }
                
                // traverses up
                while (current.up == null) {
                    current = current.left;
                }

                // sets our current node to up
                current = current.up;
                
                // re link
                Node temp = new Node(value);

                // links temps left and right
                temp.left = current;
                temp.right = current.right;

                // links current
                current.right.left = temp;
                current.right = temp;

                // more, more linking!
                temp.down = insertion;
                insertion.up = temp;
                
                // Link in case value gets promoted again
                insertion = temp;
                
                // incriment our stack height
                height++;
            }
            
            // incriment size of our skip list
            list.size++;
            if (height > list.maxlvl) {
                list.maxlvl = height;
            }
        }
    }

    // search instruction
    public Node search(SkipList list, int key) {
        Node temp = list.head;
        
        // searches through whole list
        for (int k = list.maxlvl; k > 0; k--) {
            
            // traverse through our levels
            while (temp.right.value != list.pos && temp.right.value <= key) {
                temp = temp.right;
            }
            
            // checks if lower level is null
            if (temp.down != null) {
                temp = temp.down;
            } 
            // else we are at the bottom
            else { 
                break; 
            }
        }
        return temp;
    }
    
    // delete instruction
    public void delete(SkipList list, int key) {

        // searches for our current value to be deleted
        Node current = search(list, key);
        if (current.value == key) {

            // traverses 
            while (current != null) {

                // re links our list to delete desired node
                current.left.right = current.right;
                current.right.left = current.left;
                current = current.up;
            }

            // formatting
            System.out.println(key + " deleted");

            // must decrease the size of our list 
            list.size--;
        } else {

            // handles integer not found
            System.out.println(key + " integer not found - delete not successful");
        }
    }

}

/********************************* End class ********************************/
class Node{

    public String key; 
    public long value;
    
    public Node left;
    public Node right;
    public Node up;
    public Node down;
    
    // initializing
    public Node(long value){
        this.left = null;
        this.right = null;
        this.up = null;
        this.down = null;
        this.value = value;
    }

}

/*=============================================================================
|     I Lauren Wright (4850481) affirm that this program is
| entirely my own work and that I have neither developed my code together with
| any another person, nor copied any code from any other person, nor permitted
| my code to be copied  or otherwise used by any other person, nor have I
| copied, modified, or otherwise used programs created by others. I acknowledge
| that any violation of the above terms will be treated as academic dishonesty.
+=============================================================================*/