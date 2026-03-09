#!/bin/bash
set -e
echo " "
echo -e "\e[1;33m[\e[0m\e[1;34mDERRUBANDO CONTAINERS\e[0m\e[1;33m]\e[0m"
docker compose down

echo " "
echo -e "\e[1;33m[\e[0m\e[1;34mCOMPILA TODO O PROJETO\e[0m\e[1;33m]\e[0m"
mvn clean install -U

echo " "
echo -e "\e[1;33m[\e[0m\e[1;34mBUILD DOCKERS\e[0m\e[1;33m]\e[0m"
docker compose build --no-cache

echo " "
echo -e "\e[1;33m[\e[0m\e[1;34mSUBINDO CONTAINERS\e[0m\e[1;33m]\e[0m"
docker compose up -d --remove-orphans
