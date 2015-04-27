/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chatroomclient;

//import yahoomessage.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.Thread;

/**
*Mohammed Fahad Kaleem(1000969369)
*Nitin Kamani(1000919137)
*Sarabjeet Singh(1001115369)
*Mohammed Mudassir Ahmed(1001108922)
 */

class Server extends Thread
{
    ServerProcessing [] listClient;
    int index=0;
    int port;
    Client myClient;
    public Server(Client c,int port)
    {
        myClient=c;
        this.port=port;
    }
    
    public void run()
    {
        //loop and listren connnect from client
        listClient=new ServerProcessing[10];//the max number of client to connect
            try
            {
                    ServerSocket server=new ServerSocket(port);//sever listien on port port
                    System.out.println("Server is running....");
                    while(true)
                    {
                        Socket s=server.accept();
                        System.out.println("Welcome Client");
                        //if has a connect from client then create a Severprocessing process it
                        ServerProcessing pro=new ServerProcessing(s,this);
                        pro.start();
                    }

            }
            catch(Exception ae){
                    System.out.println(ae);
            }
    }
    public int getClient(String name)
    {
        //get processerserver to process client has name is name
        for(int i=0;i<index;i++)
        {

            if(listClient[i].name.trim().toLowerCase().equals(name.trim().toLowerCase())) return i;
        }
        return -1;
    }
    public void sendMessage(String address,String msg)
    {
        // send a msg to client with address
        int i=getClient(address);
        if(i>=0)
        {
            listClient[i].sendMessage(msg);
        }
    }   
    public  void sendMessageAll(String msg)
    {
        // send message to all clients
        for(int i=0;i<listClient.length;i++)
            listClient[i].sendMessage(msg);
    }
            
}
