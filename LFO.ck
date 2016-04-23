SinOsc s1 => dac;
SinOsc lfo => blackhole;


//set initial frquency of lfo
2 => lfo.freq;

while (true)
{
    .001::second => now;
    lfo.last()*100*(9/7) + 440 => s1.freq; 
}

