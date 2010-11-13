package net.davidwallen.notification;

/**
 *
 * @author David W. Allen <david.w.allen@me.com>
 */
public interface Notification {

  /**
   * Get the application reference that is sending the notification.
   * @return the application object.
   */
  Application getApplication();

  /**
   * Get the message body.
   * @return message text.
   */
  String getMessage();

  /**
   * Return the priority assigned to this notification.
   * @return priority.
   */
  Priority getPriority();

  /**
   * Get the title();
   * @return the title text;
   */
  String getTitle();

  /**
   * Get the type of notification.
   * @return the NotificationImpl type.
   */
  NotificationType getType();

  /**
   * Return if this notification should be set to sticky or not.
   * @return true if sticky, false otherwise.
   */
  boolean isSticky();

}
