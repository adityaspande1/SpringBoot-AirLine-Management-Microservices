
# SpringBoot-AirLine-Management-Microservices

A Airline Ticket Booking System built using Spring Boot Microservices, featuring Flight Service, Ticket Service, and User Service.





![Java](https://camo.githubusercontent.com/1f03f673676cd4c4e16b900f2000b9c1e6d406416588c34f464467e73ad00fdf/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f4a6176612d32312d6f72616e6765)

![Maven](https://camo.githubusercontent.com/a4f4800486ffb2f891d3336b1f282292422a88d5f20f73d3fb0ca9459cae6aec/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f4d6176656e2d332e382e312d626c7565)

![Spring Boot](https://camo.githubusercontent.com/c7fe534a16fe8407f70384c915b099645cdddfee9a6d8415a690b72c8bfab848/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f537072696e67253230426f6f742d332e342e332d677265656e)
## API Reference

### User Service

Base URL: `http://localhost:8082`

#### Get Scheduled FLights



| End Point | Method   | Description                |
| :-------- | :------- | :------------------------- |
| `/flights`| `GET` | Get All scheduled flights|
| `/flights/{flightNumber}`| `GET` | Get Specific Flight|
| `/book`| `POST` | Book ticket in a flight|
| `/ticket/{id}`| `GET` | Get Specific Ticket Details|
| `/ticket/{id}`| `DELETE` | Cancle Specific Ticket|


User Can use the other 2 services which are
Flight Service and ticket service throught User Service and these services communicate under the hood through each other.




