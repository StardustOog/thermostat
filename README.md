Your task is to develop a Smart Thermostat management system that allows users to manage their
thermostats, as well as viewing temperature information for each device. To generate temperature
data, you need to develop a console-based simulator that generates random temperature values. If the
temperature exceeds the configured degree value, the device must be marked as critical.
To implement the system, you need to create a Spring Boot project with the necessary dependencies,
including Spring Security, and Spring Data JPA. Using REST API endpoints, you should implement
device management functionality that allows users to create and delete thermostats and view
temperature information per device. The temperature data should be stored in a database along with
the thermostat devices.
To secure the API, you should use Spring Security, which allows only authenticated users to perform
CRUD operations on the thermostat devices.
Additionally, you can implement a dashboard to display temperature information for all thermostat
devices, as well as a feature that allows users to set temperature thresholds for each device and receive
alerts when these thresholds are exceeded. However, these features are optional.
In summary, your implementation should meet the following requirements:
1. Create a Spring Boot project with the required dependencies.
2. Implement device management functionality using REST API endpoints, including the ability
   to create, update and delete thermostats, and view temperature information per device.
3. Implement a simple simulator on a console application that will throw randomly generated
   temperature values to the server.
4. Secure the API using Spring Security, and allow only authenticated users to perform CRUD
   operations on the thermostat devices.
   And, completing the following bonus requirements will demonstrate a higher level of skill and
   expertise:
5. Implement a dashboard to display temperature information for all thermostat devices.
6. Implement a feature to allow users to set temperature thresholds for each device, and show
   alerts for the user when these thresholds are exceeded.


WE CAN CALL AND I'LL SHOW YOU EVERYTHING BY MYSELF.


Running project
1) Start up docker
2) run in this project's root : docker-compose up
3) create database named smart_thermostat and scheme named smartthermostats
4) start the project

To test this project you'll have to use postman(or other tool) because I had no time for frontend(wrote this in 1.5 day)
and swagger with spring security isn't an easy thing :D ^_^

Registration:
1) Send post request to localhost:8080/register with json like this:
{ 
"username":"example",
"password":"examplepassword",
"email":"exampleemail@gmail.com"
}
ENTER YOUR MAIL!
2) You'll get jwt(valid for 5-7 mins) as a response and 4 symbol validate code on your mail. You send jwt as a header named 
"jwt-token-for-verification" and you send verification code as request parameter(named code) on url 
localhost:8080/register/verify the final url with request parameter should look like this example:
localhost:8080/register/verify?code=GKA7  and hopefully now you are registered. 
3) Now you have to authenticate on url: localhost:8080/authenticate with this kind of json
{
"username":example
"password":examplepassword
}
You'll get you jwt which is valid for 1 day. We are going to authenticate with that jwt from now on everywhere else
remember to use Bearer token type authentication(select it in postman) and paste your given jwt in the give space u'll see.
4) Now you have running application and urls: "/thermostats", "/thermostats/add-thermostat", "/thermostats/set-threshold",
"/thermostats/critical". don't forget to use Bearer token authentication type and you jwt to access these urls. 
To see the json structures to be sent on those url's look into dto package.
5) This is it! Temperatures will be changed in every 20 mins. If there is critical condition u'll get email notification.




