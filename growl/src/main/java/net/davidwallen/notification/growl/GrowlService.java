package net.davidwallen.notification.growl;

import net.davidwallen.notification.NotificationService;
import java.net.InetAddress;

/**
 *
 * @author David W. Allen <david.w.allen@me.com>
 */
public interface GrowlService extends NotificationService {

  /** The default prot to open a socket to. */
  static int DEFAULT_PORT = 9887;
  /** The GNTP version. This is Unencrypted. No support for encryption right now. */
  static byte GNTP_VERSION = 1;
  /** The packet type of registration packets with MD5 authentication. */
  static byte GROWL_TYPE_REGISTRATION = 0;
  /** The packet type of notification packets with MD5 authentication. */
  static byte GROWL_TYPE_NOTIFICATION = 1;
  /** The packet type of registration packets with SHA-256 authentication. */
  static byte GROWL_TYPE_REGISTRATION_SHA256 = 2;
  /** The packet type of notification packets with SHA-256 authentication. */
  static byte GROWL_TYPE_NOTIFICATION_SHA256 = 3;
  /** The packet type of registration packets without authentication. */
  static byte GROWL_TYPE_REGISTRATION_NOAUTH = 4;
  /** The packet type of notification packets without authentication. */
  static byte GROWL_TYPE_NOTIFICATION_NOAUTH = 5;

  /**
   * Add a client to the list of recipients.
   * @param host the client host name or ip address.
   * @param port the port. Default is 9887.
   */
  void addClient(InetAddress host, int port);

  /**
   * Remove a client to the list of recipients.
   * @param host the client host name or ip address.
   */
  void removeClient(InetAddress host);
}
