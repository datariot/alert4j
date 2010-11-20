Alerts
=============
This project allows Java applications to send alerts to a service. Growl and XMPP are currently implemented services. I use this package for long running server applications from which I want pushed updates. You know, no tailing the log and checking on it every so often.

Creating an alert enabled application is easy. You need to define three things: the alert service, your application, and the types of alerts to be sent.

To setup the growl service and add a receiving client:
<code>
    GrowlServiceImpl service = new GrowlServiceImpl();  
    service.addClient(InetAddress.getByName("My-MBP.local"), 9887);
</code>

Port 9887 is the default port for Growl to listen on. Note that you must enable remote notifications under the Growl preference panel.

To define an application you need a name ("Test Me") and a list of valid AlertTypes. In this instance there is just one type of alert, GrowTestType.TEST. I usually define the AlertTypes in an enum.
<code>
    Application app = new ApplicationImpl("Test Me", GrowlTestType.TEST);
</code>

And before the application can send alerts, it must be registered with the service:
<code>
    service.registerApplication(app);
</code>

Now let's construct an alert titled "Testing" with the content "Growl Test" and send it to the client:
<code>
    Alert alert1 = new AlertImpl(app, "Testing", "Growl Test", GrowlTestType.TEST);  
    service.sendAlert(alert1);
</code>

That's it, that's all there is. You can define many different alert types with different priorities and Growl will allow you to manage the types that are displayed through the preference panel.

OSGI
----
Because I develop in an OSGI framework, I build these projects as bundles. Included is a features.xml for karaf. To get the feature use `features:addUrl mvn:net.davidwallen.alert4j/growl/1.0-SNAPSHOT/xml/features`. The feature will be called `growl`.

A gogo command is also included for dynamically adding and removing growl clients. The command `growl:addHost hostname` will add a client for alerts on the Karaf command line.

Future
------
The intent behind this project is to provide multiple alert services such as Email or phone push notifications that an application can use for notifying users of actions such as startup, shutdown, exceptions, or job completion. If you have any ideas or want to contribute a new service implementation, let me know!