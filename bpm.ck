
20 => float bpm_factor;
(4.0*1/bpm_factor)::minute => dur next;

for (0 => int i; i < 5; i++)
{
    spork ~ bpmPlayer(Math.pow(2,i)*bpm_factor);
    next => now;
}
//Machine.remove(2);
next*3 => now;

Machine.remove(6);
next => now;
Machine.remove(5);
next => now;
Machine.remove(4);
next => now;
Machine.remove(3);



fun void bpmPlayer (float bpm_in)
{
    bpm_in => float bpm;

    SinOsc beep => Gain g => dac;
    0.08 => g.gain;

    (1/bpm) => float mpb;
    <<<mpb>>>;

    mpb::minute => dur quarter;
    quarter/2 => dur eighth;

    //spork ~ bpmPlayer(bpm*2);
    while(true)
    {
        440 => beep.freq;
        quarter => now;
        
        660 => beep.freq;
        quarter => now;
        
        330 => beep.freq;
        quarter => now;
        
        220 => beep.freq;
        quarter => now;
        
    }
    
}
