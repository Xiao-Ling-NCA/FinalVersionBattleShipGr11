/*
Chengfu Zhao
ICS3U1
June 16 2024
Most Game Logic is in this class
*/

import java.util.*;
import java.io.*;
public class BattleShipRunTime{
   
   public static char[][] putBoatPosition(char[][] board, int shipLength, char boatType){
      final int boardLen = 10;
      Scanner sc = new Scanner(System.in);
      
      boolean possible = false;
      int row;
      int column;
      
      /// flag for the boat position being good or bad
      while(possible == false){
         System.out.println("\t1\t2\t3\t4\t5\t6\t7\t8\t9\t10");
         for(int r = 0; r < boardLen; r++){
            System.out.print((r+1)+"\t");
            for(int c = 0; c < boardLen; c++){
               System.out.print(board[r][c]+"\t");
            }
            System.out.print("\n");
         }
         // gets user input
         try{
            System.out.println("Row:");
            row = sc.nextInt() - 1;     
            System.out.println("Column: ");
            column = sc.nextInt() -1;      
         } 
         
         // catches any exceptions
         catch(Exception iox){
            sc.nextLine();
            System.out.println("Please try again");
            System.out.println("Row:");
            row = sc.nextInt() - 1;     
            System.out.println("Column: ");
            column = sc.nextInt() -1;      
         }
         
         // make sure its in range
         while(row <0 || row >9 || column<0 || column >9){
               System.out.println("Row:");
               row = sc.nextInt() - 1;     
               System.out.println("Column: ");
               column = sc.nextInt() -1;      

               sc.nextLine();      
         }
         
         sc.nextLine();
         System.out.println("Rotation: \nw - Up\na - Left\nd - Right\ns - Down ");
         int falseCounter = 0;
         char direction = sc.nextLine().charAt(0);
         //explanation for each variable, ill only explain for this one since it will get repetitive
         if (direction == 'a'){// left
            for (int i = 0; i<shipLength; i++){
               // for each square and column number, if there is a wrong, then everything is wrong
               if (((column - i <0) == true) || board[row][column-i] !='-' ){
                  falseCounter = falseCounter+1;
               }
            }
            // checks if every boat square is valid 
            if (falseCounter==0){
               possible = true;
            }   
            // if the flag is true, then list the inputted char to the left repeating until its the end of the boat's length
            if (possible == true){
               for(int change = 0; change<shipLength;change++){
                  board[row][column-change] = boatType;
               }
            }
            //please enter a valid orientation if the flag  is turned on false. 
            else{
               System.out.println("Please enter a valid orientation, press enter to continue.");
               sc.nextLine();
               }
            }
               //------------------------------------------------------
            else if(direction == 's'){//down
               for (int i = 0; i<shipLength; i++){
                     if(((row + i > 9) == true) || board[row +i][column] != '-'){
                        falseCounter = falseCounter +1;
                     }
                  }
                  
                  if (falseCounter==0){
                     possible = true;
                  } 
                  
                  if (possible == true){
                     for(int change = 0; change<shipLength;change++){
                        board[row+change][column] = boatType;
                     }
                  }
                  else{
                     System.out.println("Please enter a valid orientation, press enter to continue.");
                     sc.nextLine();
                  }
                     
               }  
               //------------------------------------------
            else if (direction == 'w'){//up
               for(int i = 0; i<shipLength; i++){
                  if(((row-i < 0)==true) || board[row-i][column] != '-'){
                     falseCounter = falseCounter +1;
                  }
               }
               
               if (falseCounter==0){
                     possible = true;
               }
               
               if (possible == true){
                  for(int change = 0; change<shipLength;change++){
                        board[row-change][column] = boatType;
                  }
               }
               else{
                     System.out.println("Please enter a valid orientation, press enter to continue.");
                     sc.nextLine();
                  }
               }
               //---------------------------------------------------------
               else if(direction == 'd'){//right
                  for(int i = 0; i<shipLength; i++){
                     if(((column + i>9)==true) || board[row][column+i] != '-'){
                        falseCounter = falseCounter +1;
                     }
                     
                  }
                  
                  if (falseCounter==0){
                     possible = true;
                  }
                  
                  if (possible == true){
                     for(int change = 0; change<shipLength;change++){
                        board[row][column+change] = boatType;
                     }
                  }
                  else{
                     System.out.println("Please enter a valid orientation, press enter to continue.");
                     sc.nextLine();
                  }
                  
               }
               else{
                  System.out.println("Error, please re-enter the boat's details. press enter to continue.");
                  sc.nextLine();
               }
         }
      return board;
   }
  //============================================================================
   
   public static char[][] playerBoardStarting(){
   
      Scanner sc = new Scanner(System.in);
   
      final int boardLen = 10;
      int[] ships={2,3,3,4,5};
      String[] shipType = {"Destroyer","Cruiser","Submarine","Battleship","Aircraft Carrier"};
      
      char[][] playerBoard = new char[boardLen][boardLen];
      
      for (int r = 0; r<boardLen; r++){
         for (int c = 0; c<boardLen; c++){
            playerBoard[r][c] = '-';
         } 
      }  
      
      for(int i = 0; i<ships.length; i++){
            playerBoard = putBoatPosition(playerBoard,ships[i],shipType[i].charAt(0));
      }
      return playerBoard;
   }
   //================================================================
   public static char[][] computerBoardStarting(){
      
      final int boardLen = 10;
      final int[] shipLen = {2,3,3,4,5};
      final char[] boatName = {'D','C','S','B','A'};
      final char[] direction = {'s','d'}; // im only going to allow the computer to choose two directions simply because its easier to code
      
      char[][] board = new char[boardLen][boardLen];
      
      for (int r = 0; r< boardLen; r++){
         for(int c = 0; c<boardLen; c++){
            board[r][c] = '-';
         }
      }
      
      // for every boat
      for (int b = 0; b<shipLen.length;b++){
         boolean valid = false;

         while (valid == false){
            int falseCounter = 0;
               // choose a random direction
            char dir = direction[(int)(Math.random()*(direction.length))];
               // if the direction is s, make sure its within its limits going to the right but not without limits going down
            if (dir == 's'){
               int row = (int)(Math.random()*(boardLen - (shipLen[b]+1)));
               int column = (int)(Math.random()*(boardLen));
                  
               for (int idx = 0; idx<shipLen[b]; idx++){
                  if (board[row+idx][column] != '-'){
                     falseCounter = falseCounter + 1;
                  }
               }
                  
               if (falseCounter == 0){
                  for (int idx = 0; idx<shipLen[b]; idx++){
                     board[row+idx][column] = boatName[b];
                  }
                  valid = true;
               } 
            }   
               
            else{//d
               // same logic. 
               int row = (int)(Math.random()*((boardLen)));
               int column = (int)(Math.random()*(boardLen - (shipLen[b]+1)));
                  
               for (int idx = 0; idx<shipLen[b]; idx++){
                  if(board[row][column+idx] != '-'){
                     
                     falseCounter = falseCounter + 1;
                     
                  }
               }
                  
                  
               if (falseCounter == 0){
                  for (int idx = 0; idx<shipLen[b]; idx++){
                     board[row][column+idx] = boatName[b];
                  }
                  valid = true;
               }
            }
         } 
      }  
      return board;
   }
   
   //=============================================================================================
   
   
   // Checks for a valid hit
   public static char checkHit(int row, int column,char[][] playerBoard, char[][]computerBoard){
      // if on the hitboard the player has already hit it (not), and  if the computerboard it isnt an empty square, make sure its a hit, or else a miss.
      if (playerBoard[row][column] != 'O' && playerBoard[row][column] != 'X'){
         if(computerBoard[row][column] != '-'){
            return 'X';
         }
         else{
            return 'O';
         }
      }
      else{
         return'M';
      }  
   }
   
   public static int[] boatCounting(char[][] graph){
                 // Destroyer, Cruiser, Submarine, Battleship, Aircraft Carrier
      boolean[] boatFound ={false, false, false, false, false};
      
      // when a boat is on the board, it should at least have one (1) of its characters on the board,
      // if its not there that means it died which is why its false and then true.
      for(int r = 0; r<graph.length;r++){
         for(int c=0; c<graph[r].length; c++){
            switch(graph[r][c]){
               case 'D':
                  boatFound[0] = true;
                  break;
               case 'C':
                  boatFound[1] = true;
                  break;
               case 'S':
                  boatFound[2] = true;
                  break;
               case 'B':
                  boatFound[3] = true;
                  break;
               case 'A':
                  boatFound[4] = true;
                  break;
            }
         }
      }
      
      int[] boatCount = new int[5];
      for(int i= 0; i<boatFound.length;i++){
         if (boatFound[i] == true){
            boatCount[i] = 1;
         }
         else{
            boatCount[i] = 0;
         }
      }
      return boatCount;
   }
   
}
