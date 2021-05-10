package com.mt.wallet.service;

import com.mt.wallet.entity.PaymentDetails;
import com.mt.wallet.entity.Wallet;

import java.util.List;

/**
 * Wallet Service.
 */
public interface WalletService {

    /**
     * Get All Wallets.
     *
     * @return List of all wallet.
     */
    List<Wallet> getAllWallets();

    /**
     * Get Wallet by Name.
     *
     * @return List of all wallet.
     */
    List<Wallet> getWalletByName(String name);

    /**
     * Get Wallet By Id.
     *
     * @param id Id
     * @return Wallet
     */
    Wallet getWalletById(Long id);

    /**
     * Initialize Wallet.
     *
     * @param wallet Wallet to save
     * @return Saved Wallet
     */
    Wallet initializeWallet(Wallet wallet);

    /**
     * Update Wallet.
     *
     * @param id             Id
     * @param walletToUpdate Wallet to Update
     * @return Updated Wallet
     */
    Wallet updateWalletById(Long id, Wallet walletToUpdate);

    /**
     * Pay
     */
    Wallet pay(PaymentDetails paymentDetails) throws Exception;

    /**
     * Delete Wallet by Id.
     *
     * @param id Id
     */
    void deleteWalletById(Long id);

    /**
     * Search Wallet by name like.
     *
     * @param searchString SearchString
     * @return Search result
     */
    List<Wallet> getWalletByNameLike(String searchString);


}
