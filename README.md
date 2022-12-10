# Binotify SOAP Service

Binotify SOAP Service is an end-to-end service for Binotify's subscription services.

Made with love by

|              Name              |   NIM    |
| :----------------------------: | :------: |
| Muhammad Garebaldhie ER Rahman | 13520029 |
|        I Gede Arya Raditya Parameswara        | 13520036 |
|      Arik Rayi Arkananta       | 13520048 |

## Functionality
|              Function              |   Description    |
| :----------------------------: | :------: |
|       subscribe          | Subscribe to artist |
|       checkStatus        | Check status binotify user to artist |
|       fetchPendingSubscription    | Fetch all pending subscription |
|       getAcceptedSubscriptionBySubcriptionId | Fetch binotify user's accepted subscription |
|       acceptSubscription        | Accept binotify user's request subscription |
|       rejectSubscription        | Reject binotify user's request subscription |

## Requirement list

1. Docker
2. Java 8
3. Maven 8 Corretto

## Installation

1. Install requirements

   - For windows and mac user

     - Download docker desktop [here](https://www.docker.com/products/docker-desktop/)

   - For UNIX like user run commands below

   ```sh
    sudo apt-get update
    sudo apt-get install docker-ce docker-ce-cli containerd.io docker-compose-plugin
   ```

   To verify if docker is already installed run with `docker run hello-world` and for UNIX users don't forget to add `sudo`

2. Clone this repository
3. By default this application use port `9000` and if your computer already use the port please change it in `docker-compose.yml` file and you can refer to guide in [here](https://docs.docker.com/compose/gettingstarted/)

## How to run

1. Change directory to the clonned repo
2. Create `.env` file by using the example
3. Run `docker compose up -d`

## How are the tasks divided?

| Muhammad Garebaldhie ER Rahman |    I Gede Arya R. P    | Arik Rayi Arkananta        |
| ------------------------------ | :--------------------: | -------------------------- |
| subscribe|checkStatus|fetchPendingSubscription|
| getAcceptedSubscriptionBySubcriptionId|||
| acceptSubscription|||
| rejectSubscription|||

