# BachelorProefBackend

This project was created as a bachelor thesis from three students industrial engineering, electronics - ICT.
You can find the repository for the corresponding web application here:
And the corresponding mobile application here: 

This code contains a connection to a PostgreSQL database and implements URLs to implement most CRUD operations on the data conveniently.
Furthermore it allows the user to log in and implements multiple security checks based on the login information contained in de JSON Web Token.

You can run the code as is, then you will connect to our online database hosted by Heroku. You can send request to the application on http://localhost:8081/
If you run the code localy it is a better idea to install your own Postgre database. This tutorial will help you along: https://www.youtube.com/watch?v=Nf2_QWh-fik
You can fill in the credentials in the file resources/application.properties. The localhost database is currently marked as a comment.

