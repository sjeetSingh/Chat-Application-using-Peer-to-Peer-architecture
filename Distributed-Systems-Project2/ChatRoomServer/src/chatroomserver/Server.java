/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chatroomserver;

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
    public DriverDatabase database;
    public ListRoomChat listRoom;
    int index=0;
	public void run()
	{
            //loop and listren connnect from client
            // This function listens for client(s) waiting to establish a connection with the client
            listClient=new ServerProcessing[10];//the max number of client to connect
		try
		{
			ServerSocket server=new ServerSocket(8080);//sever listening on port 8080
			System.out.println("Server is running....");
			while(true)
			{
                            Socket s=server.accept();
                            System.out.println("Welcome Client");

                            // If a client has established a connection, then a new instance of ServerProcessing object is created
                            ServerProcessing pro=new ServerProcessing(s,this);
                            if(index<listClient.length)
                            {
                                listClient[index]=pro;
                                listClient[index].start();
                                index++;
                            }
			}
			
		}
		catch(Exception ae){
			System.out.println(ae);
		}
	}
        public int getClient(String name)
        {
            //get processerserver to process client 
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
        public int getPortfree()
        {
            int port =0;
            for(int i=0;i<index;i++)
            {
                if(listClient[i].getCurPort()>port)port=listClient[i].getCurPort();
                if(listClient[i].getNewPort()>port)port=listClient[i].getNewPort();
            }
            port++;
            return port;
        }
        public  void sendMessageAll(String msg)
        {
            // send message to all clients
            for(int i=0;i<listClient.length;i++)
                listClient[i].sendMessage(msg);
        }
        public static void main(String[] args)
        {
            try
            {
                DriverDatabase database=new DriverDatabase("127.0.0.1","chatroom", "root", "admin");
                Server s=new Server();
                s.database=database;
                s.listRoom=new ListRoomChat();
                s.start();
            }
            catch(Exception ae){}
        }
        
}
