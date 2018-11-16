import java.applet.Applet;
import java.awt.*;
public class numberUnit extends Rectangle{
  public int value;
  public boolean locked;
  public numberUnit(int x,int y,int w,int h,int val)
  {
    super(x,y,w,h);
    value = val;
    locked = false;
  }
  
  public void drawUnit(Graphics g,int x,int y,int w,int h)
  {
    if(value == 0)
      Expo.setColor(g,81,141,187);
    if(value == 2)
      Expo.setColor(g,165,207,244);
    if(value == 4)
      Expo.setColor(g,168,139,124);
    if(value == 8)
      Expo.setColor(g,169,19,20);
    if(value == 16)
      Expo.setColor(g,245,31,31);
    if(value == 32)
      Expo.setColor(g,141,170,157);
    if(value == 64)
      Expo.setColor(g,82,43,71);
    if(value == 128)
      Expo.setColor(g,63,73,54);
    if(value == 256)
      Expo.setColor(g,165,255,151);
    if(value == 512)
      Expo.setColor(g,25,11,40);
    if(value == 1024)
      Expo.setColor(g,30,63,32);
    if(value == 2048)
      Expo.setColor(g,224,162,37);
    if(value == 4096)
      Expo.setColor(g,201,201,201);
    if(value == 8192)
      Expo.setColor(g,Expo.black);
    Expo.fillRectangle(g,x,y,x+w,y+h);
    Expo.setColor(g,Expo.white);
    int offset = 1;
    int tempVal = value;
    while (tempVal/10 != 0) 
    { 
      offset++;
      tempVal/=10;
    } 
    if(value>0)
      Expo.drawString(g,""+value,x+w/2 - 5*offset,y+h/2 + 5);
  }      
  
}