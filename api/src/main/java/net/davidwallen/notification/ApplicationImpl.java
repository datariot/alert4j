package net.davidwallen.notification;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author David W. Allen <david.w.allen@me.com>
 */
public class ApplicationImpl implements Application {

  private String name;

  private NotificationType[] registeredNotificationTypes;

  private List<Byte> defaults = new LinkedList<Byte>();

  /**
   * Setup an application with a name and the valid notification types.
   * @param name Application name.
   * @param validType valid notification types.
   */
  public ApplicationImpl(String name, NotificationType... validType) {
    this.name = name;
    this.registeredNotificationTypes = validType;
    for(int i=0; i<this.registeredNotificationTypes.length; i++) {
      if(this.registeredNotificationTypes[i].isEnabled()) {
        defaults.add((byte)i);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public NotificationType[] getRegisteredNotificationTypes() {
    return registeredNotificationTypes;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Byte[] getDefaults() {
    Byte[] returnArray = new Byte[defaults.size()];
    defaults.toArray(returnArray);
    return returnArray;
  }

}
