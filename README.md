# spring-boot-rest-h2-wallet

**Spring Boot Rest H2 Sample**

**Functions completed**:

    1.Functionalities includes: initialWallet, queryWallet, pay

    2.Restful API and Insomnia test json scripts

    3.H2

    4.Basic test cases

    5.slf4j logger

    6.Define api version as "v1"


**Future enhancements:**

    1.Create more test cases, both unit and api test, lift up code coverage to 90%

    2.Refactor api path and resources with more readable name

    3.Wrap all "write" function flows into transactions, add cache layer for "read" functions

    4.Enhance controller layer:

      4.1.Error handler with error code & reason mapping

      4.2.Create context for isolate concurrent request

      4.3.Add more readable description for queryWallet api, improve response body, with common result 

    5.Add proxy class by Composition into walletService layer, with this stratergy pattern, we can apply different pay stratergies, for instance, fast scan for the exact matching coin before default stratergy; support customized payment with user priority input; support feature payment like discount/coupon

    6.Split digest and service log hierarchy




**For instructions:**

1.Build the project :

  ![build-01](https://user-images.githubusercontent.com/6138747/117593979-b882d400-b16f-11eb-9dd4-4c3d56587787.PNG)
  
2.Launch the spring boot project

  1.1.Launch CoinWalletApplication.main from IntelliJ

  ![sprintboot-01](https://user-images.githubusercontent.com/6138747/117593977-b751a700-b16f-11eb-99c2-89c64678a726.PNG)
  
  1.2.Download Insomnia, import Insomnia scripts rest-h2-wallet/restful/Restful_Demo_Insomnia_2021-05-11.json
  
  ![import-01](https://user-images.githubusercontent.com/6138747/117594164-11eb0300-b170-11eb-92c1-6c9ba5e2be21.PNG)

  1.3.Invoke Restful API from Insomnia
  
  ![rest-02](https://user-images.githubusercontent.com/6138747/117593881-7c4f7380-b16f-11eb-91bf-1fab829bc5bb.PNG)
  
  ![rest-03](https://user-images.githubusercontent.com/6138747/117593884-7ce80a00-b16f-11eb-8311-92520e5e4d31.PNG)
  
  ![rest-04](https://user-images.githubusercontent.com/6138747/117593885-7d80a080-b16f-11eb-937a-5964b503e43d.PNG)
  
  ![rest-05](https://user-images.githubusercontent.com/6138747/117593888-7d80a080-b16f-11eb-9caa-a5ae108fb2bc.PNG)
  
  ![rest-06](https://user-images.githubusercontent.com/6138747/117593889-7e193700-b16f-11eb-817d-c0a0b732542c.PNG)
  
  ![rest-07](https://user-images.githubusercontent.com/6138747/117593891-7eb1cd80-b16f-11eb-8461-df1f27be15df.PNG)
  
  ![rest-08](https://user-images.githubusercontent.com/6138747/117593892-7eb1cd80-b16f-11eb-9d6c-9114f72bd9b5.PNG)
  
  ![rest-09](https://user-images.githubusercontent.com/6138747/117593893-7f4a6400-b16f-11eb-9eb2-d8343eccf2ac.PNG)
  
  1.4 Access H2 DB and check backend result
  
  ![h2-02](https://user-images.githubusercontent.com/6138747/117593910-8e311680-b16f-11eb-9d96-87256297f7ed.PNG)
  
  ![h2-03](https://user-images.githubusercontent.com/6138747/117593912-8ec9ad00-b16f-11eb-8d39-03af82cd058b.PNG)
  
  ![h2-04](https://user-images.githubusercontent.com/6138747/117593913-8f624380-b16f-11eb-8481-74437fe0199c.PNG)
  
3.Access H2 DB : 

  2.1.application.properties -> spring.h2.console.enabled=true

  2.2.Sprintbooth2Application.java and run the main()

  2.3.http://localhost:8080/h2-console

  2.4.jdbc:h2:file:./data/wallet -> connect



