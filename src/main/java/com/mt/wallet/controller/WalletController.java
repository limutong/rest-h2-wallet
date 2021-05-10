package com.mt.wallet.controller;

import com.mt.wallet.entity.PaymentDetails;
import com.mt.wallet.entity.Wallet;
import com.mt.wallet.service.WalletService;
import com.mt.wallet.utils.WalletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Wallet Rest Controller.
 */
@RestController
@RequestMapping("/wallet")
public class WalletController {

    public static final String RESULT_SUCCESS = "Success";
    public static final String RESULT_FAILED = "Failed";
    /**
     * Autowiring the Wallet Service.
     */
    @Autowired
    private WalletService walletService;

    /**
     * Api to setup new Wallet.
     *
     * @param wallet Wallet to add
     * @return Saved Wallet
     */
    @PostMapping()
    public ResponseEntity<String> initializeWallet(
            @RequestBody final Wallet wallet) {
        //validator
        if (null == wallet || null == wallet.getName() || !WalletUtil.isValidCoinStr(wallet.getCoins())) {
            return ResponseEntity.badRequest().body("Invalid input parameters");
        }

        String responseMsg = RESULT_SUCCESS;
        try {
            walletService.initializeWallet(wallet);
        } catch (Exception e) {
            responseMsg = RESULT_FAILED;
            e.printStackTrace();
        }
        return new ResponseEntity<>(responseMsg, HttpStatus.CREATED);
    }

    /**
     * get Api to return list of all wallets.
     *
     * @return List of Wallets
     */
    @GetMapping()
    public ResponseEntity<List> getAllWallets() {
        List<Object> result = new ArrayList();
        String responseMsg = RESULT_SUCCESS;
        try {
            result.addAll(walletService.getAllWallets());
        } catch (Exception e) {
            responseMsg = RESULT_FAILED;
            e.printStackTrace();
        }
        result.add(responseMsg);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * get Api to return the Wallet by name.
     *
     * @param name name
     * @return Wallets
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<List<Wallet>> getWalletByName(
            @PathVariable("name") final String name) {
        List<Wallet> walletList = walletService.getWalletByName(name);
        return new ResponseEntity<>(walletList, HttpStatus.OK);
    }

    /**
     * get Api to return the Wallet by Id.
     *
     * @param id Id
     * @return Wallets
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<Wallet> getWalletById(
            @PathVariable("id") final Long id) {
        Wallet wallet = walletService.getWalletById(id);
        return new ResponseEntity<>(wallet, HttpStatus.OK);
    }

    /**
     * Api to pay with coin.
     * todo: return success pay message description
     *
     * @param paymentDetails
     * @return Wallet
     */
    @PostMapping("/pay")
    public ResponseEntity<List> pay(
            @RequestBody final PaymentDetails paymentDetails) {
        System.out.println("===== recevied pay request =====");
        List<String> result = new ArrayList();
        String responseMsg = "Successfully paid "+ paymentDetails.getAmount();
        try {
            Wallet wallet = walletService.pay(paymentDetails);
            result.add(responseMsg);
            result.add("My current coins are [" + wallet.getCoins() + "]");
        } catch (Exception e) {
            result.add(e.getMessage());
            e.printStackTrace();
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Api to update an existing Wallet.
     *
     * @param id             Id to update
     * @param walletToUpdate Wallet to update
     * @return Updated Wallet
     */
    @PutMapping("/{id}")
    public ResponseEntity<Wallet> updateWalletById(
            @PathVariable("id") final Long id,
            @RequestBody final Wallet walletToUpdate) {
        Wallet updatedWallet
                = walletService.updateWalletById(id, walletToUpdate);
        return new ResponseEntity<>(updatedWallet, HttpStatus.OK);
    }

    /**
     * Api to delete a wallet.
     *
     * @param id Id to delete
     * @return Success Message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWalletById(
            @PathVariable("id") final Long id) {
        walletService.deleteWalletById(id);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    /**
     * get Api to return search wallet by name like.
     *
     * @param searchString Search String
     * @return Search Result
     */
    @GetMapping("/search/{searchString}")
    public ResponseEntity<List<Wallet>> getWalletByNameLike(
            @PathVariable("searchString") final String searchString) {
        List<Wallet> walletList
                = walletService.getWalletByNameLike(searchString);
        return new ResponseEntity<>(walletList, HttpStatus.OK);
    }
}
