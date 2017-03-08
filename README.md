<p align="center">
  <img src="http://www.gregoriopalama.com/wp-content/uploads/2016/06/cloud.png" alt="Spring Cloud Netflix"/>
</p>

##Lab 10 - Applying Security to Cloud Applications

  **Part 1 - Startup**

1.  Stop ALL of the services that you may have running from previous exercises.  If using an IDE you may also wish to close all of the projects that are not related to "lab-10”.

2.  Run the lab-10-config-server.  Run the microspring11-cloud-client-lab10.  Access the client at [http://localhost:8002/lucky-word](http://localhost:8002/lucky-word) to see the lucky word.  This is essentially the result of what we achieved in Lab 3.


  **Part 2 - Securing the Config Server**

3.  Stop both services.

4.  Open microspring11-cloud-server-lab10.  Open the POM, add another dependency for spring-cloud-security.

5.  Create a bootstrap.yml in the classpath root (src/main/resources).  Add a key for “encrypt.key”.  Use any value you like (such as an obscure value like “key”).

6.  Save your work and run the microspring11-cloud-server-lab10.  Observe the console output and obtain the generated password.  Copy it.

7.  Access the config server at [http://localhost:8001/anyapp-anyprofile.yml](http://localhost:8001/anyapp-anyprofile.yml).  You should be prompted for a user and password.  Enter “user” then the generated value for password. You should receive some YAML output. If so, you have successfully enabled HTTP Basic security and encryption in the config server.

  **Part 3 - Encrypt**

8.  Encrypt a password to be used by the config server:  Using a REST client, or the “curl” command on linux/unix, POST the string “password” (or some other some password value) to http://localhost:8001/encrypt:
    ```
      curl -d "pass1" http://localhost:8001/encrypt 
    ```
You’ll have to provide the same “user” and generated value as in the last step.  Copy the encrypted returned value.

9.  Open bootstrap.yml.  Add a key for security.user.password.  For the value, paste the encrypted password value from the last step, but prefix it with “{cipher}” (no quotes).  Then place the entire value within single quotes.  Save your work:
    ```
      ---
      encrypt:
        key: key

      security:
        user:
          #Executing 'curl -d "pass1" http://localhost:8001/encrypt' we get encrypted password below
          password: '{cipher}283782ad77ad0efd28fd8a236a30c48c3dfe9b87b46649343f3d7dd870b6f35f'
    ```

10.  Restart the config server.  Access [http://localhost:8001/anyapp-anyprofile.yml](http://localhost:8001/anyapp-anyprofile.yml).  You should be prompted for a user and password.  Enter “user” and “password” (or the value you encrypted for password).  You should once again see the YAML output.  If so, you have successfully configured the server with an encrypted password.

  **Part 4 - Adjust Client**

11.  The client won’t work until we adjust it to use the userid and password required by the config server.  Open the microspring11-cloud-client-lab10.  

12.  Open bootstrap.yml.  Alter the spring.cloud.config.uri to include the userid and password.  The syntax is http://(USER):(PASSWORD)@localhost:8001
    ```
      spring.application.name=microspring11-cloud-client-lab10
      #'user' is our user's name and 'pass1' is our user's password
      spring.cloud.config.uri=http://user:pass1@localhost:8001
      #spring.cloud.config.uri=http://localhost:8001
      server.port=8002
    ```

13.  Start the client.  Access the client at [http://localhost:8002/lucky-word](http://localhost:8002/lucky-word) to see the lucky word.  The client is now using HTTP basic authentication when accessing the config server.


  **BONUS - More Encryption**

14.  Encrypt a lucky word:  Using a REST client or curl command, POST a lucky word such as “Irish” to http://localhost:8001/encrypt:
     ```
      curl -d "Irish_2" http://localhost:8001/encrypt
     ```
Copy the encrypted returned value.

15.  Stop the config server.  Open application.yml.  Change the spring.cloud.config.server.git.uri to your own personal git repository.  If you are not sure what this is, take a look back at lab 3 or 8 where we used spring cloud config earlier:
     ```
        ---
        spring:
          cloud:
            config:
              server:
                git:
                  #Testing lab-10 (you must commnet URI local git repository line below)
                  #uri: https://github.com/Yorso/microspring-config-data.git #Remote GitHub repository where microspring11-cloud-client-lab10.yml file exists

                  #Testing "BONUS - More Encryption" section (you must commnet URI remote GitHub repository line above)
                  uri: file://home/jorge/git/microspring11-security-lab10/microspring11-local-git-repository-lab10 #Local git repository where microspring11-cloud-client-lab10.yml file exists

        server: 
          port: 8001
     ```

16.  Open you repository’s copy of lucky-word-client.yml.  Change the “lucky-word” to the encrypted value from the step above.  Be sure to prefix it with “{cipher}” and enclose the entire value in single quotes:
     ```
      # File used for testing "BONUS - More Encryption" section. This file must be in local git repository
      # curl -d "Irish_2" http://localhost:8001/encrypt
      ---
      lucky-word: '{cipher}ad612b31cf5da4dff37652b338118748aa64b4e6e1ead311a96188b4752799fc' # This is encrypted 'Irish_2' word
     ```
     
17.  Start the config server.  Restart the client.  Access the client at [http://localhost:8002/lucky-word](http://localhost:8002/lucky-word) to see the lucky word.  At this point, the word is stored in encrypted form, and is unencrypted by the config server before being sent to the client.

**Reflection**

1.  At present, all HTTP traffic is transmitted in the clear because we are not using HTTPS.  We could easily switch all of our apps to HTTPS, but doing so would require a certificate signed by a certificate authority.

2.  The encryption provided by the config server is a great way to avoid storing credentials in the repository in decrypted form.  But it is not foolproof; if one has access to the config server, or the config server properties, the encrypt.key can be obtained and used to decrypt the stored values.

3.  HTTP Basic authentication on the config server is nice, but it requires all clients to have a userid password, and this is stored in the clear.  

##Info

- [x] **[Microservices with Spring Cloud (Udemy)](https://www.udemy.com/microservices-with-spring-cloud/learn/v4/overview)**

- [x] **Instructor: [Ken Krueger, Technical Instructor in Software Development topics](https://linkedin.com/in/ken-krueger-43670111)**
