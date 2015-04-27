/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatroomserver;
import java.util.*;

/**
*Mohammed Fahad Kaleem(1000969369)
*Nitin Kamani(1000919137)
*Sarabjeet Singh(1001115369)
*Mohammed Mudassir Ahmed(1001108922)
 */

// This class saves a roomchat. A roomchat has name and the list of users in the room
public class RoomChat {
    public String RoomId;
    public List listUsers;
    public RoomChat(String RoomId)
    {
        this.RoomId=RoomId;
        listUsers=new ArrayList();
    }
    public void addUser(String user)
    {
        if(!listUsers.contains(user)) listUsers.add(user);
    }
    public void removeUser(String user)
    {
        if(listUsers.contains(user))listUsers.remove(user);
    }
    public String getAllUser()
    {
        String result="";
        for(int i=0;i<listUsers.size();i++)
            result=result+MessageChat.dotMsg+listUsers.get(i);
        return result;
    }
}
