package net.davidwallen.alert4j.xmpp;

import net.davidwallen.alert4j.NotificationService;
import org.jivesoftware.smack.XMPPException;

/**
 * Provide notification service over XMPP protocol.
 *
 * @author David W. Allen <david.w.allen@me.com>
 */
public interface XMPPService extends NotificationService {

  /**
   * Login to the server. Login details should be injected with configuration file or DI mechanism.
   * @throws XMPPException problems logging in.
   */
  void login() throws XMPPException;

  /**
   * Disconnect from the server.
   */
  void disconnect();

  /**
   * Register a username for receiving notifications.
   * @param username user to add.
   */
  void registerUser(String username);

  /**
   * Remove a username from receiving notifications.
   * @param username user to remove.
   */
  void removeUser(String username);

}
