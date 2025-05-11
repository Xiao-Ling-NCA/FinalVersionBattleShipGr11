/*
Chengfu Zhao
ICS3U1
June 16, 2024
Reads and Writes boards and interprets them to be made for each player's hitboard.
*/

import java.io.*;
public class ReaderAndWriter{
   public static char[][][] reader(String file){
      
      final int boardLen = 10;
      final int totalBoards = 4;
      
      char[][][] boards = new char[3][boardLen][boardLen]; // 3 boards, interpreter is in gameruntime
      
      try{
         BufferedReader input = new BufferedReader(new FileReader(file+".txt"));//finds the file
         for (int t = 0; t<totalBoards; t = t+2){
            for(int r = 0; r<boardLen; r++){
               String text = input.readLine();//reads each line but makes sure that each line is in its right place
               char[] boardConvert = text.toCharArray();
               for(int c = 0; c<boardLen;c++){
                  boards[t][r][c] = boardConvert[c];//puts correct notation in correct square
               }
            }
         }
         
         boards[1] = fileInterpreter(boards[2]); // then sends an interpreter the board for the player and it gets a hitBoard for the player.
         
      }
      catch(IOException iox){
         System.out.println("Error Reading to File, a file Reset is needed (Manual Action)");
      }
      return boards;
   }
   
   public static char[][] fileInterpreter(char[][] graph){ // the interpreter to change from board to a hitboard
      final int boardLen = 10;
      char[][] transposedBoard = new char[boardLen][boardLen];// creates new board
      
      for (int r = 0; r<boardLen; r++){
        for (int c = 0; c<boardLen; c++){ // for each square
            
            if (graph[r][c] == 'O'){ // only transposes misses "O"
               transposedBoard[r][c] ='O';
            }
            else if(graph[r][c] == 'X'){
               transposedBoard [r][c] = 'X';// transposes hits "X"
            }
            else{
               transposedBoard[r][c] = '-'; // and everything else is a missing square
            } 
         } 
      }
     
      return transposedBoard;// returns board
      
   }
   
   public static void fileSaver(String file, char[][] playerGraph, char[][] AIGraph,String AI_Level){ // saving a level
      
      final int boardLen = 10;
      
      try{
         BufferedWriter output = new BufferedWriter(new FileWriter((file+".txt")));// writes to a file
         for(int r = 0; r<boardLen; r++){
            for(int c = 0; c<boardLen; c++){// for each square
               output.write(playerGraph[r][c]);// write out the square
            }
            output.newLine();// then creates a new line after each row
         }
         for(int r = 0; r<boardLen; r++){//for each square
            for(int c = 0; c<boardLen; c++){
               output.write(AIGraph[r][c]);//write out the square
            }
            output.newLine();//create a new line after each row
         }
         
         output.close(); // closes writer so my file can actually save.
      } 
      
      catch(IOException iox){
         System.out.println("Please select another file, this file may not exist");
      }
   
   }
   
}