//Joseph Bain
import java.applet.Applet;
import java.awt.*;

public class 2048 extends Applet{
  numberUnit [] [] gameUnits ;
  numberUnit [] [] prevTurnUnits ;
  int swipeX;
  int swipeY;
  Rectangle up;
  Rectangle down;
  Rectangle left;
  Rectangle right;
  Rectangle restart;
  Rectangle undo;
  int score;
  int prevScore;
  boolean gameStarted  = false;
  Rectangle menuStart = new Rectangle(325,350,150,100);
  boolean gameOver;
  public void init(){
    gameUnits = new numberUnit[4][4];
    prevTurnUnits = new numberUnit[4][4];
    int xPos=150;
    int yPos;
    score = 0;
    gameOver = false;
    for(int c = 0;c<4;c++)
    {
      yPos = 150;
      for(int r=0;r<4;r++)
      {
        gameUnits[c][r] = new numberUnit(xPos,yPos,100,100,0);
        prevTurnUnits[c][r] = new numberUnit(xPos,yPos,100,100,0);
        yPos+=110;
      }
      xPos+=110;
    }
    
    for(int i =0;i<2;i++)
    {
      int rndC = Expo.random(0,3);
      int rndR = Expo.random(0,3);
      if(gameUnits[rndC][rndR].value ==0){gameUnits[rndC][rndR].value = 2; prevTurnUnits[rndC][rndR].value = 2;}
      else i--;
        
    }
    
    
    up = new Rectangle(335,60,65,60);
    down = new Rectangle(335,600,65,60);
    left = new Rectangle(60,335,60,65);
    right = new Rectangle(600,335,60,65); 
    restart = new Rectangle(0,0,100,30);
    undo = new Rectangle(700,0,100,30);
    
  }
    
  public void paint(Graphics g){
    if(!gameStarted){
      Expo.setFont(g,"Arial",Font.BOLD,50);
      Expo.setColor(g,165,207,244);
      Expo.drawString(g,"2",315,265);
      Expo.setColor(g,81,141,187);
      Expo.drawString(g,"0",365,265);
      Expo.setColor(g,168,139,124);
      Expo.drawString(g,"4",415,265);
      Expo.setColor(g,169,19,20);
      Expo.drawString(g,"8",465,265);
      
      Expo.setColor(g,Expo.gray);
      Expo.fillRectangle(g,325,350,325+150,350+100);
      Expo.setColor(g,Expo.white);
      Expo.drawString(g,"Start",340,415);
    }
    if(gameStarted){playGame(g);}
  }   
  
  public void playGame(Graphics g){
    Expo.setFont(g,"Arial",Font.BOLD,20);
    int xPos=150;
    int yPos;
    Expo.setColor(g,Expo.gray);
    Expo.fillRectangle(g,0,0,100,30);
    Expo.fillRectangle(g,700,0,800,30);
    Expo.setColor(g,Expo.white);
    Expo.drawString(g,"RESTART",3,22);
    Expo.drawString(g,"UNDO",703,22);
    Expo.setColor(g,Expo.gray);
    for(int c = 0;c<4;c++)
    {                                        
      yPos = 150;
      for(int r=0;r<4;r++)
      {
        Expo.setColor(g,Expo.gray);
        Expo.fillRectangle(g,xPos,yPos,xPos+102,yPos+102);
        
        gameUnits[c][r].drawUnit(g,xPos+11,yPos+11,80,80);
        yPos+=110;
      }
      xPos+=110;
    }
    
    Expo.setColor(g,Expo.lightBlue);
    Expo.fillRectangle(g,335,60,400,120);
    Expo.fillRectangle(g,335,600,400,660);
    Expo.fillRectangle(g,60,335,120,400);
    Expo.fillRectangle(g,600,335,660,400);
    
    Expo.drawString(g,"SCORE: "+score,550,100);
    
    if(loseCheck() == true)
    {
      Expo.setFont(g,"Arial",Font.BOLD,50);
      Expo.setColor(g,Expo.black);
      Expo.drawString(g,"Game Over",230,375);
    }
  } 
  
  
  
  
    
  public boolean mouseDown(Event e,int x,int y)
  {
    if(menuStart.inside(x,y)){gameStarted = true;}
    if(up.inside(x,y)){shiftUp();}
    if(down.inside(x,y)){shiftDown();}
    if(left.inside(x,y)){shiftLeft();}
    if(right.inside(x,y)){shiftRight();}
    if(restart.inside(x,y)){init();}
    if(undo.inside(x,y)){undoMove();} 
    swipeX = x;
    swipeY = y;
    for(int c = 0;c<4;c++)
    {
      for(int r=0;r<4;r++)
      {
        gameUnits[c][r].locked = false;
      }
    }
    
    repaint();
    return true;
  }
  
  public boolean mouseUp(Event e, int x, int y)
  {
    int xDiff = swipeX-x;
    int yDiff = swipeY-y;
    if(Math.abs(xDiff)>Math.abs(yDiff))
    {
      if(xDiff<-100){shiftRight();}
      if(xDiff>100){shiftLeft();}  
    }
    
    if(Math.abs(yDiff)>Math.abs(xDiff))
    {
      if(yDiff<-100){shiftDown();}
      if(yDiff>100){shiftUp();}  
    }
    
    
    repaint();
    return true;
  }
    
    
    
  public void shiftUp()
  { 
    boolean moved = false;
    saveMove();
    for(int i =0;i<4;i++)
    {
      for(int c =0;c<4;c++)
      {
        for(int r=1;r<4;r++)
        {
          if((gameUnits[c][r-1].value==0 || gameUnits[c][r-1].value==gameUnits[c][r].value) && gameUnits[c][r].value!=0 && (gameUnits[c][r-1].locked == false && gameUnits[c][r].locked == false))
          {
            
            merge(gameUnits[c][r],gameUnits[c][r-1]);
            moved = true;
          }
          
        }
      }
    } 
    if(moved) 
      generateNewUnit(); 
  }
    
  public void shiftDown()
  { 
    boolean moved = false;
    saveMove();
    for(int i =0;i<4;i++)
    {
      
      for(int c =0;c<4;c++)
      {
        for(int r=2;r>=0;r--)
        {
          if((gameUnits[c][r+1].value==0 || gameUnits[c][r+1].value==gameUnits[c][r].value) && gameUnits[c][r].value!=0 && (gameUnits[c][r+1].locked == false && gameUnits[c][r].locked == false))
          {
            
            merge(gameUnits[c][r],gameUnits[c][r+1]);
            moved = true;
            
          }
          
        }
      }
    }
    if(moved)
      generateNewUnit();
  }  
  public void shiftLeft()
  {  
    boolean moved = false;
    saveMove();
    for(int i =0;i<4;i++)
    {
      for(int c =1;c<4;c++)
      {
        for(int r=0;r<4;r++)
        {
          if((gameUnits[c-1][r].value==0 || gameUnits[c-1][r].value==gameUnits[c][r].value) && gameUnits[c][r].value!=0 && (gameUnits[c-1][r].locked == false && gameUnits[c][r].locked == false))
          {
            
            merge(gameUnits[c][r],gameUnits[c-1][r]);
            moved = true;
          }
          
        }
      }
    }
    if(moved)
      generateNewUnit();
  }
    
  public void shiftRight()
  {  
    boolean moved = false;
    saveMove();
    for(int i =0;i<4;i++)
    {
      for(int c =2;c>=0;c--)
      {
        for(int r=0;r<4;r++)
        {
          if((gameUnits[c+1][r].value==0 || gameUnits[c+1][r].value==gameUnits[c][r].value) && gameUnits[c][r].value!=0 && (gameUnits[c+1][r].locked == false && gameUnits[c][r].locked == false) )
          {
            
            merge(gameUnits[c][r],gameUnits[c+1][r]);
            moved = true;
          }
          
        }
      }
    }
    if(moved)
      generateNewUnit(); 
  }
    
  public void saveMove(){
    prevScore = score;
    for(int c = 0; c<4; c++){
      for(int r = 0; r<4; r++){
        prevTurnUnits[c][r].value = gameUnits[c][r].value; 
      }
    }
  }
    
  public void undoMove(){
    score = prevScore;
    for(int c = 0; c<4; c++){
      for(int r = 0; r<4; r++){
        gameUnits[c][r].value = prevTurnUnits[c][r].value; 
      }
    }
  }
    
    
    
  public void merge(numberUnit f, numberUnit s)
  {
    s.value+=f.value;
    
    if(s.value>0 && s.value!=f.value)
    {
      s.locked = true;
      score+=s.value;
    }
    f.value = 0;
    
  } 
    
  public void generateNewUnit()
  {
    int rndVal = Expo.random(0,99);
    if(rndVal <= 9)
    {
      rndVal = 4;
    }
    else 
      rndVal = 2;
      
      
    for(int i =0;i<1;i++)
    {
      int rndC = Expo.random(0,3);
      int rndR = Expo.random(0,3);
      if(gameUnits[rndC][rndR].value ==0){gameUnits[rndC][rndR].value = rndVal;}
      else i--;
        
    }
  }
    
    
    
  public boolean loseCheck()
  {
    boolean lost = true;
    for(int c = 0; c<4;c++)
    {
      for(int r = 0; r<3;r++)
      {
        if(gameUnits[c][r].value == gameUnits[c][r+1].value || gameUnits[c][r].value == 0)
        {
          lost = false;
        }
      }
    }
    
    for(int r = 0; r<4;r++)
    {
      for(int c = 0; c<3;c++)
      {
        if(gameUnits[c][r].value == gameUnits[c+1][r].value || gameUnits[c][r].value == 0)
        {
          lost = false;
        }
      }
    }
    return lost;
  }
}