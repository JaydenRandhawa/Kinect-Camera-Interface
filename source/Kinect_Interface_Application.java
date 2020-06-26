import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import org.openkinect.freenect.*; 
import org.openkinect.freenect2.*; 
import org.openkinect.processing.*; 
import org.openkinect.tests.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Kinect_Interface_Application extends PApplet {






Kinect kinect;

int getKey;

int state;

String colour;

int brightX;
int brightY;
int brightLoc;

float r;
float g;
float b;

float targetR = 170;
float targetG = 170;
float targetB = 170;

public void setup() {
  
  kinect = new Kinect(this);
  kinect.initVideo();
  kinect.initDepth();
  //kinect.enableIR(true);
}

public void keyPressed() {
  if(keyCode == TAB){
   if (state == 0){
     kinect.enableIR(true);
     state = 1;
     //print("tab");
   }
   else if (state == 1){
     kinect.enableIR(false);
     state = 0;
     //print("tab");
    }
    }
}

public void mousePressed() {
  brightX = mouseX;
  brightY = mouseY;
  
  print(brightX, ",", brightY, "      ");
}
public void draw() {
  background(0);
  
  //tint(10,10,10);
  
  PImage img = kinect.getVideoImage();
  PImage imgDepth = kinect.getDepthImage();
  
  brightLoc = brightX + brightY * imgDepth.width;
  
  image(img, 0, 0, img.width, img.height);
  image(imgDepth, 0, 480, imgDepth.width, imgDepth.height);
  
  for(int x = 0; x < img.width; x += 2) {
    for(int y = 0; y < img.height; y += 2){
      for(int depthX = 0; depthX < imgDepth.width; depthX += 2) {
        for(int depthY = 0; depthY < imgDepth.height; depthY += 2){
      
      int index = x + y * img.width;
      int depthIndex = depthX + depthY * imgDepth.width;
      
      float bright = (brightness(img.pixels[index]));
      float brightDepth  = (brightness(imgDepth.pixels[depthIndex]));
      float brightLocDepth  = (brightness(imgDepth.pixels[brightLoc]));
      
      //print (bright);
       
       r = red(img.pixels[index]);
       g = green(img.pixels[index]);
       b = blue(img.pixels[index]);
       
      if(r > targetR && g > targetG && b > targetB && brightDepth == brightLocDepth){
          fill(r, g, b);
        rect(x + img.width, y, 2, 2);
      }
      else{
        fill(0);
        rect(x + img.width * 2, y, 2, 2);
          }
        }
      }
    }
  }
}
  public void settings() {  size(1280, 960); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Kinect_Interface_Application" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
