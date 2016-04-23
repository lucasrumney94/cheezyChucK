//Create an OscSend object:
OscSend xmit;
//Set the host and port of this object:
xmit.setHost("localhost", 1234);


//To send a message with no contents:
//xmit.startMsg("conductor/downbeat");
//To send a message with one integer:
//xmit.startMsg("conductor/downbeat, i");
//To send a message with a float, an int, and another float:
//xmit.startMsg("conductor/downbeat, f, i, f");

xmit.startMsg("conductor/downbeat, i, f, s");


//to add an int:
xmit.addInt(10);
//to add a float:
xmit.addFloat(13.37);
//to add a string:
xmit.addString("Hello!");
