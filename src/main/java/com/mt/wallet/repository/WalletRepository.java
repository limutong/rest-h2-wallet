package com.mt.wallet.repository;

import com.mt.wallet.entity.Wallet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WalletRepository extends CrudRepository<Wallet, Long> {

  /**
   * Method to return List of Wallets with same substring in name.
   * Does the same job as below method, but different way of doing in Spring
   * @param value Value to check
   * @return List of Planel
   */
  List<Wallet> findByNameContaining(String value);

  /**
   * Method to return List of Wallets with same substring in name.
   * Does the same job as above method, but different way of doing in Spring
   * @param value Value to check
   * @return List of Planel
   */
  @Query("SELECT p FROM Wallet p WHERE p.name LIKE %:value%")
  List<Wallet> findByNameLike(@Param("value") String value);

  @Query("SELECT p FROM Wallet p WHERE p.name = :value")
  List<Wallet> findByName(@Param("value") String value);
}
