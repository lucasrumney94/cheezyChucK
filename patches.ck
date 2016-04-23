//sin wave to gain to dac
SinOsc s1 => Gain g;
SinOsc s2 => g;
g => dac;

440 => s1.freq;
441 => s2.freq;

//set gain to 0.5
0.5 => g.gain;

10::second => now;