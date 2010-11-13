package net.davidwallen.notification.growl.gogo;

import net.davidwallen.notification.growl.GrowlService;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.ServiceProperty;
import org.osgi.service.command.Descriptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This Class holds commands to use with the Growl service.
 *
 * @author David W. Allen <david.w.allen@me.com>
 */
@Component(public_factory = false, immediate = true)
@Provides(specifications = GrowlCommand.class)
public class GrowlCommand {

  private Logger logger = LoggerFactory.getLogger(GrowlCommand.class);
  /**
   * Defines the command scope (ipojo).
   */
  @ServiceProperty(name = "osgi.command.scope")
  String m_scope = "growl";
  /**
   * Defines the functions (commands).
   */
  @ServiceProperty(name = "osgi.command.function")
  String[] m_function = new String[]{
    "addHost",
    "removeHost"
  };
  @Requires
  private GrowlService service;

  /**
   * Adds a host to the notification list.
   * @param host the hostname of a recipient.
   * @param port the port of the host. Default is 9887 if ommited
   */
  @Descriptor(description = "Adds a host to the notification list.")
  public void addHost(@Descriptor(description = "hostname") String host,
      @Descriptor(description = "port") Integer port) {
    if (null == port) {
      port = 9887;
    }
    try {
      logger.info("Registering {}", host);
      InetAddress address = InetAddress.getByName(host);
      service.addClient(address, port);
    } catch (UnknownHostException ex) {
      logger.error("Hostname not found {}", host, ex);
    }
    logger.info("Host registered {}:{}", host, port);
  }

  /**
   * Removes a host to the notification list.
   * @param host the hostname of a recipient.
   */
  @Descriptor(description = "Removes a host from the notification list.")
  public void removeHost(@Descriptor(description = "hostname") String host) {
    try {
      logger.info("Removing {}", host);
      InetAddress address = InetAddress.getByName(host);
      service.removeClient(address);
    } catch (UnknownHostException ex) {
      logger.error("Hostname not found {}", host, ex);
    }
    logger.info("Host removed {}:{}", host);
  }
}
