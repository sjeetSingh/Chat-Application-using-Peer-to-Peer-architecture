/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatroomclient;
import java.util.*;

/**
*Mohammed Fahad Kaleem(1000969369)
*Nitin Kamani(1000919137)
*Sarabjeet Singh(1001115369)
*Mohammed Mudassir Ahmed(1001108922)
 */

public class P2PList {
    List<P2PClient> listClient;
    public P2PList()
    {
        listClient=new ArrayList<P2PClient>();
    }
    public void add(P2PClient client)
    {
        P2PClient c=getClient(client.name);
        if(c==null)
            listClient.add(client);
    }
    public P2PClient getClient(String name)
    {
        for(int i=0;i<listClient.size();i++)
            if(listClient.get(i).name.equalsIgnoreCase(name)) return listClient.get(i);
        return null;
    }
    public void sendChat(String msg)
    {
        for(int i=0;i<listClient.size();i++)
        {
             System.out.println("Send message to: "+listClient.get(i).name );
            listClient.get(i).sendMessage(msg);
        }
    }
}
