# spring-boot-rest-h2-wallet
Spring Boot Rest H2 Sample

For instructions:

1.Launch the spring boot project

  1.1.Launch CoinWalletApplication.main from IntelliJ
  
  1.2.Download Insomnia, import Insomnia scripts rest-h2-wallet/restful/Restful_Demo_Insomnia_2021-05-09.json
  
  1.3.Invoke Restful API from Insomnia
  ![rest-01](https://user-images.githubusercontent.com/6138747/117593344-fb43ac80-b16d-11eb-8467-4d5dd0185d54.PNG)
  1.4 Access H2 DB and check backend result

2.Access H2 DB : 

  2.1.application.properties -> spring.h2.console.enabled=true

  2.2.Sprintbooth2Application.java and run the main()

  2.3.http://localhost:8080/h2-console

  2.4.jdbc:h2:file:./data/planetdb -> connect

