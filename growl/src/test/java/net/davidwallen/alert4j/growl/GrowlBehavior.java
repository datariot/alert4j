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
package net.davidwallen.alert4j.growl;

import net.davidwallen.alert4j.growl.impl.GrowlServiceImpl;
import java.net.InetAddress;
import java.net.UnknownHostException;
import net.davidwallen.alert4j.Application;
import net.davidwallen.alert4j.ApplicationImpl;
import net.davidwallen.alert4j.Notification;
import net.davidwallen.alert4j.NotificationImpl;
import net.davidwallen.alert4j.NotificationType;

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
