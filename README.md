Notifications
=============
This project allows Java applications to send notifications to a service. Growl is the currently implemented service, allowing for remote Growl notifications. I use this package for long running server applications from which I want pushed updates. You know, no tailing the log and checking on it every so often.

Creating a notification able application is easy. You need to define three things: the notification service, your application, and the types of notifications to be sent.

To setup the growl service and add a receiving client:  
<code>
    GrowlServiceImpl service = new GrowlServiceImpl();  
    service.addClient(InetAddress.getByName("My-MBP.local"), 9887);
</code>

Port 9887 is the default port for Growl to listen on. Note that you must enable remote notifications under the Growl preference panel.

To define an application you need a name ("Test Me") and a list of valid NotificationTypes. In this instance there is just one type of notification, GrowTestType.TEST. I usually define the NotificationTypes in an enum.  
<code>
    Application app = new ApplicationImpl("Test Me", GrowlTestType.TEST);
</code>

And before the application can send notifications, it must be registered with the service:  
  `service.registerApplication(app);`

Now let's construct a notification titled "Testing" with the content "Growl Test" and send it to the client:  
<code>
    Notification note1 = new NotificationImpl(app, "Testing", "Growl Test", GrowlTestType.TEST);  
    service.sendNotification(note1);
</code>

That's it, that's all there is. You can define many different notification types with different priorities and Growl will allow you to manage the types that are displayed through the preference panel.

OSGI
----
Because I develop in an OSGI framework, I build these projects as bundles. Included is a features.xml for karaf. To get the feature use `features:addUrl mvn:net.davidwallen.notification/growl/1.0/xml/features`. The feature will be called `growl`.

A gogo command is also included for dynamically adding and removing growl clients. The command `growl:addHost hostname` will add a client for notifications on the Karaf command line.

Future
------
The intent behind this project is to provide multiple notification services such as XMPP or Email that an application can use for notifying users of actions such as startup, shutdown, exceptions, or job completion. If you have any ideas or want to contribute a new service implementation, let me know!