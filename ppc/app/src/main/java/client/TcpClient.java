package client;

import java.io.*;
import java.net.*;

import common.*;

public class TcpClient {
  private final Socket connection;
  private final ObjectInput objectIn;
  private final ObjectOutput objectOut;
  
  public TcpClient() throws IOException {
    connection = new Socket(Protocol.SERVER_ADDRESS, Protocol.SERVER_PORT);
    objectOut = new ObjectOutputStream(connection.getOutputStream());
    objectOut.flush();
    objectIn = new ObjectInputStream(connection.getInputStream());
  }
  
  public Message sendReceive(Message message) throws IOException, ClassNotFoundException {
    send(message);
    return receive();
  }
  
  private void send(Message message) throws IOException {
    objectOut.writeObject(message);
    objectOut.flush();
  }
  
  private Message receive() throws IOException, ClassNotFoundException {
    return (Message) objectIn.readObject();
  }
}
