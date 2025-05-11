/*
Chengfu Zhao
ICS3U1
June 16 2024
Responsible for displaying and putting the game's logic together
*/


import java.util.*;
public class GameRunTime{
   
   public static void main(String[] args){
         
         final int boardLen = 10;
         final int boatNum = 5;
         
         Scanner sc = new Scanner(System.in);                   
         //int[] settings = {saves,computerLevel,newSave}; in Menu.menu
         // where saves = the save number, computerLevel is the AI Level, newSave as if its a new game                  
         String[] settings = Menu.menu();
         char[][] playerBoard = new char[boardLen][boardLen]; 
         char[][] computerBoard = new char[boardLen][boardLen];
         char[][] playerHitBoard = new char [boardLen][boardLen];
         
         // im gonna be totally honest, a 3D array was never planned, 
         //but it was the easiest to code in my opinion so I just left it like this
         char[][][] gameBoards = new char[3][10][10];
         
         
         // 1 means true
         if (settings[2].equals("1")){
            // three boards (AI Ship board will be in a seperate list of list)
            playerBoard = BattleShipRunTime.playerBoardStarting();
            computerBoard = BattleShipRunTime.computerBoardStarting();
            
            for(int r = 0; r<boardLen; r++){
               for(int c=0; c<boardLen; c++){
                  playerHitBoard[r][c] = '-';
               }
            }
         }
         else if(settings[2].equals("0")){
            gameBoards = ReaderAndWriter.reader(settings[0]);
            playerBoard = gameBoards[0];
            computerBoard = gameBoards[2];
            playerHitBoard = gameBoards[1];
         }
         
         String [] playerLossBoats = new String[boatNum];
         String [] computerLossBoats = new String[boatNum];
         //X_LossCount is how many boats a player has lost
         int playerLossCount = 0;
         int computerLossCount = 0;
         
         String[] boatName = {"Destroyer","Cruiser","Submarine","Battleship","Aircraft Carrier"};
         
         boolean AILoss = false;
         boolean playerLoss = false;
         
         while(AILoss == false && playerLoss == false){
            
            
            // player's turn
            System.out.println("\t1 2 3 4 5 6 7 8 9 10|\t1 2 3 4 5 6 7 8 9 10");
            for(int r = 0; r < boardLen; r++){
               System.out.print((r+1)+"\t");
               for(int c = 0; c <boardLen; c++){
                  System.out.print(playerBoard[r][c]+" ");
               }
               System.out.print("|\t");
               for(int c = 0; c <boardLen; c++){
               
                  System.out.print(playerHitBoard[r][c]+" ");
               }
               
               System.out.println();
            }
            
            for(int r = 0; r<boardLen; r++){
               for(int c = 0; c<boardLen; c++){
                  System.out.print(computerBoard[r][c]+"\t");
               }
               System.out.print("\n");
            }
            
            //Player chooses square to shoot or save or quit;
            
            System.out.println("1 - Shoot");
            System.out.println("2- Save and Quit");
            System.out.println("3 - Surrender");
            
            int choice = sc.nextInt();
            if (choice == 1){
               System.out.println("Row");
               int row = sc.nextInt()-1;
               System.out.println("Column");
               int column = sc.nextInt()-1;
               
               while(row <0 || row >10 || column<0 || column >10){
                  System.out.println("Please enter a valid shot");
                  System.out.println("Row");
                  row = sc.nextInt()-1;
                  System.out.println("Column");
                  column = sc.nextInt()-1;
                  // finish this for the top
                  for(int r = 0; r < boardLen; r++){
                     System.out.print(r+"\t");
                     for(int c = 0; c <boardLen; c++){
                        System.out.print(playerBoard[r][c]+"\t");
                     }
                     System.out.print("\t");
                     for(int c = 0; c <boardLen; c++){
                        System.out.print(playerHitBoard[r][c]+" ");
                     }
                     System.out.println();
                  }
                  
               }
               
               System.out.println("Computer is thinking...");
            
               //This counts how many boats each player has
               // this will be reused to save time and thats why its in a list and its repeated at the bottom
               int[] playerBoatCount = BattleShipRunTime.boatCounting(playerBoard);
               int[] AIBoatCount = BattleShipRunTime.boatCounting(computerBoard);
               
               
               //if the known amount of boats lost is less than the counted, then check for the boat thats missing
               for (int i = 0; i< playerBoatCount.length; i++){
                  boolean isIn = false;
                  if (playerBoatCount[i]==0){
                        for(int b = 0; b<playerLossBoats.length;b++){
                        if(playerLossBoats[b] == boatName[i]){
                           isIn = true;
                        }
                     } 
                     if (isIn == false){
                        System.out.println("Player Lost a " + boatName[i]);
                           playerLossCount = playerLossCount +1;
                        playerLossBoats[playerLossCount-1] = boatName[i];
                     }
                  }
               }  
               
                  
               for(int i=0; i<AIBoatCount.length;i++){
                  boolean isIn = false;
                  if (AIBoatCount[i]==0){
                     for(int b = 0; b<computerLossBoats.length;b++){
                           if(computerLossBoats[b] == boatName[i]){
                           isIn = true;
                        }
                     }
                     if (isIn == false){
                        System.out.println("Computer Lost a " + boatName[i]);
                           computerLossCount = computerLossCount +1;
                        computerLossBoats[computerLossCount-1] = boatName[i];
                     }
   
                  }
                  
               }
               
               
               
               int playerCount = 0;
               int computerCount = 0;
               for(int i = 0; i<playerBoatCount.length; i++){
                  playerCount = playerCount + playerBoatCount[i];
                  }
               for(int i = 0; i<AIBoatCount.length; i++){
                     computerCount = computerCount + AIBoatCount[i];
               }
               
               //Count how many boat each player has
               //Sees if anyone has won
               if (playerCount == 0 || computerCount == 0){
                  if (computerCount == 0){
                     System.out.println("Player wins. W Game");
                     AILoss = true;
                  }
                  else if(playerCount == 0){
                     System.out.println("Computer Wins. L Game");
                     playerLoss = true;
                  }
               }
               else{
                  int[] computershot= new int[2];
                  if (settings[1] == "1"){
                     computershot = AI3.ai1(playerBoard);
                  }
                  else if ((settings[1]) == "2"){
                     computershot = AI3.ai3(playerBoard,playerBoatCount);
                  }
                  
                  if(playerBoard[computershot[0]][computershot[1]] != '-'){
                     playerBoard[computershot[0]][computershot[1]] = 'X';
                  }
                  else{
                     playerBoard[computershot[0]][computershot[1]] = 'O';
                  }
                  
                  // checks if the players have lost any boat
                  
                  playerBoatCount = BattleShipRunTime.boatCounting(playerBoard);
                  AIBoatCount = BattleShipRunTime.boatCounting(computerBoard);
                  for(int i=0; i<playerBoatCount.length;i++){
                     boolean isIn = false;
                     if (playerBoatCount[i]==0){
                        for(int b = 0; b<playerLossBoats.length;b++){
                           if(playerLossBoats[b] == boatName[i]){
                              isIn = true;
                           }
                        }
                        if (isIn == false){
                           System.out.println("Player Lost a " + boatName[i]);
                           playerLossCount = playerLossCount + 1;
                           playerLossBoats[playerLossCount-1] = boatName[i];
                        }
   
                     }
                     
                  }
                  
                  for(int i=0; i<AIBoatCount.length;i++){
                     boolean isIn = false;
                     if (AIBoatCount[i]==0){
                        for(int b = 0; b<computerLossBoats.length;b++){
                           if(computerLossBoats[b] == boatName[i]){
                              isIn = true;
                           }
                        }
                        if (isIn == false){
                           System.out.println("Computer Lost a " + boatName[i]);
                           computerLossBoats[computerLossCount-1] = boatName[i];
                        }
   
                     }
                     
                  }
                  
                  //Count how many boat each player has
                  //Sees if anyone has won
                  if (playerCount == 0 || computerCount == 0){
                     if (computerCount == 0){
                        System.out.println("Player wins. W Game");
                     }
                     else if(playerCount == 0){
                        System.out.println("Computer Wins. L Game");
                     }
                  }
               }
                  
                  // player shoots a shot
                  char check = BattleShipRunTime.checkHit(row,column,playerHitBoard,computerBoard);
                  
                  while (check == 'M'){
                     System.out.println("Please choose a valid Shot");
                     System.out.println("Row");
                     row = sc.nextInt()-1;
                     System.out.println("Column");
                     column = sc.nextInt()-1;
                     check = BattleShipRunTime.checkHit(row,column,playerHitBoard,computerBoard);
                  }
                  playerHitBoard[row][column] = check;
                  computerBoard[row][column] = check;
               }
               
               
            else if(choice == 2){
               System.out.println("Which file do you want to save this game to?");
               sc.nextLine();
               String file = sc.nextLine();
               try{
                  ReaderAndWriter.fileSaver(file,playerBoard,computerBoard,settings[1]);
               }
               catch(Exception e){
                  System.out.println("Something went wrong :/");
               }
            }
            else if (choice == 3){
               System.out.println("The computer wins");
               playerLoss = true;
            }
            
                        
         }
         
         
         System.out.println("Computer's Board:");
         System.out.println();
         System.out.println("\t1\t2\t3\t4\t5\t6\t7\t8\t9\t10");
         for(int r = 0; r < boardLen; r++){
            System.out.print((r+1)+"\t");
            for(int c = 0; c <boardLen; c++){
               System.out.print(playerBoard[r][c]+"\t");
            }
            System.out.print("\n");
         }
         System.out.println("Press Enter to return to main menu");
         sc.nextLine();
         Menu.menu();
   }
   
}