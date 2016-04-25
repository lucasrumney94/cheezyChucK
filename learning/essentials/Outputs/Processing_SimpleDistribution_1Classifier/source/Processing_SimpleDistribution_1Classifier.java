import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import oscP5.*; 
import netP5.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Processing_SimpleDistribution_1Classifier extends PApplet {

//This demo shows probability distribution coming from Wekinator
// Works with 1 output 
// Set number of classes below (default 5) and osc message name (default /outputs-1)

//Necessary for OSC communication with Wekinator:


OscP5 oscP5;
NetAddress dest;

//TODO: SET THESE VALUES!
int numClasses = 5;
String messageName = "/outputs-1";
String typeTag = "f"; 

//no need to edit below:
float[] dist = new float[numClasses];

PFont myFont;
final int myHeight = 400;
final int myWidth = 400;
int frameNum = 0;

public void setup() {
    

  //typeTag = "ffffff";
  String t = "f";
  for (int i = 0; i < numClasses; i++) {
     t = t + "f";
  } 
  typeTag = t;
  
  
  //Initialize OSC communication
  oscP5 = new OscP5(this,12000); //listen for OSC messages on port 12000 (Wekinator default)
  dest = new NetAddress("127.0.0.1",6448); //send messages back to Wekinator on port 6448, localhost (this machine) (default)
  
  colorMode(HSB);
  
  background(255);
  
 /* String typeTag = "f";
  for (int i = 0; i < numClasses; i++) {
    typeTag += "f";
  } */
  myFont = createFont("Arial", 14);
}

public void draw() {
  frameRate(30);
  background(0);
  drawText();
  drawDist();

}

//This is called automatically when OSC message is received
public void oscEvent(OscMessage theOscMessage) {
 // println("received message");
 if (theOscMessage.checkAddrPattern("/outputs-1")==true) {
     if(theOscMessage.checkTypetag(typeTag)) { // looking for numClasses values
        for (int i = 0; i < numClasses; i++) {
           dist[i] = theOscMessage.get(i+1).floatValue(); 
        }
      } else {
        println("Error: unexpected OSC message received by Processing: ");
        theOscMessage.print();
      }
 }
}


//Write instructions to screen.
public void drawText() {
    stroke(0);
    textFont(myFont);
    textAlign(LEFT, TOP); 
    fill(0, 0, 255);

    text("Receives probability distribution from Wekinator", 10, 10);
   // String s= 
   text("Listening for " + numClasses + " classes, OSC message " + messageName, 10, 30);
   text("(You will have to turn this message on:\n edit model output type in Wekinator)", 10, 50); 
}

public void drawDist() {
  int rectWidth = myWidth / numClasses;
  for (int i = 0 ; i < numClasses; i++) {
   println("dist[i] for i=" + i + "," + dist[i]);
     float thisHeight = map(dist[i], 0, 1, 0, (myHeight - 40));
     stroke(0, 255, 255);
     fill(255, 255, 255);
     rect(rectWidth * (i), myHeight-thisHeight, rectWidth, thisHeight);

  }
   
}
  public void settings() {  size(400,400, P3D);  smooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Processing_SimpleDistribution_1Classifier" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
