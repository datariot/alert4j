package net.davidwallen.notification.growl;

import net.davidwallen.notification.ApplicationImpl;
import net.davidwallen.notification.NotificationImpl;
import net.davidwallen.notification.Application;
import net.davidwallen.notification.Notification;
import net.davidwallen.notification.NotificationType;
import net.davidwallen.notification.growl.impl.GrowlServiceImpl;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Unit test for simple App.
 */
public class GrowlBehavior {

  public static void main(String... args) throws UnknownHostException {

    GrowlServiceImpl service = new GrowlServiceImpl();
    service.addClient(InetAddress.getByName("localhost"), 9887);

    Application app = new ApplicationImpl("Test Me", GrowlTestType.TEST_START, GrowlTestType.TEST_END);
    service.registerApplication(app);

    Notification note1 = new NotificationImpl(app, "Starting", "Starting the test app", GrowlTestType.TEST_START);
    Notification note2 = new NotificationImpl(app, "Ending", "Ending the test app", GrowlTestType.TEST_END);

    service.sendNotification(note1);
    service.sendNotification(note2);

  }

  private enum GrowlTestType implements NotificationType {

    TEST_START("Start", true),
    TEST_END("End", true);

    private String name;
    private boolean enabled;

    GrowlTestType(String name, boolean enabled) {
      this.name = name;
      this.enabled = enabled;
    }

    @Override
    public String getTypeName() {
      return this.name;
    }

    @Override
    public boolean isEnabled() {
      return this.enabled;
    }

  }
}
