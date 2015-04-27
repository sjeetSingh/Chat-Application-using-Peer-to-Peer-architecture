/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chatroomserver;

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
                                System.out.println("from client:"+st);
                                processMessage(st);
                            }
                            catch(Exception ae)
                            {
                                continue;
                            }
				
			}
		}
		catch(Exception ae){
                System.out.println("error message:"+ae );}
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
        
        public int getPortfree()
        {
            return myserver.getPortfree();
        }
        public int getCurPort()
        {
            return server.getPort();
        }
        public int getNewPort()
        {
            return port;
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
        if(command.equalsIgnoreCase(MessageChat.Login))
        {
            //check if client has logged in            
            String pass=strTokToken.nextToken();

            // check userID and password from database
            boolean check=myserver.database.checkLogin(strmsg, pass);
            
            //send result to client with the port
            if(check)
            {
                
                port=myserver.getPortfree();
                
            }
            System.out.println("The port number is: "+strmsg+" is "+port);
            String message=clientName+MessageChat.dotMsg+MessageChat.Login+MessageChat.dotMsg+
                    "0"+MessageChat.dotMsg+check+MessageChat.dotMsg+myserver.database.user+MessageChat.dotMsg
                    +myserver.database.password+MessageChat.dotMsg+MessageChat.Port+MessageChat.dotMsg+port;
            
            myserver.sendMessage(clientName, message);
            
            //send all chat messages if login successful
            if(check)
            {
                
                message=clientName+MessageChat.dotMsg+MessageChat.AllRoom+MessageChat.dotMsg+
                    "0"+MessageChat.dotMsg+myserver.database.getAllRoom();;
                myserver.sendMessage(clientName, message);
            }
            
        }
        if(command.equalsIgnoreCase(MessageChat.Logout))
        {
            String message=clientName+MessageChat.dotMsg+MessageChat.Logout+MessageChat.dotMsg+
                    "0"+MessageChat.dotMsg+clientName;
            myserver.sendMessageAll(message);
        }
        if(command.equalsIgnoreCase(MessageChat.Register))
        {
            // if client requests to register
           String pass=strTokToken.nextToken();
           
           //check userID and password
            boolean check=myserver.database.register(strmsg, pass);
            
            //send result
            String message=clientName+MessageChat.dotMsg+MessageChat.Register+MessageChat.dotMsg+
                    "0"+MessageChat.dotMsg+check;
            
            myserver.sendMessage(clientName, message);
        }

        if(command.equalsIgnoreCase(MessageChat.JoininRoom))
        {
            RoomChat room=new RoomChat(roomID);
            
            // if new client is joining then insert the user in the list of that chatroom
            myserver.listRoom.joinInRoom(clientName, room);
            
            // Sent the list of all users to the client who's just joined the chatroom
            String alluser=myserver.listRoom.getListUser(room);
            System.out.println("list of users are: "+alluser);
            String kq="";
            StringTokenizer userTokToken =
            new StringTokenizer(alluser, MessageChat.dotMsg, false);
            while(userTokToken.hasMoreTokens())
            {
                String username=userTokToken.nextToken();
                System.out.println("New user joined is: "+username);
                int index = myserver.getClient(username);
                if(index>=0)
                kq+=username+MessageChat.dotMsg+MessageChat.IP+MessageChat.dotMsg+ myserver.listClient[index].IP+MessageChat.dotMsg+
                        MessageChat.Port+MessageChat.dotMsg+myserver.listClient[index].port+MessageChat.dotMsg;
            }
            System.out.println("result of Join room is "+kq);
            
            
            String message=clientName+MessageChat.dotMsg+MessageChat.JoininRoom+MessageChat.dotMsg+
                        roomID+MessageChat.dotMsg+kq;
            myserver.sendMessageAll( message);
        }
        if(command.equalsIgnoreCase(MessageChat.JoinoutRoom))
        {
           RoomChat room=new RoomChat(roomID);
           // if join out then update the listroom
            myserver.listRoom.joinOutRoom(clientName, room);

            //send to client
            String message=clientName+MessageChat.dotMsg+MessageChat.JoinoutRoom+MessageChat.dotMsg+
                        roomID+MessageChat.dotMsg+clientName;
            myserver.sendMessageAll( message);
        }
        if(command.equalsIgnoreCase(MessageChat.CreateRoom))
        {
            boolean check=myserver.database.CreateRoom(roomID);
            if(check)
            {
                // if create room then create room in database and send the information about creation of new room to all clients
                String message=clientName+MessageChat.dotMsg+MessageChat.CreateRoom+MessageChat.dotMsg+
                        roomID+MessageChat.dotMsg+check;
                myserver.sendMessageAll( message);
            }
            else
            {
                String message=clientName+MessageChat.dotMsg+MessageChat.CreateRoom+MessageChat.dotMsg+
                        roomID+MessageChat.dotMsg+check;
                myserver.sendMessage(clientName, message);
            }
        }
        if(command.equalsIgnoreCase(MessageChat.Chat))
        {
            // if command is chat then send chat to all client in room
            String message=clientName+MessageChat.dotMsg+MessageChat.Chat+MessageChat.dotMsg+
                        roomID+MessageChat.dotMsg+strmsg;
                myserver.sendMessageAll(message);
        }
        if(command.equalsIgnoreCase(MessageChat.RoomCount))
        {
            // get the number of users in the room and send it to the clients in the chatroom
            RoomChat room=new RoomChat(roomID);
            String message=clientName+MessageChat.dotMsg+MessageChat.RoomCount+MessageChat.dotMsg+
                        roomID+MessageChat.dotMsg+myserver.listRoom.getUserCount(room);
                myserver.sendMessage(clientName, message);
        }
    }
}