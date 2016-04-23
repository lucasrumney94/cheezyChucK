//create MidiIn Source
MidiIn min;
min.open(0) => int minOpen;
MidiOut mout;
mout.open(0) => int moutOpen;

if (!minOpen || !moutOpen)
{
    me.exit();
}


//Check for Midi Input
while (true)
{
    MidiMsg msg;
    min => now;
    while(min.recv(msg))
    {
        <<<msg.data1,msg.data2,msg.data3,"MIDI MESSAGE!">>>;
    }
}