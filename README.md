# Jsuc ☕
A java CLI-Tool for various subnet calculation features with saveable history.

## Installation 👾
Unix/Linux🐧:
  - Download jsuc.jar from repo
  
  - Add the folling line to .bashrc / .zshrc / config.fish
       
       ``` fish
       alias jsuc = "java -jar /home/user/path/to/jar"
       ```
       
## Usage 🔬
```
jsuc [-h] [COMMAND]
Subnet Calculator
  -h, --help   display a help message
Commands:
  info       displays calculated network data
  save       save network to history
  history    displays saved history
  reset      resets the saved history
  addHeader  adds a custom header to the history
  contains   Checks if IpAddress is contained in NetworkAddress / Subnetmask
```

## Examples 🧪
- Calculate all details:
  ```
  jsuc info 192.168.0.1 255.255.255.0
  ```
- Save Network to history
  ```
  jsuc save 192.168.0.1 255.255.255.0
  ```
- Check if ip is in network
  ```
  jsuc contains 192.168.0.1 255.255.255.0 192.168.0.10
  ```


