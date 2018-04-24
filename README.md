This Api is written for implementing:

1) To find nearest ’n' charging points to the current location. The user will supply the number ’n’, the latitude, and the longitude
 of the user. The end point will return a JSON document with the name of the charging point(s), and their longitude(s) and latitude(s).

2) The API should be able able to bulk upload data from a CSV in the format provided

3) The API should be able to amend a specific charging point by calling an end point



You should upload a csv file. First five column should be deviceId,reference, name,latitude,longitude

I implemented a service accepting a byte array for different types of csv files in BulkUploadController. For mapping the csv rows to models, I used opencsv library.

I implemented end points for CRUD operations for a single charging point.

I created validators to validate API models such as latitude not null,  -90<latitude<90, longitude not null, -180<latitude<180, charge device id not null etc.

I used mockito for mocking

For general exception handling, I wrote a custom exception handling class

I put all messages in messages.properties file.

I checked input if [lat,long] point already exists for a different charging device. I assumed there exists only one charging point at that location.

I used swagger for invoking my end points. You can access it http://localhost:8080/swagger-ui.html

I used modelmapper for mapping entity objects and ui models

For logging, I used slf4j library.