/**
 * (c) 2009, David W. Allen
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of David W. Allen nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL David W. Allen BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.davidwallen.notification.growl.impl;

import net.davidwallen.notification.Application;
import net.davidwallen.notification.Notification;
import net.davidwallen.notification.growl.GrowlService;
import net.davidwallen.notification.NotificationType;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Implements a growl service to allow registration of clients, applications, and notifications.
 *
 * GNTP documented here: http://growl.info/documentation/developer/protocol.php
 *
 * @author David W. Allen <david.w.allen@me.com>
 */
@Component(immediate=true)
@Provides
public class GrowlServiceImpl implements GrowlService {

  /** The default prot to open a socket to. */
  public static int DEFAULT_PORT = 9887;
  /** The GNTP version. This is Unencrypted. No support for encryption right now. */
  public static byte GNTP_VERSION = 1;
  /** The packet type of registration packets with MD5 authentication. */
  public static byte GROWL_TYPE_REGISTRATION = 0;
  /** The packet type of notification packets with MD5 authentication. */
  public static byte GROWL_TYPE_NOTIFICATION = 1;
  /** The packet type of registration packets with SHA-256 authentication. */
  public static byte GROWL_TYPE_REGISTRATION_SHA256 = 2;
  /** The packet type of notification packets with SHA-256 authentication. */
  public static byte GROWL_TYPE_NOTIFICATION_SHA256 = 3;
  /** The packet type of registration packets without authentication. */
  public static byte GROWL_TYPE_REGISTRATION_NOAUTH = 4;
  /** The packet type of notification packets without authentication. */
  public static byte GROWL_TYPE_NOTIFICATION_NOAUTH = 5;

  private static final String serviceName = "Growl";
  private static final String UTF8 = "UTF-8";
  private Logger logger = LoggerFactory.getLogger(GrowlService.class);
  private HashMap<InetAddress, Integer> hostMap = new HashMap<InetAddress, Integer>();

  /**
   * Starts the growl service.
   */
  @Validate
  public void start() {
    logger.info("Started Growl service. ");
  }

  /**
   * Shutdown the growl service.
   */
  @Invalidate
  public void stop() {
    logger.info("Shutting down Growl service.");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {
    return serviceName;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void registerApplication(Application app) {
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      DatagramSocket socket = new DatagramSocket();
      try {
        for (InetAddress host : hostMap.keySet()) {
          int port = hostMap.get(host);
          try {
            byte[] byteArray = composeRegistration(app, md);
            send(socket, host, port, byteArray);
          } catch (SocketException ex) {
            logger.error("Unknown Host {}", host);
          } catch (IOException ex) {
            logger.error("Problem with communication.", ex);
          }
        }
      } finally {
        socket.close();
      }
    } catch (SocketException ex) {
      logger.error("Problem getting socket.", ex);
    } catch (NoSuchAlgorithmException ex) {
      logger.error("Problem getting MD5", ex);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void sendNotification(Notification notification) {
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      DatagramSocket socket = new DatagramSocket();
      try {
        for (InetAddress host : hostMap.keySet()) {
          int port = hostMap.get(host);
          try {
            byte[] byteArray = composeNotification(notification, md);
            send(socket, host, port, byteArray);
          } catch (SocketException ex) {
            logger.error("Unknown Host {}", host);
          } catch (IOException ex) {
            logger.error("Problem with communication.", ex);
          }
        }
      } finally {
        socket.close();
      }
    } catch (SocketException ex) {
      logger.error("Problem getting socket.", ex);
    } catch (NoSuchAlgorithmException ex) {
      logger.error("Problem getting MD5", ex);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addClient(InetAddress host, int port) {
    this.hostMap.put(host, port);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeClient(InetAddress host) {
    this.hostMap.remove(host);
  }

  /**
   * Construct a datagram packet and send it to the specified host.
   * @param socket The socket to use.
   * @param host the destination host.
   * @param port the destination port.
   * @param data the data to send.
   * @throws IOException problems with sending the data.
   */
  private void send(DatagramSocket socket, InetAddress host, int port, byte[] data) throws IOException {
    DatagramPacket packet = new DatagramPacket(data, data.length, host, port);
    socket.send(packet);
  }

  /**
   * Fill a byte buffer with a registration message;
   * @param app The application to register;
   * @param md the MD5 function.
   * @return the constructed ByteBuffer to send.
   * @throws IOException problem with constructing the packet data.
   */
  private byte[] composeRegistration(final Application app, final MessageDigest md) throws IOException {
    if (null == app) {
      throw new IllegalArgumentException("Application cannot be null");
    }
    ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
    DataOutputStream out = new DataOutputStream(byteOut);
    NotificationType[] notificationTypes = app.getRegisteredNotificationTypes();
    Byte[] defaults = app.getDefaults();

    out.writeByte(GNTP_VERSION);
    out.writeByte(GROWL_TYPE_REGISTRATION);
    byte[] appName = app.getName().getBytes(UTF8);
    out.writeShort(appName.length);
    out.writeByte(notificationTypes.length);
    out.writeByte(defaults.length);
    out.write(appName);
    for (NotificationType type : app.getRegisteredNotificationTypes()) {
      byte[] name = type.getTypeName().getBytes(UTF8);
      out.writeShort(name.length);
      out.write(name);
    }
    for (Byte defaultIndex : defaults) {
      out.writeByte(defaultIndex);
    }
    byte[] total = appendMD5(byteOut, md);
    return total;
  }

  /**
   * Composes the GNTP byte array for a notification.
   *
   * @param notification object to construct array for.
   * @param md the MD5 function.
   * @return the byte array representing the notification.
   * @throws IOException problems constructing the array.
   */
  private byte[] composeNotification(Notification notification, MessageDigest md) throws IOException {
    if (null == notification) {
      throw new IllegalArgumentException("Application cannot be null");
    }
    ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
    DataOutputStream out = new DataOutputStream(byteOut);

    out.writeByte(GNTP_VERSION);
    out.writeByte(GROWL_TYPE_NOTIFICATION);
    out.writeByte(notification.getPriority().getPriorityValue());
    if (!notification.isSticky()) {
      out.writeByte(0);
    } else {
      out.writeByte(1);
    }
    byte[] noteName = notification.getType().getTypeName().getBytes(UTF8);
    out.writeShort(noteName.length);
    byte[] noteTitle = notification.getTitle().getBytes(UTF8);
    out.writeShort(noteTitle.length);
    byte[] noteDesc = notification.getMessage().getBytes(UTF8);
    out.writeShort(noteDesc.length);
    byte[] appName = notification.getApplication().getName().getBytes(UTF8);
    out.writeShort(appName.length);
    out.write(noteName);
    out.write(noteTitle);
    out.write(noteDesc);
    out.write(appName);
    byte[] total = appendMD5(byteOut, md);
    return total;
  }

  /**
   * Takes a byte array in, calculates the MD5 hash and appends the hash. Growl uses this to veryify the contents
   * of the data.
   *
   * @param byteOut data to hash.
   * @param md the digest.
   * @return the concatenated byte array.
   */
  private byte[] appendMD5(ByteArrayOutputStream byteOut, MessageDigest md) {
    byte[] returnArray = byteOut.toByteArray();
    int len = returnArray.length;
    byte[] md5 = md.digest(returnArray);
    byte[] total = new byte[len + md5.length];
    System.arraycopy(returnArray, 0, total, 0, returnArray.length);
    System.arraycopy(md5, 0, total, len, md5.length);
    return total;
  }
}
