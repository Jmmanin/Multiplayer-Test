/*
Jeremy Manin
Multiplayer Test- Client
*/

import java.io.*;
import java.net.*;

public class MPTestClient
{
   public static void main(String args[])
   {
      String hostName= null;
      int portNumber1= 0, portNumber2= 0;
      Socket mptSocket;
      PrintWriter out;
      BufferedReader in;
      BufferedReader stdIn;
      boolean isPlayer1;
      boolean isTurn= true;
      String fromServer,fromUser;
      
      try
      {
         hostName= args[0];
         portNumber1= Integer.parseInt(args[1]);
         portNumber2= Integer.parseInt(args[2]);
      }
      catch(Exception e)
      {
         System.err.println("Usage: java MPTestClient <host name> <port number 1> <port number 2>");
         System.exit(1);
      }
   
      try
      {
         mptSocket= new Socket(hostName, portNumber1);
         in= new BufferedReader(new InputStreamReader(mptSocket.getInputStream()));
         
         isPlayer1= Boolean.parseBoolean(in.readLine());
         
         if(!isPlayer1)
         {
            mptSocket.close();
            mptSocket= new Socket(hostName, portNumber2);
            in= new BufferedReader(new InputStreamReader(mptSocket.getInputStream()));
         
            isTurn= false;
         }
         
         out= new PrintWriter(mptSocket.getOutputStream(), true);
         stdIn= new BufferedReader(new InputStreamReader(System.in));         
         
         if(isPlayer1)
            System.out.println(in.readLine() + ", Player 1!");
         else
            System.out.println(in.readLine() + ", Player 2!");
            
         System.out.println(in.readLine()); //outputs intro
         System.out.println(in.readLine());
         System.out.println(in.readLine());
         System.out.print("\n");
      
         while(true)
         {
            if(isTurn)
            {
               fromUser= stdIn.readLine();
               out.println(fromUser);
               
               if(fromUser.equalsIgnoreCase("The End"))
                  break;
            }
            else
            {
               fromServer= in.readLine();
               System.out.println(fromServer);
               
               if(fromServer.equalsIgnoreCase("The End"))
                  break;
            }
            
            isTurn= !isTurn;
         }
         
         System.out.print("\n");
         System.out.println(in.readLine());
         System.out.println(in.readLine());
         
         mptSocket.close();
      } 
      catch (UnknownHostException e)
      {
         System.err.println("Invalid hostname " + hostName);
         System.exit(1);
      } 
      catch (IOException e)
      {
         System.err.println("I/O error with " + hostName);
         System.exit(1);
      }   
   }
}