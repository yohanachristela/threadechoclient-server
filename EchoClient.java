import java.io.*;
import java.net.*;
import java.util.*;   // package sbg scanner 

public class EchoClient {
  
  private static int port = 8998;
  private static String hostname;
 
 public static void main (String [] args) {
  
   // untuk mengirim pesan ke server harus masuk ke localhost dulu
   if(args.length != 1){
     System.out.println("Welcome To The Client!");
     System.exit(1);
   } else {
     hostname = args[0];
   }

   // membuat objek scanner baru 
   Scanner sc = new Scanner(System.in);
  
  try {
   //make connection to server socket
   Socket conn = new Socket(hostname, port);
   
   //Create data streams
   DataOutputStream out = new DataOutputStream(conn.getOutputStream());
   InputStream in = conn.getInputStream();
   ByteArrayOutputStream buffer = new ByteArrayOutputStream();
   

   
    while(true){
      //Take user input
      String m = sc.nextLine();

      byte[] byteMsg = m.getBytes(); //program membaca setiap byte msg
      String message = new String(byteMsg, "UTF-8"); // mengkonversi setiap byte msg ke dalam str
      
      //Check if the escape character is used
      if(message.equals("quit")){
        conn.close();
        break;
      } else if(message.equals("")) {
        System.out.println("Please enter something!");
      }else {
        //Output message to the output stream
        out.write(byteMsg);
        
        //Read the response from the server
          int nRead;
          byte[] data = new byte[16384];

          if ((nRead = in.read(data, 0, data.length)) != -1){
              buffer.write(data,0,nRead);
          }

          buffer.flush();

          String res = new String(buffer.toByteArray(), "UTF-8"); // mengkonversi setiap byte res ke dalam str

          System.out.println("Server Respond: " + res);
          buffer.reset();
      }

    }

  } 
  catch (IOException e) {
   System.out.println("Server Connection Failed: Server not available.");
   System.exit(1);
  }
  
 }

}