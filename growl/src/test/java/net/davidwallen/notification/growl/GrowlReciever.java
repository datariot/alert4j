package net.davidwallen.notification.growl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author David W. Allen <david.w.allen@me.com>
 */
public class GrowlReciever {

  private static Logger logger = LoggerFactory.getLogger(GrowlReciever.class);

  public static void main(String... args) throws SocketException, IOException {
    DatagramSocket socket = new DatagramSocket(9887);
    byte[] buff = new byte[128];
    try {
      while(true) {
        buff = new byte[128];
        DatagramPacket packet = new DatagramPacket(buff, buff.length);
        socket.receive(packet);
        logger.info("Contents:\n{}",Arrays.toString(packet.getData()));
      }
    } finally {
      socket.close();
    }
  }

}
