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
package net.davidwallen.alert4j.xmpp.gogo;

import net.davidwallen.alert4j.xmpp.XMPPService;
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
@Provides(specifications = XMPPCommand.class)
public class XMPPCommand {

  private Logger logger = LoggerFactory.getLogger(XMPPCommand.class);
  /**
   * Defines the command scope (ipojo).
   */
  @ServiceProperty(name = "osgi.command.scope")
  private String m_scope = "xmpp";
  /**
   * Defines the functions (commands).
   */
  @ServiceProperty(name = "osgi.command.function")
  private String[] m_function = new String[]{
    "addUser",
    "removeUser"
  };
  @Requires
  private XMPPService service;

  @Descriptor(description = "Adds a user to the notification list.")
  public void addUser(@Descriptor(description = "username") String user) {
    logger.info("Registering {}", user);
    service.registerUser(user);
  }

  /**
   * Removes a host to the notification list.
   * @param host the hostname of a recipient.
   */
  @Descriptor(description = "Removes a user from the notification list.")
  public void removeUser(@Descriptor(description = "username") String user) {
      logger.info("Removing {}", user);
      service.removeUser(user);
  }
}
