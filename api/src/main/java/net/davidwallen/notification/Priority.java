package net.davidwallen.notification;

/**
 *
 * The priority of a notification can be set by the following enum.
 *
 * @author David W. Allen <david.w.allen@me.com>
 */
public enum Priority {

  LOW((byte)-2),
  MODERATE((byte)-1),
  NORMAL((byte)0),
  HIGH((byte)1),
  EMERGENCY((byte)2);

  private byte value;

  Priority(byte value) {
    this.value = value;
  }

  public byte getPriorityValue() {
    return value;
  }

}
