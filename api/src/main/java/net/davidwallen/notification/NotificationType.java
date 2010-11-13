package net.davidwallen.notification;

/**
 *
 * An interface for the type of notifications an application will be sending.
 * The types must be registered when the application is registered.
 *
 * @author David W. Allen <david.w.allen@me.com>
 */
public interface NotificationType {

  /**
   * Get the name of the notification.
   * @return the name of the Notification Type.
   */
  public String getTypeName();

  /**
   * Query if this notification type is enabled.
   * @return true if enabled, false otherwise.
   */
  public boolean isEnabled();

}
