/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatroomclient;
import java.net.*;
import java.io.*;
import java.io.DataInputStream;
import java.util.StringTokenizer;
import java.util.*;

/**
*Mohammed Fahad Kaleem(1000969369)
*Nitin Kamani(1000919137)
*Sarabjeet Singh(1001115369)
*Mohammed Mudassir Ahmed(1001108922)
 */
public class P2PClient {
    public String name;
    public String IP;
    public int port;
    public P2PClient(String name,String ip,int port)
    {
        this.name=name;this.IP=ip;this.port=port;
    }
    public void sendMessage(String msg)
    {
        try
        {
            // connect the new client to the new server
            Socket client=new Socket(IP,port);
            DataOutputStream dos=new DataOutputStream(client.getOutputStream());
            dos.writeUTF(msg);
            client.close();
        }
        catch(Exception ae){
            System.out.println("Error in connecting. The Error is: "+ae);
        }
    }
}
