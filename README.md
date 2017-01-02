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






