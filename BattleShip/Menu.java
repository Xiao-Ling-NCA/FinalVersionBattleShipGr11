/*
Chengfu Zhao
ICS3U1
June 16 2024
Is a menu, I wont comment some methods because it quite literally is too simplistic to comment
*/

import java.util.*;
import java.io.*;
public class Menu{
   

   //===============================================
   public static String computerDifficulty(){
      
      Scanner sc = new Scanner(System.in);
      int level;
      System.out.println("Select AI Difficulty");
      System.out.println("1 - Very Easy");
      System.out.println("2 - Normal");
      System.out.println("4 - Return to Menu");
      try{
         level = sc.nextInt();
         while((level >4 || level<1) && level == 3){
            System.out.println("AI level not a valid value");
            level = sc.nextInt();
         }
      }
      catch(Exception iox){
         System.out.println("Invalid Input");
         System.out.println("Select AI Difficulty");
         System.out.println("1 - Very Easy");
         System.out.println("2 - Normal");
         System.out.println("4 - Return to Menu");
         level = sc.nextInt();
      }
      
      String rLevel =""; // r stands for return
      switch(level){
         case 1: rLevel = "1";
         case 2: rLevel = "2";
         case 4: rLevel = "4";
      }
      
      return rLevel;
      
   }
   //====================================================
   public static String loadsAndSaves(){
      
      Scanner sc = new Scanner(System.in);
         
      System.out.println("Please Enter the File's Name (CaseSeNsItIvE):");
      System.out.println("Only the name of the file, DO NOT add the file format name (.txt)");
      System.out.println("4 - Exit");
      String load = sc.nextLine();
      if(load == "4"){
         Menu.menu();
      }
      return load;
   }
   //=======================================================
   
   public static void rules(){
      Scanner sc = new Scanner(System.in);
      
      System.out.println("Rules:");
      System.out.println("1. You have 5 Ships\n\t Destroyer, Submarine, Cruiser, Battleship, Aircraft Carrier");
      System.out.println("2. Take down the opponent's ship to win.");
      System.out.println("Game terminology:\nRow: row number\nColumn: column number\n");
      System.out.println("4 - to exit");
      
      try{
         int input = sc.nextInt();
         while(input != 4){
         }
         Menu.menu();
      }
      catch(Exception iox){
         System.out.println("Returning to menu");
      }
   }
   
   //=======================================================
   public static String[] menu(){
      
      // gamestart is when the player starts a new game thats why its called game start
      // game is when the player plays a game
      
      boolean game = false;
      boolean gameStart = false;
      
      Scanner sc = new Scanner(System.in);
      
      String computerLevel = "1";
      
      int choice;
      while (game == false){
      
         System.out.println("Welcome to battleship");
         System.out.println("1: Load an existing game");
         System.out.println("2: New Game");
         System.out.println("3: Set Computer Difficulty");
         System.out.println("4: Rules");
         System.out.println("5: Exit Game");
         
         choice = sc.nextInt();
         try{
            if (choice == 1){
               String fileSelection;
                  
               fileSelection = loadsAndSaves();
                     
               if (fileSelection.equals("4")){
                  continue;
               }
               String[] settings = {fileSelection,computerLevel,"0"};
               // settings: the file thats being accessed, the computer level, if file selection should occur
               return settings;
            }
            else if (choice == 2){
               // starts a new game by making the last string a "1"
               String[]settings = {null,computerLevel,"1"};
               return settings;
            }
            else if(choice == 3){
               computerLevel = computerDifficulty();
            }
            else if(choice == 4){
               rules();
            }
            else if (choice == 5){
               System.exit(0);
            }
            else{
               System.out.println("Invalid input");
            }
         }
         catch(Exception iox){
            System.out.println("Enter correct input");
            
         }  
         sc.nextLine();   
      }
      
      return null;
   }

}