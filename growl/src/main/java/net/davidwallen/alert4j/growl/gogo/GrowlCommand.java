/**
 * (c) 2009, David W. Allen
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of David W. Allen nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL David W. Allen BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.davidwallen.alert4j.growl.gogo;

import net.davidwallen.alert4j.growl.GrowlService;
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
  private String m_scope = "growl";
  /**
   * Defines the functions (commands).
   */
  @ServiceProperty(name = "osgi.command.function")
  private String[] m_function = new String[]{
    "addHost",
    "removeHost"
  };
  @Requires
  private GrowlService service;

  @Descriptor(description = "Adds a host to the notification list.")
  public void addHost(@Descriptor(description = "hostname") String host) {
    this.addHost(host, 9887);
  }

  /**
   * Adds a host to the notification list.
   * @param host the hostname of a recipient.
   * @param port the port of the host. Default is 9887 if ommited
   */
  @Descriptor(description = "Adds a host to the notification list.")
  public void addHost(@Descriptor(description = "hostname") String host,
      @Descriptor(description = "port") Integer port) {
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
