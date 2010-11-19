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
package net.davidwallen.alert4j;

/**
 * A notification to be sent to Growl.
 *
 * @author David W. Allen <david.w.allen@me.com>
 */
public class AlertImpl implements Alert {

  private final Application app;
  private final String title;
  private final String message;
  private final AlertType type;
  private Priority priority = Priority.NORMAL;
  private boolean sticky = false;

  /**
   * Constructor for a notification.
   * @param app The application sending the notification.
   * @param title The title of the AlertImpl.
   * @param message The message body of the AlertImpl.
   * @param type The notification type. The type must be registered with the app before use.
   */
  public AlertImpl(Application app, String title, String message, AlertType type) {
    this.app = app;
    this.title = title;
    this.message = message;
    this.type = type;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final Application getApplication() {
    return this.app;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final Priority getPriority() {
    return priority;
  }

  /**
   * {@inheritDoc}
   */
  public final void setPriority(Priority priority) {
    this.priority = priority;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean isSticky() {
    return sticky;
  }

  /**
   * Sets the notification as sticky.
   */
  public final void makeSticky() {
    sticky = true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final String getMessage() {
    return message;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final String getTitle() {
    return title;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final AlertType getType() {
    return type;
  }

}
