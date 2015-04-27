package chatroomclient;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.lang.Thread;
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
public class Client extends Thread {
    DataOutputStream dos;
    DataInputStream dis;
    Socket client;
    Chat[] UImessage;
    App app;
    int index=0;
    P2PList lClient;
    Server myserver;
    public Client(InetAddress host,int port,String name)
    {
        UImessage=new Chat[5];
        lClient=new P2PList();
        try
        {
            // connect client to the server
            client=new Socket(host,port);
            dos=new DataOutputStream(client.getOutputStream());
            dis=new DataInputStream(client.getInputStream());
            //dos.writeUTF("Name:"+app.name);
        }
        catch(Exception ae){
            System.out.println("Error in connecting: "+ae);
        }
    }
    public void setApp(App app)
    {
        this.app=app;
    }
    
    public void addMessage(Chat ui)
    {
        // add a chat window to list
        if(index<UImessage.length)
        {
            UImessage[index++]=ui;
        }
    }
    public Chat getUIMessage(String n)
    {
        //get the chatname of the chatwindow
        for(int i=0;i<index;i++)
        {
            
            if(UImessage[i].getName().trim().equalsIgnoreCase(n.trim()))
            {
                
                return UImessage[i];
            }
        }
        return null;
    }
    public void sendMessage(String msg) 
    {
        try
        {
           //send mesage to server
             dos.writeUTF(msg+"\n");
             
             
        }
        catch(Exception ae)
        {
            System.out.println("Error is: "+ae);
        }
       
    }
    public void sendChat(String msg) 
    {
        lClient.sendChat(msg);
       
    }
    public boolean saveChat(String session)
    {
        //save chat to database
        boolean check=true;
        for(int i=0;i<index;i++)
        {
            check=app.database.saveSesion(app.name, session, UImessage[i].roomID, UImessage[i].getValue());
        }
        return check;
    }
    public void run()
    {
        
            while(true)
            {
                //get a message from server
                try
                {
                String data=dis.readUTF();
                System.out.println("Message from server:"+data);
                processMessage(data);
                }
                catch(Exception ae)
                {}
            }
        
        
    }
    
    //process the message from the server
    public void processMessage(String msg)
    {
        /*The message is: name|command|IDroom|message
        =>to login send message: name|@login|0|username|password
        =>to register send message: name|@register|0|username|password
        =>to create room send message:name|@create|0|roomname
        =>to logout send message: name|@logout|0|logout
        =>to chat send message: name|@chat|idroom|message
        */
        
        
        StringTokenizer strTokToken =
            new StringTokenizer(msg, MessageChat.dotMsg, false);
        String clientName=strTokToken.nextToken();// get client name
        String command=strTokToken.nextToken();//get command
        String roomID=strTokToken.nextToken();//get romid
        String strmsg=strTokToken.nextToken();//get parama
        if(command.equalsIgnoreCase(MessageChat.Login))//if messge is login
        {
            
            
            if(strmsg.trim().equalsIgnoreCase("true"))// check login is true
            {
                //open the chatroom window
                if(app.chatroom==null)
                {
                    app.chatroom=new ChatRoom();
                    app.chatroom.setApp(app);
                }
                app.login.checkLogin(true);
                app.chatroom.show();
                
                //create database to connect mysql to get history chat
                String userroot=strTokToken.nextToken();
                String pasroot=strTokToken.nextToken();
                String nextStr=strTokToken.nextToken();
                if(nextStr.equalsIgnoreCase(MessageChat.Port))
                {
                    int getport=Integer.parseInt(strTokToken.nextToken());
                    System.out.println("This port is "+getport);
                    myserver=new Server(this,getport);
                    myserver.start();
                }
                app.database=new DriverDatabase(app.ipServer, app.dbname, userroot, pasroot);
            }
            else
            {
                //if not login then show error 
               if(app==null) System.out.println("Input proper data.");
                if(app.login==null)
                {
                    app.login=new Login();
                }
                //call not login from login window
                app.login.checkLogin(false);
            }
        }
        if(command.equalsIgnoreCase(MessageChat.Logout))
        {
            
        }
        if(command.equalsIgnoreCase(MessageChat.AllRoom))
        {
            
            if(app.chatroom==null)
            {
                //creat chatroom window if is null
                app.chatroom=new ChatRoom();
                app.chatroom.setApp(app);
            }
            // add all romid from server
            app.chatroom.addRoom(strmsg);
            while (strTokToken.hasMoreTokens())
            {
                app.chatroom.addRoom(strTokToken.nextToken());//add roomid
            }
        }
        
        if(command.equalsIgnoreCase(MessageChat.Register))
        {
            if(strmsg.equalsIgnoreCase("true"))//if register is ok
            {
                app.register.checkRegister(true);//show comple from register window
            }
            else
                app.register.checkRegister(false);//show error from register window
        }
        if(command.equalsIgnoreCase(MessageChat.JoininRoom))
        {
            //strmsg is list user in roomid
            
             Chat ui=app.client.getUIMessage(roomID);//get chat roomid if exist
            
           if(ui!=null)//if not exist create new
           {
               //add list user is in roomid from server
               System.out.println("list all user:"+strmsg);
                ui.addUser(strmsg);
                String user=strmsg;
                String ip="";
                int port=0;
                while (strTokToken.hasMoreTokens())
               {
                   
                   String token=strTokToken.nextToken();
                   if(user.length()<=0)user=token;
                   if(token.equalsIgnoreCase(MessageChat.IP))ip=strTokToken.nextToken();
                   if(token.equalsIgnoreCase(MessageChat.Port))
                   {
                       System.out.println("The user is: "+user);
                       port=Integer.parseInt( strTokToken.nextToken());
                       ui.addUser(user);
                       P2PClient p2p=new P2PClient(user,ip,port);//save other client
                       
                       lClient.add(p2p);
                       user="";
                       
                   }
                   
               }
                
           }
           // request the number user in roomid
           String message=app.name+MessageChat.dotMsg+MessageChat.RoomCount+MessageChat.dotMsg+
                        roomID+MessageChat.dotMsg+roomID;
                app.client.sendMessage(message); 
           
        }
        if(command.equalsIgnoreCase(MessageChat.JoinoutRoom))
        {
            Chat ui=getUIMessage(roomID);//get chat roomid if exist
             if(ui!=null)
             {
               ui.removeUser(strmsg);   //remove user out room
             }
             // request the number user in roomid
            String message=app.name+MessageChat.dotMsg+MessageChat.RoomCount+MessageChat.dotMsg+
                    roomID+MessageChat.dotMsg+roomID;
            app.client.sendMessage(message);
        }
        if(command.equalsIgnoreCase(MessageChat.CreateRoom))
        {
            //if create room ok. add to list room
            if(strmsg.equalsIgnoreCase("true")) app.chatroom.addRoom(roomID);
        }
        if(command.equalsIgnoreCase(MessageChat.Chat))
        {
            //send message chat to chat room id
           reciveMessageChat(clientName,roomID,strmsg);
        }
        if(command.equalsIgnoreCase(MessageChat.RoomCount))
        {
            //if respont from server is roomcount then update to chatroom windows
            app.chatroom.updateRoom(roomID, strmsg);
        }
    }
    public void reciveMessageChat(String clientName,String roomID,String strmsg)
    {
        Chat ui=getUIMessage(roomID);
             if(ui!=null)
             {
                 ui.receiveMessage(clientName+":"+ strmsg);
             }
    }


}
