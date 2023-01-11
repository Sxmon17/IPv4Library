# Java Subnet Calculator ☕

## A command-line subnet calculator written in Java.

## Features 
   ⭐ Calculates the subnet mask, broadcast address, and range of addresses for a given network
 
   ⭐ Can handle netmask notation (e.g. 192.168.0.0 255.255.255.0). CIDR coming soon!
   
## Usage 👾

To run the subnet calculator, use the following command:

    $ java -jar jsuc.jar [IP ADDRESS] [NETMASK/CIDR]

For example:

    $ java -jar jsuc.jar 192.168.0.1 255.255.0.0

## Building 🤖

To build the project, you will need Java and Maven installed on your machine.

Clone the repository and navigate to the root directory:

    $ git clone https://github.com/sxmon17/jsuc.git
    $ cd jsuc

Then run the following command to build the project:

    $ mvn package

This will create a jar file in the target directory that you can use to run the subnet calculator.
Contributing

If you find a bug or have an idea for a new feature, please open an issue or submit a pull request.
