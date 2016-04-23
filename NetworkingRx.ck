//Create an OscRecv object:
OscRecv orec;
//Tell the OscRecv object the port:
1234 => orec.port;
while (true)
{
        
    //Tell the OscRecv object to start listening for OSC messages on that port:
    orec.listen();

    orec.event("conductor/downbeat, i,f,s") @=> OscEvent myDownBeat;

    myDownBeat => now;

    while (myDownBeat.nextMsg() != 0)
    {
        //process message here (no need to call nextMsg again
        myDownBeat.getInt() => int i;
        myDownBeat.getFloat() => float f;
        myDownBeat.getString() => string s;
        <<<"Received: ", i, f, s>>>;
    }
}