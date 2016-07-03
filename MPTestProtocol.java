/*
Jeremy Manin
Multiplayer Test- Protocol
*/

//import java.io.*;
//import java.net.*;

public class MPTestProtocol
{   
   public String processInput(String input)
   {
      if(input==null)
         return("Welcome to collaborative story telling\nTake turns writing lines to create a story.\nEnd the story by entering \"The End\". (no punctuation)\nPlayer 1 goes first.");
      else if(input.equalsIgnoreCase("The End"))
         return("Thanks for playing!\nDisconnecting from server.");
      else
         return(input);      
   }
}