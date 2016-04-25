using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class OSCReceiver : MonoBehaviour
{
    //You can set these variables in the scene because they are public 
    public string RemoteIP   = "127.0.0.1";
    public int SendToPort  = 6448;
    public int ListenerPort = 12000;
    public  Transform controller; 
    private Osc handler;
    private float sig1  = 0.0f;
    private float sig2  = 0.0f;


    void Start()
    {
        //Initializes on start up to listen for messages
        //make sure this game object has both UDPPackIO and OSC script attached

        UDPPacketIO udp = this.GetComponent<UDPPacketIO>();
        udp.init(RemoteIP, SendToPort, ListenerPort);
        handler = this.GetComponent<Osc>();
        handler.init(udp);


        //Tell Unity to call function Example1 when message /wek/outputs arrives
        handler.SetAddressHandler("/wek/outputs", Example1);
        Debug.Log("OSC Running");
    }


    //Use the values from OSC to do stuff
    void Update()
    {
        var go = GameObject.Find("Cube1");
        go.transform.position = new Vector3(5*sig1, 5*sig2, 0.0f);
    }

    //This is called when /wek/outputs arrives, since this is what's specified in Start()
    void Example1(OscMessage oscMessage)
    {	
	
	    Debug.Log("Called Example One > " + Osc.OscMessageToString(oscMessage));
	    Debug.Log("Message Values > " + oscMessage.Values[0] + " " + oscMessage.Values[1]);
	    sig1 = (float)oscMessage.Values[0];
	    sig2 = (float)oscMessage.Values[1];

	
    }
} 
