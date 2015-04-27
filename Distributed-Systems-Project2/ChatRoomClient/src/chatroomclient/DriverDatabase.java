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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.util.Date;

  public class DriverDatabase {
     
     String driver;
     String url;
     String dbname;
     String user;
     String password;
     
     // create database and connect it to mysql
     public DriverDatabase(String url,String dbName,String userName,String password)
     {
         driver = "org.gjt.mm.mysql.Driver";
         this.url="jdbc:mysql://"+url+"/";
         this.dbname=dbName;
         this.user=userName;
         this.password=password;
     }
     
     //check a session from database
     public boolean checkSession(String session)
     {
        Connection conn=null;
         try
         {
             Class.forName(driver).newInstance();
             conn = DriverManager.getConnection(url+dbname,this.user,this.password);
         }
         catch (Exception e) { e.printStackTrace(); } 
        try
         {
              
            Statement st = conn.createStatement(); 
            ResultSet res = st.executeQuery("SELECT * FROM chatlogs where session = '"+session.trim()+"'");
            if(res.next())
            {
                conn.close();
                return true;
            }
            conn.close();
         }
        catch (Exception e) { e.printStackTrace(); }
        
        return false;
     }
     
     //save a session to database
     public boolean  saveSesion(String userName,String session,String roomid,String value)
     {
         Connection conn=null;       
         try
         {
             Class.forName(driver).newInstance();
             conn = DriverManager.getConnection(url+dbname,this.user,this.password);
             
         }
         catch (Exception e) { e.printStackTrace(); } 
        Date date = new Date();   
        String strdate=date.getDay()+"/"+date.getMonth()+"/"+date.getYear();
       
        if(checkSession(session))return false;
        try
        {
            
            Statement st = conn.createStatement(); 
            int val = st.executeUpdate("INSERT into chatlogs  VALUES("+"'"+userName.trim()+"','"+session.trim()+"','"+strdate+"','"
                    +roomid+"','"+value+"')"); 
            if(val==1)
            {
                conn.close();
                return true;
            }
            
            conn.close();
        }
        catch (Exception e) { e.printStackTrace(); }
        
        return false;
     }
     
     //view session
     public String  viewSesion(String userName,String session,String date)
     {
         String result="";
        
         Connection conn=null;
         try
         {
             Class.forName(driver).newInstance();
             conn = DriverManager.getConnection(url+dbname,this.user,this.password);
            
         }
         catch (Exception e) { e.printStackTrace(); } 
        try
         {
            Statement st = conn.createStatement(); 
            ResultSet res = st.executeQuery("SELECT * FROM chatlogs where username='"+userName.trim() +"' and  session = '"+session.trim()+"' and date='"+date.trim()+"'");
            if(res.next())
            {
                result=result+res.getString("contend")+"\n";
            }
            conn.close();
         }
        catch (Exception e) { e.printStackTrace(); }
        
        return result;
     }
  }
