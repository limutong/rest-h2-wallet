package com.mt.wallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot Main Class to bootstrap the Application.
 */
@SpringBootApplication
public class CoinWalletApplication {

  /**
  * Main method.
  * @param args args
  */
  public static void main(final String[] args) {
    SpringApplication.run(CoinWalletApplication.class, args);
  }
}
