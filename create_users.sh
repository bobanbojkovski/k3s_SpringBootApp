#!/usr/bin/env bash

# run like ./create_users.sh <fqdn>
# example: ./create_users.sh dev.example.com

fqdn="${1}"
for i in {1..5}; 
do 
   username=$(curl --silent 'https://frightanic.com/goodies_content/docker-names.php');
   password=$(tr -cd '[:alnum:]' < /dev/urandom | fold -w32 | head -n1;echo;);
   curl -X POST -H 'Content-Type: application/json' -i http://"${fqdn}"/users/create --data '{"username":"'"${username}"'","password":"'"${password}"'"}';
   echo;
done
