package com.zheng.poker.texas;

/**
 * Created by zheng on 2016/11/25.
 */
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client
{
    public static void main(String[] args)
    {
        try
        {
            Socket connectionToServer = new Socket("localhost", 8888);
            System.out.println(connectionToServer.getLocalPort());
            DataInputStream inFromServer = new DataInputStream(connectionToServer.getInputStream());
            DataOutputStream outToServer = new DataOutputStream(connectionToServer.getOutputStream());
            System.out.println("input your name now:");
            String outStr, inStr;
            BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
            outStr = buf.readLine();
            outToServer.writeUTF(outStr);
            outToServer.flush();
            inStr = inFromServer.readUTF();
            System.out.println(inStr);
            while(true){
                inStr = inFromServer.readUTF();
                System.out.println(inStr);
                if(inStr.equalsIgnoreCase("Game Over"))
                    break;
                if(inStr.substring(0,7).equalsIgnoreCase("inquire")){
                    outToServer.writeUTF("all_in");
                    outToServer.flush();
                }
            }
            inFromServer.close();
            outToServer.close();
            connectionToServer.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}