/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatroomserver;
import java.util.*;
/**
 *
 * @author Administrator
 */
public class ListRoomChat {
    public List<RoomChat> listRoom;
    public ListRoomChat()
    {
        listRoom=new ArrayList<>();
    }
    
    public void joinInRoom(String user,RoomChat room)
    {
        // this fuction adds a user to a roomchat and makes him available in the room
        RoomChat lroom=checkRoom(room);
        if(lroom==null)
        {
            room.addUser(user);
            listRoom.add(room);
        }
        else lroom.addUser(user);
        
    }
    public void joinOutRoom(String user,RoomChat room)
    {
        // the fuction remove a user from the roomchat and remove his/her name from the room
        RoomChat lroom=checkRoom(room);
        if(lroom!=null)
        {
            lroom.removeUser(user);
        }
        
        
    }
    public int getUserCount(RoomChat room)
    {
        // the fuction gets the number of users in a roomchat 
        RoomChat lroom=checkRoom(room);
        if(lroom!=null)
        {
            return lroom.listUsers.size();
        }
        return 0;
    }
    public String getListUser(RoomChat room)
    {
        // get list users in roomchat
        RoomChat lroom=checkRoom(room);
        if(lroom!=null)
        {
            return lroom.getAllUser();
        }
        return null;
    }
    private RoomChat checkRoom(RoomChat room)
    {
        for(int i=0;i<listRoom.size();i++)
            if(listRoom.get(i).RoomId.equalsIgnoreCase(room.RoomId))return listRoom.get(i);
        return null;
    }
}
