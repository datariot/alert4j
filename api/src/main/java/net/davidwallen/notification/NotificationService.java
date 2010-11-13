package net.davidwallen.notification;

/**
 *
 * @author David W. Allen <david.w.allen@me.com>
 */
public interface NotificationService {

  /**
   * Return a name for the service.
   * @return service name.
   */
  String getName();

  /**
   * Retisters and application with all of the growl clients.
   * @param app the application to register.
   */
  void registerApplication(Application app);

  /**
   * Sends a notification to the clients.
   * @param notification notification to send.
   */
  void sendNotification(Notification notification);

}
