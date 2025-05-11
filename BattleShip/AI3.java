public class AI3{

   // this class is for checking if a placement the computer takes into account is a valid move or not
   public static boolean possiblePlacement(int row, int column, char direction,int shipLength, int[][] board){
      
      boolean possible = true;
      //direction left
      if (direction == 'a'){
         for (int i = 0; i<shipLength; i++){
            if (column - i <0 || board[row][column -i] == -1){
               possible = false;
            }
         }
      }
      //direction down
      else if(direction == 's'){
         for (int i = 0; i<shipLength; i++){
            if(row + i > 9 || board[row +i][column] == -1){
               possible = false;
            }
         }
      }
      //direction up
      else if (direction == 'w'){
         for(int i = 0; i<shipLength; i++){
            if(row-i < 0 || board[row-i][column] == -1){
               possible = false;
            }
         }
      }
      //direction right
      else if (direction == 'd'){
         for(int i = 0; i<shipLength; i++){
            if(column + i>0 || board[row][column+1] == -1){
               possible = false;
            }
         }
      }
      return possible;
   }
   
   //this is the start of the algorithm
   
   public static int[] probability(int[][] board, int[] boatCount){
      
      final int boardLen = 10;
      final int certaintyFactor = 40;
      
      char direction;
      
      int[] shiplen = {2,3,3,4,5};
      int[] shipcount = boatCount;
      
      // if the boat is hit (denoted by -2),
      // all hits around it will be increased by the certainty factor
      
      for(int row = 0; row < boardLen; row++){
         for (int column = 0; column < boardLen; column ++){
            if (board[row][column] == -2){
               if(board[row+1][column] !=-1 && board[row+1][column] != -2){
                  board[row+1][column] = board[row+1][column] +certaintyFactor;
               }
               if (board[row-1][column] != -1 && board[row-1][column] != -2){
                  board[row-1][column] = board[row-1][column] +certaintyFactor;
               }
               if(board[row][column+1] != -1 && board[row][column+1] != -2){
                  board[row][column+1] = board[row][column+1] +certaintyFactor;
               }
               if(board[row][column-1] != -1 && board[row][column-1] != -2){
                  board[row][column-1] = board[row][column-1] +certaintyFactor;
               }
            }
         }
      }

      
      //this checks for every single square after and makes a judgement on the other squares
      
      for (int row = 0; row < boardLen; row++){
         for (int column = 0; column < boardLen; column++){
            
            
            // there are four directions a boat can be in: facing up, down, left and right
            for (int dirc = 0 ; dirc <4; dirc++){
               
               if (dirc == 0){
                  direction = 'w';
               }
               else if(dirc == 1){
                  direction = 'a';
               }
               else if(dirc == 2){
                  direction = 's';
               }
               else{
                  direction = 'd';
               }
               // big calc time
               // for each count of boats remaining, count that boat in the calculation
               //this is how the computer works:
               // for each square+ that a boat can be on, make each square's probability add by 1
               // that means if a 1x3 boat is possible on 3 squares, all 3 square's probably get added by 1
               for(int b = 0; b<boatCount.length; b++){
                  for (int c = 0; c<boatCount[b]; c++){
                     if (possiblePlacement(row, column, direction,shiplen[b], board)){
                        switch (direction){
                           case 'w':
                              for(int i = 0; i< shiplen[b]; i++){
                                 if (board[row-i][column] != -2){
                                    board[row-i][column] = board[row-i][column] +1;
                                 }
                              }
                              break;
                           case 'a':
                              for(int i = 0; i<shiplen[b]; i++){
                                 if (board[row][column-i] != -2){
                                    board[row][column-i] = board[row][column -i] + 1;
                                 }
                              }
                              break;
                           case 's':
                              for(int i = 0; i<shiplen[b]; i++){
                                 if (board[row+i][column] != -2){
                                    board[row+i][column] = board[row + i][column] +1;
                                 }
                              }
                              break;
                           case 'd':
                              for(int i = 0; i<shiplen[b]; i++){
                                 if(board[row][column + i] != -2){
                                    board [row][column+i] = board[row][column+i]+1;
                                 }
                              }
                             break;
                           }
                        }  
                     
                     }     
                  }
               }
         }  
      }

      
      
                
      // this checks for the best possible node
      int rBest = 0;
      int cBest = 0;
      for(int row = 0; row <boardLen; row ++){
         for(int column = 0; column <boardLen; column ++){
            if(board[row][column]>board[rBest][cBest]){
               rBest = row;
               cBest = column;
            }
         }
      }
      
      int[] move = {rBest,cBest};
        
      return move;
     
      }

   public static int[] ai3(char graph[][], int[] boatCount){
   
         // while the computer techincally uses the player's board,
         // its only allowed to read which squares are a hit or a miss
         final int boardLen = 10;
         int [][] probabilityGrid = new int[boardLen][boardLen];
         
         for (int r=0; r<boardLen;r++){
            for (int c=0; c<boardLen;c++){
               if (graph[r][c] == 'O'){
                  probabilityGrid[r][c] = -1;
               }
               else if(graph[r][c] == 'X'){
                  probabilityGrid[r][c] = -2;
               }
               else{
                  probabilityGrid[r][c] = 0;
               }
            }
         }
      /* all games start with:
         i     type: boat  num in set
         0:    1x2   boat  (1)
         1:    1x3   boat  (1)
         2:    1x3   boat  (1)
         2:    1x4   boat  (1)
         3:    1x5   boat  (1)
      */
      
      int[] answer = AI3.probability(probabilityGrid, boatCount);
      
      return answer;
   }
   
   public static int[] ai1(char[][] playerBoard){
      
      final int boardLen = 10;
      boolean validShot = false;
      // interpret's the player's board into something the computer can read (P.S its interpreted in a way so that the computer does not know anything besides hit or miss)
      char[][] computerBoard = ReaderAndWriter.fileInterpreter(playerBoard);
      
      int row = 0;
      int column = 0;
      while(validShot == false){
         row = (int)(Math.random()*boardLen);
         column = (int)(Math.random()*boardLen);
         
         if(computerBoard[row][column] == '-'){
            validShot = true;
         }
      }
      int[] answer = {row, column};
      return answer;
      
   }
   
}