/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatroomserver;
/**
*Mohammed Fahad Kaleem(1000969369)
*Nitin Kamani(1000919137)
*Sarabjeet Singh(1001115369)
*Mohammed Mudassir Ahmed(1001108922)
 */

public class MessageChat {
    
    public static  String Register="@Registe";
    public static  String CreateRoom="@Create";
    public static  String Login="@Login";
    public static  String Logout="@Logout";
    public static  String JoininRoom="@Joinin";
    public static  String Chat="@Chat";
    public static String dotMsg="|";
    public static  String JoinoutRoom="@Joinout";
    public static String AllRoom="@AllRoom";
    public static  String RoomCount="@RoomCount";
    public static  String IP="@IP";
    public static  String Port="@Port";
    public static String encryption(String pass)
    {
        String kq="";
        for(int i=0;i<pass.length();i++)
        {
            char ch=pass.charAt(i);
            ch++;
            kq=kq+ch;
        }
        return kq;
    }
    public static String Decryption(String pass)
    {
        String kq="";
        for(int i=0;i<pass.length();i++)
        {
            char ch=pass.charAt(i);
            ch--;
            kq=kq+ch;
        }
        return kq;
    }
    
}
