package com.mt.wallet.service;

import com.mt.wallet.entity.Wallet;
import com.mt.wallet.repository.WalletRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Wallet Service Test.
 */
@SpringBootTest
class WalletServiceTest {

  /**
   * Autowire WalletService.
   */
  @Autowired
  private WalletService walletService;

  /**
   * Create a mock implementation of the WalletRepository.
   */
  @MockBean
  private WalletRepository walletRepository;

  /**
   * Get all Wallets test.
   */
  @Test
  @DisplayName("Get all Wallets test")
  void getAllWalletsTest() {
    Wallet wallet1 = new Wallet();
    wallet1.setName("limutong");
    wallet1.setCoins("2, 3, 1, 2, 1");
    doReturn(List.of(wallet1)).when(walletRepository).findAll();

    // Execute the service call
    List<Wallet> returnedWallets = walletService.getAllWallets();

    // Assert the response
    Assertions.assertNotNull(returnedWallets);
    Assertions.assertEquals(List.of(wallet1), returnedWallets);
  }

  /**
   * Get Wallet by id test.
   */
  @Test
  @DisplayName("Get Wallet by id test")
  void getWalletByIdTest() {
    Wallet wallet1 = new Wallet();
    wallet1.setName("lijiayao");
    wallet1.setCoins("2, 3, 1, 2, 1");
    doReturn(Optional.of(wallet1)).when(walletRepository).findById(1L);

    // Execute the service call
    Wallet returnedWallet = walletService.getWalletById(1L);

    // Assert the response
    Assertions.assertNotNull(returnedWallet);
    Assertions.assertSame(wallet1, returnedWallet,
            "The wallet returned was not the same as the mock");
  }

  /**
   * Save Wallet test.
   */
  @Test
  @DisplayName("Save Wallet test")
  void saveWalletTest() {
    Wallet wallet1 = new Wallet();
    wallet1.setName("limutong");
    wallet1.setCoins("2, 3, 1, 2, 1");
    when(walletRepository.save(any())).thenAnswer(
            invocation -> {
              Object[] args = invocation.getArguments();
              Wallet walletToSave = (Wallet) args[0];
              walletToSave.setId(1L);
              return walletToSave;
            });

    // Execute the service call
    Wallet returnedWallet = walletService.initializeWallet(wallet1);

    // Assert the response
    Assertions.assertNotNull(returnedWallet);
    Assertions.assertSame(wallet1, returnedWallet);
  }

  /**
   * Update Wallet test.
   */
  @Test
  @DisplayName("Update Wallet test")
  void updateWalletByIdTest() {
    Wallet wallet1 = new Wallet();
    wallet1.setName("limutong");
    wallet1.setCoins("2, 3, 1, 2, 1");
    doReturn(Optional.of(wallet1)).when(walletRepository).findById(1L);

    when(walletRepository.save(any())).thenAnswer(
            invocation -> {
              Object[] args = invocation.getArguments();
              Wallet walletToSave = (Wallet) args[0];
              return walletToSave;
            });

    // Execute the service call
    Wallet returnedWallet = walletService.updateWalletById(1L, wallet1);

    // Assert the response
    Assertions.assertNotNull(returnedWallet);
    Assertions.assertSame(wallet1, returnedWallet);
  }

  /**
   * Delete Wallet by id test.
   */
  @Test
  @DisplayName("Delete Wallet by id test")
  void deleteWalletByIdTest() {

    // Execute the service call
    walletService.deleteWalletById(1L);

    // Assert the response
    verify(walletRepository, times(1)).deleteById(1L);
  }

  /**
   * Get Wallets by name like test.
   */
  @Test
  @DisplayName("Get Wallets by name like test")
  void getWalletByNameLikeTest() {
    Wallet wallet1 = new Wallet();
    wallet1.setName("limutong");
    wallet1.setCoins("2, 3, 1, 2, 1");
    doReturn(List.of(wallet1)).when(walletRepository).findByNameLike("lim");

    // Execute the service call
    List<Wallet> returnedWallets = walletService.getWalletByNameLike("lim");

    // Assert the response
    Assertions.assertNotNull(returnedWallets);
    Assertions.assertEquals(List.of(wallet1), returnedWallets);
  }

}
