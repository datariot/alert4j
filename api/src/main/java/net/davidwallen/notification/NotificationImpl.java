package net.davidwallen.notification;

/**
 *
 * A notification to be sent to Growl.
 *
 * @author David W. Allen <david.w.allen@me.com>
 */
public class NotificationImpl implements Notification {

  private final Application app;
  private final String title;
  private final String message;
  private final NotificationType type;
  private Priority priority = Priority.NORMAL;
  private boolean sticky = false;

  /**
   * Constructor for a notification.
   * @param app The application sending the notification.
   * @param title The title of the NotificationImpl.
   * @param message The message body of the NotificationImpl.
   * @param type The notification type. The type must be registered with the app before use.
   */
  public NotificationImpl(Application app, String title, String message, NotificationType type) {
    this.app = app;
    this.title = title;
    this.message = message;
    this.type = type;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Application getApplication() {
    return this.app;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Priority getPriority() {
    return priority;
  }

  /**
   * {@inheritDoc}
   */
  public void setPriority(Priority priority) {
    this.priority = priority;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isSticky() {
    return sticky;
  }

  /**
   * Sets the notification as sticky.
   */
  public void makeSticky() {
    sticky = true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getMessage() {
    return message;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTitle() {
    return title;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public NotificationType getType() {
    return type;
  }

}
