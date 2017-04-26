# event-souring-kafka

This project is based on event bus concept. It has four modules.
    # event-app
    # event-domain
    # event-infrastructure
    # event-web


   event-app is the brain of the project and contains all service implementation.
   It works on in memory database h2 and the communication happens via active MQ.

   1) start ApplicationApiBootstrap in org.springboot.eventbus.starter to start the services.

   2) Down load apache-activemq and  start ActiveMQ so that communication can happen via activeMQ.

   3) Start WebAppInitializer  in org.springboot.eventbus.web.starter package.

   4) Use http://localhost:8080/ to access the web page.

   MVC Exception Handling :https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc#sample-application
   http://www.baeldung.com/exception-handling-for-rest-with-spring


Event Bus with ActiveMQ and Camel


Pros :

    1) We can take advantage of strong routing and mediation capability provided out of box by camel.
    2) We can use all the integration design patterns as camel implements them quite nicely.
    3) We can shift from ActiveMQ to any other message provider with very less or negligible code change.

Cons:


    1) Camel components have their own dependencies and we have to be careful using third party tools which may have a conflict with these versions.
    2) With this approach, we will have to rely on MOM provider like ActiveMQ/Kafka for guarantee message delivery.
    3) We have to rely on third party tools like Jolokia or ActiveMQ console to monitor the message traffic.
    4) Message consumption will be done  round robin algorithm and we have to depend on the provider's implementation.
    5) Events will be broadcasted and whichever listener available will consume the message. Though we can mitigate this with the reliable consumer.


Issue to be address In the Poc:


      1) When only one topic is used as a message channel
      2) All the component registered with event bus will get a copy of the event but  the one which has the handler in his registry will handle it and rest listener will ignore it. The issue will come when we deploy  it in the cluster where we have two handlers eligible to handle the event and will be picked by both. It can be avoided by using a global store like hazelcast, which provides global map and lock and using these it can be handled.
      3) Another issue with this approach is we can’t use request/reply model.


Separate request response Queue:

    1) It resolves the multiple handler activation issues as JMS queue implementation ensure only one consumer can consume a message.
       Routing can be handled by camel.
    2) We need to have separate request and response queue for each module, otherwise, if we use a single queue for all the modules then the message can be consumed by any of the modules and it will throw an exception as it may or may not have the handler in its registry to handle the event.
       This issue can be resolved by using a global registry but then we need the handler classes to be present in all the modules which are not possible.
    3) We can put filter based on host name. But then it has to be part of the message. The host name can be added to the response/request event by the event producer.
       Event consumer will have this filter set at the listener end.