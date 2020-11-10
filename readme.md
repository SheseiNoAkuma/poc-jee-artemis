# Jboss artemis message broker example

nella classe jms.Constants cambia le seguenti costanti:
- **QUEUE**: la coda sulla quale desideri scrivere e leggere
- **CONNECTION_FACTORY_JNDI**: il jndi della queue connection factory 
- **CONNECTION_FACTORY_NAME**: il name della queue connection factory

## Consumer

jms.HelloWorldQueueMDB consumerà automaticamene tutti i messaggi che arrivano sulla coda indicata

## Producer 

Collegandoti all'endpoint http://localhost:8080/jms-example/HelloWorldMDBServletClient verranno prodotti 5 messaggi e spediti sulla coda indicata

## Build 

mvn clean install
