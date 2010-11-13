package net.davidwallen.notification;

/**
 *
 * @author David W. Allen <david.w.allen@me.com>
 */
public interface Application {

  /**
   * Return the application's name.
   * @return the name.
   */
  String getName();

  /**
   * Returns an array of types that
   * @return
   */
  NotificationType[] getRegisteredNotificationTypes();

  /**
   * Gets a byte array of indexes to the default notification types.
   * @return an index array of default notification types.
   */
  Byte[] getDefaults();

}
