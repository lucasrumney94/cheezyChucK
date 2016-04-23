SinOsc s1 => dac.left;
SinOsc s2 => dac.right;

440 => s1.freq;
441 => s2.freq;

10::second=>now;