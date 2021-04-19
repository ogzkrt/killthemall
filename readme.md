![Build Workflow](https://github.com/ogzkrt/killthemall/actions/workflows/gradle.yml/badge.svg)



# Table of Contents

* [Game](#killthemall)
* [Installation](#installation)
* [Architecture](#architecture)


# Killthemall

Killthemall is a multiplayer game written with Java by using LibGDX and Kryonet. As of today, game only supports desktop. It has been tested with Linux, MacOS and Windows. Reccomended Java version for this game is 1.8.

Here is the gameplay.

![killthemall](https://user-images.githubusercontent.com/6486180/112219522-2a41a580-8c36-11eb-94f8-d14932747d5a.gif)

Game is so simple, but it can be used as a starting point to develop something more complex. Right now, players only can do following things.
- Move with W,A,S,D
- Shoot Circles to heal
- Shoot other players to win.


## Installation

### Run the game with Jars

If you just want to play the game without touching the source code. You can just download the jars from /jars folder.

Before running, make sure that you have Java installed.

First run the server with this command:

```bash
java -jar server.jar
```
To run a client execute this command:

```bash
java -jar client.jar <server_ip_address>
```
İf your server is not running on the same network, make sure that you have enabled the port forwarding on the host machine:
- **TCP port 1234**
- **UDP port 1235**

### Working with source code

Clone this project with following command

```bash
git clone https://github.com/javakaian/killthemall.git
```
Import the project as a gradle project in IDE of you choice then you are ready to go !


## Architecture

This game follows Authoritative server approach. Which means, clients sends their every move to the server, servers does the validity check and then updates their status.

![diagram](https://user-images.githubusercontent.com/6486180/112293306-0e281d80-8ca3-11eb-838d-dd2b0fbad063.png)


This games still lacks features like

- **Client-Side Prediction**
- **Server Reconciliation**
- **Entity Interpolation**

If you want to learn more about these stuff. Please visit the awesome blog by Gabriel Gambetta [here](https://www.gabrielgambetta.com/client-side-prediction-server-reconciliation.html)




## Contributing

If you find an error please create an issue. Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.


## License
[MIT](https://choosealicense.com/licenses/mit/)


