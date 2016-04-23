//sin wave to gain to dac
SinOsc s1 => PRCRev g;
SinOsc s2 => g;
g => dac;

500 => s1.freq;
500 => s2.freq;

//set gain to 0.5
0.5 => g.gain => g.mix;

1::second => now;