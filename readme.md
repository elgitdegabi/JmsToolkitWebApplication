# JmsToolkitWeb #
## Disclaimer ##
This is not a commercial or an administrative tool. It was development for test purpose only so doesn't have any warranty.

Feel free to test, use and/or modify.

For more details see the LICENSE file.

## Description ##
A few week ago I had a requirement to create a message producer for a TIBCO-EMS Topic. It was a very simple task.

The problem started when I needed to test if the sent messages were on the Topic... and I didn't have access to the Topic's Admin console.

So I started to research how to create a message producer but also a message consumer. I founded a lot of examples on Internet but most of them depended of the queue/topic implementation and they didn't use JMS interfaces.

Finally, I created a producer/consumer JAVA program for the TIBCO-EMS implementation.
My co-workers found it very useful to test the sent and received messages and asked to me if I could "configure" for IBM-MQ queues.

The original program became in a Spring Boot Application that removes the particular implementations and uses JMS interfaces for several operations.
The next step was transform the local project in a Web Application with a very simple (and ugly) interface to test the send, browse/consume and remove operations on different configured topics and queues: the JmsToolkitWeb :)
 
## Features ##
* The application uses JAVA JMS interfaces so you don't depend of the implementation of the queue/topic provider
* You only need to create a JMS ConnectionFactory Bean using the Connection Factory provided by you selected queue/topic driver.
* You don't need access to Administrator console to perform operation with the messages
* Includes Restful End-points for different queue/topic operations
* Creates initial topic subscribers for the configured topics when the Application starts (you don't need to create through the Administrator console)
* Developed with Spring Boot Application 1.5.3.RELEASE version (and also tested with 1.4.2.RELEASE version)
* Configured by default to use Apache Active-MQ
* Tested with TIBCO-EMS, IBM-MQ and Apache ActiveMQ providers.

## Configuration ##
### TIBCO-EMS ###
* add the Maven dependency to pom.xml
* create a Bean for the JMS ConnectionFactoring using new com.tibco.tibjms.TibjmsConnectionFactory(url)
* set the specific values using a JmsTemplate and set the ConnectionFactory to this JmsTemplate.
* instantiate the JmsTemplate to get the ConnectionFactory in your services (for example, use @Autowired annotation) 
### IBM-MQ ###
* add the Maven dependency to pom.xml
* create a Bean for the JMS ConnectionFactoring using new com.ibm.mq.jms.MQConnectionFactory();
* set the specific values for the MQ Connection Factory.
* instantiate the ConnectionFactory in your services (for example, use @Autowired annotation)
### Apache Active-MQ ###
* add the Maven dependency to pom.xml
* create a Bean for the JMS ConnectionFactoring using new ActiveMQConnectionFactory();
* set the specific values for the MQ Connection Factory.
* instantiate the ConnectionFactory in your services (for example, use @Autowired annotation)

## Restful End-points ##
The HTML interface is very ugly but I did it faster for test purpose so feel free to change or improve it.
### Send a message: ###
* Select a configured resource (queue or topic) and send a message
* End-point: http://localhost:8080/send.html
### Browse Queue messages:  ###
* Select a configured resource (queue or topic) and browse for the messages
* For the queues, use the JMS browse object. Maybe you can add a service to consume the messages.
* For the topics, consume the messages (topics doesn't have a JMS browse object). Maybe you can rewrite the service to consume and send again the messages (to simulate the browse action) and add another service only to consume the topic's messages 
* End-point: http://localhost:8080/browse.html
### Purge Queue/Topic messages: ###
* Select a configured resource (queue or topic) and purge the messages (for the topics removes the messages from the all the subscribers created by the application -not by the topic administrator console-)
* End-point: http://localhost:8080/purge.html

## License ##

JmsToolkitWebApplication is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

JmsToolkitWebApplication is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with JmsToolkitWebApplication.  If not, see <http://www.gnu.org/licenses/>.

## GitHub repository: ##
#### https://github.com/Gabotto/JmsToolkitWebApplication ####
## Contact ##
Let me know if you have any problem, comment or new ideas:
#### Upwork: https://www.upwork.com/o/profiles/users/_~01b674725ab8f5ce8e/ ####
#### Wordpress: http://gabelopment.wordpress.com/ ####
#### Email: gabelopment@gmail.com ####

Edited on: 4th June 2017
 
