/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatroomclient;

/**
*Mohammed Fahad Kaleem(1000969369)
*Nitin Kamani(1000919137)
*Sarabjeet Singh(1001115369)
*Mohammed Mudassir Ahmed(1001108922)
 */

public class App extends Thread
{
    public String name="noname";
    public Chat chat;
    public ChatRoom chatroom;
    public Login login;
    public MessageChat msgchat;
    public NewChatRoom newchat;
    public Register register;
    public Client client;
    public DriverDatabase database;
    public String ipServer;
    public int port;
    public String dbname;
    public void run()
    {
        while(true)
        {
            if(client!=null&&client.client.isClosed()) return;
        }
        
    }
    
    public static void main(String args[])
    {
        /*The message is: name|command|IDroom|message
        =>to login send message: name|@login|0|username|password
        =>to register send message: name|@register|0|username|password
        =>to create room send message:name|@create|0|roomname
        =>to logout send message: name|@logout|0|logout
        =>to chat send message: name|@chat|idroom|message
        */
        App app=new App();
        app.ipServer="127.0.0.1";
        app.port=8080;
        app.dbname="chatroom";
        //Step 1 open login 
        app.login=new Login();
        app.login.setApp(app);
        app.login.show();
        app.start();
        System.out.println("Client Connected!");
        
    }
    
}
