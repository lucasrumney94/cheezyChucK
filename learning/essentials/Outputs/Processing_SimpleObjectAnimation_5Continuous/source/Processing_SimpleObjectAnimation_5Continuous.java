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

public class Processing_SimpleObjectAnimation_5Continuous extends PApplet {

//This demo allows wekinator to control x, y, size, hue, and rotation of an object
//All are continuous values between 0 and 1

//Necessary for OSC communication with Wekinator:


OscP5 oscP5;
NetAddress dest;

//Parameters of sketch
float myX, myY, mySize, myHue, myRot;
PFont myFont;

public void setup() {
  //Initialize OSC communication
  oscP5 = new OscP5(this,12000); //listen for OSC messages on port 12000 (Wekinator default)
  dest = new NetAddress("127.0.0.1",6448); //send messages back to Wekinator on port 6448, localhost (this machine) (default)
  
  colorMode(HSB);
  
  
  background(255);

  //Initialize appearance
  myX = 200;
  myY = 200;
  mySize = 50;
  myHue = 255;
  myRot = 0;
  sendOscNames();
  myFont = createFont("Arial", 14);
}

public void draw() {
  background(0);
  noStroke();
  fill(myHue, 255, 255);
  pushMatrix();
  translate(myX+mySize/2,myY+mySize/2);
  rotate(myRot);
  rect(-mySize/2, -mySize/2, mySize, mySize);
  popMatrix();
  drawtext();
}


//Update x, y position according to mouse click, send new parameters to wekinator
/*void mouseClicked() {
  myX= (float) mouseX;
  myY = (float) mouseY;
  sendOsc();
} */

//This is called automatically when OSC message is received
public void oscEvent(OscMessage theOscMessage) {
 if (theOscMessage.checkAddrPattern("/wek/outputs")==true) {
     if(theOscMessage.checkTypetag("fffff")) { // looking for 5 parameters
        float receivedX = theOscMessage.get(0).floatValue();
        float receivedY = theOscMessage.get(1).floatValue();
        float receivedSize = theOscMessage.get(2).floatValue();
        float receivedHue = theOscMessage.get(3).floatValue();
        float receivedRot = theOscMessage.get(4).floatValue();
        myX = map(receivedX, 0, 1, -mySize/2, width-mySize/2);
        myY = map(receivedY, 0, 1, -mySize/2, height-mySize/2);
        mySize = map(receivedSize, 0, 1, 0, 400);
        myHue = map(receivedHue, 0, 1, 0, 255);
        myRot = map(receivedRot, 0, 1, 0, TWO_PI); 
        
       // println("Received new output values from Wekinator");  
      } else {
        println("Error: unexpected OSC message received by Processing: ");
        theOscMessage.print();
      }
 }
}

//Sends current parameter (hue) to Wekinator
public void sendOscNames() {
  OscMessage msg = new OscMessage("/wekinator/control/setOutputNames");
  msg.add("X"); //Now send all 5 names
  msg.add("Y");
  msg.add("Size");
  msg.add("Hue");
  msg.add("Rotation");
  oscP5.send(msg, dest);
}

//Write instructions to screen.
public void drawtext() {
    stroke(0);
    textFont(myFont);
    textAlign(LEFT, TOP); 
    fill(0, 0, 255);

    text("Listening for message /wek/inputs on port 12000", 10, 10);
    text("Expecting 5 continuous numeric outputs, all in range 0 to 1:", 10, 25);
    text("   x, y, size, hue, rotation" , 10, 40);
}
  public void settings() {  size(400,400, P3D);  smooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Processing_SimpleObjectAnimation_5Continuous" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
