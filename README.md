
# immergas-bot-login project

Questo progetto e' basato su Quake, framework di sviluppo per microservizi.

Ogni nodo applicativo e' un'applicazione [https://quarkus.io][Quarkus] che comunica con gli altri nodi tramite Vert.x
EventBus o chiamate REST.
E' necessario sia presente almeno un nodo Quake per fornire i servizi di base ai nodi applicativi quali:
- Config
- Dataset

Per l'autenticazione viene usato Keycloak che fornisce i Token JWT da passare nelle chiamate REST ai servizi.

Il progetto base contiene un ambiente preassemblato con:
- DB Postgres
- Quake
- Keycloak
- Reverse proxy

Il reverse proxy viene usato per esporre su https://localhost tutti i servizi dell'ambiente e l'applicazione locale in
modo che siano tutti sotto lo stesso protocollo, dominio e porta

Eseguendo l'ambiente vengono anche compilati e lanciati in modalita' Dev i servizi del progetto corrente. Questi
sono eseguiti nei container in modo da poter condividere la stessa rete virtuale dei servizi dell'ambiente e ricreare
cosi' una situazione piu' simile possibile a quella reale.

## Esecuzione dell'ambiente di sviluppo

Per vedere le ultime versioni disponibili di Quake con relative release notes confrontare il documento: https://sites.google.com/quix.it/teamrd/quake

Dalla cartella principale del progetto eseguire:
```
docker-compose up -d
```

Per fermare tutti i servizi:
```
docker-compose stop
```

Per far ripartire i servizi:
```
docker-compose start
```

Per eliminare tutti i container (i dati del DB vengono mantenuti):
```
docker-compose down --remove-orphans
```

Per eliminare tutti i container e i dati:
```
docker-compose down --remove-orphans --volumes
```

I log dei singoli container sono accessibili con:
```
docker logs immergas-bot-login_<servizio>_1
```
dove `servizio` puo' avere i seguenti valori:
- postgres
- auth
- quake
- service
- reverse-proxy

Tutte le funzioni di avvio, stop, creazione, log, mappature porte etc... sono anche accessibili da IntelliJ nella Tool
Window Services -> Docker e usando le azioni direttamente aprendo il `docker-compose.yaml`

Il Reverse Proxy prevede gia' una regola che punta ad una eventuale UI Angular in esecuzione su `localhost:4200`


## Endpoints

Una volta eseguito l'ambiente saranno utilizzabili le seguenti URL:
- https://localhost/bot-login con la UI del progetto (da eseguire a parte e se presente)
- https://localhost/bot-login/q/swagger-ui l'interfaccia web per i servizi REST
- https://localhost/quake con la UI di Quake (administrator:admin)
- https://localhost/auth con il pannello di configurazione di Keycloak (kadmin:kadmin)


## CI/CD

Ad ogni push sul branch develop viene lanciata in automatico una compilazione completa con deploy maven sul Nexus.

Per attivare la scansione con Sonar mettere il login code del progetto sonar all'interno del Sonar Mave Plugin nel pom.xml
e aggiungere alla pipeline il comando:

`mvn -P sonar verify sonar:sonar`

Nota: la scansione con Sonar puo' richiedere 30/40 minuti, e' sconsigliato farla ad ogni push

Per la Continuous Delivery va creata una pipeline a parte sul repo di GitOps


## Pre-requisiti

Per l'esecuzione dell'ambiente e dei servizi del progetto e' necessario avere installato Docker e Docker Compose.
Assicurarsi di fare la login al repo aziendale con le proprie credenziali di dominio:

```
docker login docker.quix.it
```
