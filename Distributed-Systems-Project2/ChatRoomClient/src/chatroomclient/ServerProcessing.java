
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chatroomclient;

/**
*Mohammed Fahad Kaleem(1000969369)
*Nitin Kamani(1000919137)
*Sarabjeet Singh(1001115369)
*Mohammed Mudassir Ahmed(1001108922)
 */
import java.net.*;
import java.io.*;
import java.util.*;
class ServerProcessing extends Thread
{
    Socket server;
    public String name;
    public String IP;
    public int port;
    DataOutputStream dos;
	DataInputStream dis;
        Server myserver;
        
	public ServerProcessing(Socket s,Server ss)
	{
            IP=    s.getInetAddress().getHostName();
            port=s.getPort();
            
		server=s;
                myserver=ss;
	}
       
	public void run()
	{
		try
		{
			dos=new DataOutputStream(server.getOutputStream());
			dis=new DataInputStream(server.getInputStream());
			
			while(true)
			{
                            try
                            {
                                String st= dis.readUTF();
                                System.out.println("Message from client: "+st);
                                processMessage(st);
                            }
                            catch(Exception ae)
                            {
                                continue;
                            }
				
			}
		}
		catch(Exception ae){
                System.out.println("Error message:"+ae );}
	}
        public void sendMessage(String msg)
        {
            try
            {
            	
                dos.writeUTF(msg);
                
            }
           catch(IOException ae)
           {
                System.out.println("Error message:"+ae );
           }
        }
        
    public void processMessage(String msg)
    {
        StringTokenizer strTokToken =
            new StringTokenizer(msg, MessageChat.dotMsg, false);
        String clientName=strTokToken.nextToken();
        String command=strTokToken.nextToken();
        String roomID=strTokToken.nextToken();
        String strmsg=strTokToken.nextToken();
        
        if(this.name==null) this.name=clientName;
        
        if(command.equalsIgnoreCase(MessageChat.Logout))
        {
            String message=clientName+MessageChat.dotMsg+MessageChat.Logout+MessageChat.dotMsg+
                    "0"+MessageChat.dotMsg+clientName;
            myserver.sendMessageAll(message);
        }
        
        if(command.equalsIgnoreCase(MessageChat.Chat))
        {
            // if command is chat then send chat to all clients in room
            System.out.println(roomID+" chat message:"+strmsg );
            myserver.myClient.reciveMessageChat(clientName,roomID,strmsg);
        }
        
    }
}