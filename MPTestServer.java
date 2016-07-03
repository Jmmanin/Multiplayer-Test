/*
Jeremy Manin
Multiplayer Test- Server
*/

import java.io.*;
import java.net.*;

public class MPTestServer
{
   public static void main(String args[])
   {
      int portNumber1= 0, portNumber2= 0;
      String inputLine, outputLine;
      ServerSocket serverSocket1, serverSocket2;
      Socket p1Socket, p2Socket;
      PrintWriter p1Out, p2Out;
      BufferedReader p1In, p2In;
      MPTestProtocol theProtocol;
      boolean isP1Turn= true;
   
      try
      {
         portNumber1= Integer.parseInt(args[0]);
         portNumber2= Integer.parseInt(args[1]);
      }
      catch(Exception e)
      {
         System.err.println("Usage: java MPTestServer <port number 1> <port number 2>");
         System.exit(1);
      }
      
      try
      {
         serverSocket1= new ServerSocket(portNumber1);
         serverSocket2= new ServerSocket(portNumber2);
         
         p1Socket= serverSocket1.accept();
         System.out.println("Player 1 has connected: " + p1Socket.getInetAddress().toString());
         p2Socket= serverSocket1.accept();
      
         p1Out= new PrintWriter(p1Socket.getOutputStream(), true);
         p1In= new BufferedReader(new InputStreamReader(p1Socket.getInputStream()));
         p2Out= new PrintWriter(p2Socket.getOutputStream(), true);
         
         p1Out.println("true");
         p2Out.println("false");
                     
         p2Socket.close();
         p2Socket= serverSocket2.accept();
         System.out.println("Player 2 has connected: " + p2Socket.getInetAddress().toString());
      
         p2Out= new PrintWriter(p2Socket.getOutputStream(), true);
         p2In= new BufferedReader(new InputStreamReader(p2Socket.getInputStream()));
         
         theProtocol = new MPTestProtocol();
         outputLine = theProtocol.processInput(null);
      
         p1Out.println(outputLine);
         p2Out.println(outputLine);
         System.out.println("Intro sent.");
      
         while(true)
         {
            if(isP1Turn)
            {
               inputLine= p1In.readLine();
               outputLine= theProtocol.processInput(inputLine);
                              
               if(inputLine.equalsIgnoreCase("The End"))
               {
                  p2Out.println(inputLine);
                  p1Out.println(outputLine);
               }
               
               p2Out.println(outputLine);
               
               System.out.println("Player 1 took their turn.");
           }
            else
            {
               inputLine= p2In.readLine();
               outputLine= theProtocol.processInput(inputLine);
               
               if(inputLine.equalsIgnoreCase("The End"))
               {
                  p1Out.println(inputLine);
                  p2Out.println(outputLine);
               }
                              
               p1Out.println(outputLine);
               
               System.out.println("Player 2 took their turn.");
            }
                        
            isP1Turn= !isP1Turn;
            
            if(inputLine.equalsIgnoreCase("The End"))
               break;     
         }
         
         System.out.println("Story over.");
         
         p1Socket.close();
         p2Socket.close();
         serverSocket1.close();
         serverSocket2.close();
      }
      catch(IOException e)
      {
         System.err.println("Error communicating or connecting to server");
         System.exit(1);
      }
      catch(NullPointerException e)
      {
         System.err.println("Error communicating or connecting to server");
         System.exit(1);
      }
      
      System.out.println("Server closing.");
   }
}