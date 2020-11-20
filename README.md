# Kotlin Selenium Framwork

### Introduction
This is the attempt of someone coming from a Python background, switching to something that would run on the JVM, Kotlin.
This framework includes:
- Maven
- JUnit 5
- Allure reporting
- Page Object Model (POM) structure

### Requirements
To run this locally, follow these steps:
1. Clone this repo: `git clone XXXX` and `cd kotlin-selenium-framework`
2. Start Zalenium Grid: `docker-compose up -d`
3. [Access your local Admin Grid](http://localhost:4444/grid/admin/live) 
4. Run: `mvn test -Dbrowser=chrome` -- You can run immediatly on multiple browsers by separating them with a comma: `-Dbrowser=chrome,firefox`
5. [See the result and video](http://localhost:4444/dashboard)
6. Check the report with `allure serve PATH_TO_OUTPUT` 

To be able to run the commands above you'll need to have installed:
- Docker
- Docker-Compose
- Allure (to see the results locally)
- Your usual development environment. This may imply you to change some settings in this code.



