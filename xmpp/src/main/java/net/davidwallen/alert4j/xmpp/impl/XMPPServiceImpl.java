package net.davidwallen.alert4j.xmpp.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.davidwallen.alert4j.Application;
import net.davidwallen.alert4j.Alert;
import net.davidwallen.alert4j.xmpp.XMPPService;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Property;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Validate;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An XMPP service implementation. This implementation will send alerts but any messages
 * sent back tot the service go into a black hole. In the future, perhaps commands can be sent
 * via messages for interacting with the service.
 *
 * @author David W. Allen <david.w.allen@me.com>
 */
@Component
@Provides
public class XMPPServiceImpl implements XMPPService, MessageListener {

  private final Logger logger = LoggerFactory.getLogger(XMPPService.class);
  private static final String serviceName = "XMPP";
  private XMPPConnection connection;
  private Map<String, Chat> users = new HashMap<String, Chat>();
  private Set<Application> apps = new HashSet<Application>();
  @Property
  private String username;
  @Property
  private String password;
  @Property
  private String server;
  @Property
  private int port;
  @Property
  private String service;

  /**
   * Default constructor used for iPojo
   */
  public XMPPServiceImpl() { }

  /**
   * Constructor for injecting parameters.
   * @param username the account username.
   * @param password the account password.
   * @param server the jabber server.
   * @param port the jabber server port.
   * @param service the server domain
   */
  public XMPPServiceImpl(String username, String password, String server, int port, String service) {
    this.username = username;
    this.password = password;
    this.server = server;
    this.port = port;
    this.service = service;
  }

  /**
   * {@inheritDoc}
   */
  @Validate
  @Override
  public final void login() throws XMPPException {
    ConnectionConfiguration config = new ConnectionConfiguration(server, port, service);
    connection = new XMPPConnection(config);
    connection.connect();
    connection.login(username, password);
    logger.debug("Authenticated: {}",connection.isAuthenticated());
  }

  /**
   * {@inheritDoc}
   */
  @Invalidate
  @Override
  public final void disconnect() {
    connection.disconnect();
  }

  @Override
  public void processMessage(Chat chat, Message msg) {
    logger.debug("Recieved a message, sending it to the bit bucket.\n{}", msg.getBody());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final String getName() {
    return serviceName;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void registerApplication(Application application) {
    apps.add(application);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void sendAlert(Alert alert) {
    if(apps.contains(alert.getApplication())) {
      for(String user : users.keySet()) {
        try {
          Chat chat = users.get(user);
          StringBuilder builder = new StringBuilder();
          builder.append(alert.getApplication().getName()).append('\n');
          builder.append(alert.getMessage());
          chat.sendMessage(builder.toString());
        } catch(XMPPException ex) {
          logger.error("Problem sending message.", ex);
        }
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void registerUser(String username) {
    Chat chat = connection.getChatManager().createChat(username, this);
    users.put(username, chat);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void removeUser(String username) {
    users.remove(username);
  }
}
