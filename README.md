# ocProjet9 Mediscreen
***
## Table of Contents
1. [General Info](#general-info)
2. [Technologies](#technologies)
2. [Installation](#technologies)
***

## General Info
Mediscreen specializes in detecting risk factors for disease.
Our screenings using predictive analysis of patient populations at affordable cost.
  - Are you small or rual clinic or practice ?
  - Do you need screenings for heart diseace or hyper tension precursors ?
We Have a solution for you .

## Technologies
* [JAVA]
* [PostgresSql]
* [MongoDb] 
* [Docker]
* [Angular]

***
## Installation
* git clone https://github.com/S-Buscato/ocProjet9.git

***
* **FRONT**
* > cd mediscreen/front 
* > npm install
* > ng serve

***
* **BACK**
* > cd mediscreen/back
* make each docker image for in each java project
* in patientApp run this command :
* >  docker build -t patientapp . 
* >  docker tag patientapp:latest patient:v1.0.0
***
* in noteApp run this command :
* >  docker build -t noteapp . 
* >  docker tag noteapp:latest note:v1.0.0

***
* in Mediscreen run this command :
* >  docker build -t  mediscreen .
* >  docker tag mediscreen:latest mediscreen:v1.0.0

***
* in Mediscreen-server run this command :
* >  docker build -t  mediscreen-server .
* >  docker tag mediscreen-server:latest mediscreen-server:v1.0.0

***
* then, run the docker compose in Mediscreen.

    
