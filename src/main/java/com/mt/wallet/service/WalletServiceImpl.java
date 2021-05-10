package com.mt.wallet.service;

import com.google.common.collect.Lists;
import com.mt.wallet.controller.WalletController;
import com.mt.wallet.entity.PaymentDetails;
import com.mt.wallet.entity.Wallet;
import com.mt.wallet.repository.WalletRepository;
import com.mt.wallet.utils.WalletUtil;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WalletServiceImpl implements WalletService {

    Logger logger = LoggerFactory.getLogger(WalletServiceImpl.class);
    /**
     * Autowiring the Wallet Repository.
     */
    @Autowired
    private WalletRepository walletRepository;

    /**
     * Get All Wallet.
     *
     * @return List of all wallet.
     */
    @Override
    public List<Wallet> getAllWallets() {
        List<Wallet> walletList = (List<Wallet>) walletRepository.findAll();
        walletList.stream().forEach(w -> {
            w.setCoins(sortCoinString(w.getCoins()));
        });
        return walletList;
    }

    /**
     * Get Wallet By Name.
     *
     * @param name Name
     * @return Wallet
     */
    @Override
    public List<Wallet> getWalletByName(String name) {
        List<Wallet> walletList = walletRepository.findByName(name);
        walletList.stream().forEach(w -> {
            w.setCoins(sortCoinString(w.getCoins()));
        });
        return walletList;
    }

    protected String sortCoinString(String coins) {
        if(!WalletUtil.isValidCoinStr(coins)){
            return coins;
        }
        Optional<String[]> listOptionalString = Optional.of(coins.split(","));
        String out = listOptionalString.map(strings -> {
            List<Integer> coinsIntList = Arrays.stream(strings).map(str -> Integer.valueOf(str.trim())).collect(Collectors.toList());
            Collections.sort(coinsIntList);
            return coinsIntList.toString();
        }).orElse(coins);
        return out;
    }

    /**
     * Get Wallet By Id.
     *
     * @param id Id
     * @return Wallet
     */
    @Override
    public Wallet getWalletById(Long id) {
        return walletRepository.findById(id).get();
    }

    /**
     * Save Wallet.
     *
     * @param wallet Wallet to save
     * @return Saved Wallet
     */
    @Override
    public Wallet initializeWallet(Wallet wallet) {
        return walletRepository.save(wallet);
    }

    /**
     * Update Wallet.
     *
     * @param id             Id
     * @param walletToUpdate Wallet to Update
     * @return Updated Wallet
     */
    @Override
    public Wallet updateWalletById(Long id, Wallet walletToUpdate) {
        // Fetch the Wallet from db
        Wallet walletFromDb = walletRepository.findById(id).get();
        walletFromDb.setName(walletToUpdate.getName());
        walletFromDb.setCoins(walletToUpdate.getCoins());
        return walletRepository.save(walletFromDb);
    }

    @Override
    public Wallet pay(PaymentDetails paymentDetails) throws Exception {
        logger.info("-----");
        logger.info("paymentDetails : " + paymentDetails.toString());
        // Fetch the Wallets from db
        List<Wallet> walletsFromDb = walletRepository.findByName(paymentDetails.getName());
        Integer payAmount = paymentDetails.getAmount();
        if (CollectionUtils.isEmpty(walletsFromDb) || walletsFromDb.size() > 1) {
            String errorMsg = "Invalid user name : [" + paymentDetails.getName() + "], walletsFromDb : [" + walletsFromDb.toString() + "]";
            logger.info("----" + errorMsg);
            throw new InvalidParameterException(errorMsg);
        }
        Wallet walletFromDb = walletsFromDb.get(0);
        String newCoins = payWithDefaultStratergy(walletFromDb.getCoins(), payAmount);
        walletFromDb.setCoins(newCoins);

        logger.info(" ---- store wallet complete ----");
        return walletRepository.save(walletFromDb);
    }

    protected String payWithDefaultStratergy(String coins, Integer payAmount) throws InsufficientBalanceException {
        if (!WalletUtil.isValidCoinStr(coins)) {
            if (Strings.isBlank(coins)) {
                throw new InsufficientBalanceException("You do not have sufficient coins to pay " + payAmount + "." + "\\r\\n" +
                        " My current coins are [" + coins + "]");
            } else {
                throw new IllegalStateException("Invalid coin wallet status." + "\\r\\n" +
                        " My current coins are [" + coins + "]");
            }
        }

        List<Integer> coinList = Arrays.stream(coins.split(",")).map(str -> Integer.valueOf(str.trim())).collect(Collectors.toList());
        Collections.sort(coinList);
        logger.info("---- default Stratergy ----");
        logger.info("coinList :" + coinList.toString());
        List<Integer> withdrawCoins = Lists.newArrayList();
        Integer withdrawAmount = 0;
        for (Integer coin : coinList) {
            if (withdrawAmount >= payAmount) {
                break;
            }
            withdrawCoins.add(coin);
            withdrawAmount += coin;
        }
        if (withdrawAmount == payAmount) {
            logger.info("---- payAmount [" + payAmount + "], No Change required, paid [" + withdrawCoins.toString() + "] ----");
        }
        if (withdrawAmount > payAmount) {
            Integer change = withdrawAmount - payAmount;
            logger.info(" ---- payAmount [" + payAmount + "], Change required [" + change + "], paid [" + withdrawCoins.toString() + "] ----");
            coinList.add(change);
            logger.info(" ---- coinList with change : [" + coinList.toString() + "]");
        }
        if (withdrawAmount < payAmount) {
            logger.info(" ---- InsufficientBalanceException ---- ");
            String errorMsg = "You do not have sufficient coins to pay " + payAmount + "." + "\\r\\n" +
                    " My current coins are [" + coins + "]";
            throw new InsufficientBalanceException(errorMsg);
        }

        logger.info(" ---- withdrawCoins  : " + withdrawCoins.toString() + " ----");
        logger.info(" ---- coinList before remove garthered coin : " + coinList.toString() + " ----");
        withdrawCoins.forEach(gc -> {
            coinList.remove(gc);
        });
        Collections.sort(coinList);
        logger.info(" ---- coinList after remove garthered coin : " + coinList.toString() + " ----");
        return coinList.toString().replace("[", "").replace("]", "");
    }

    /**
     * Delete Wallet by Id.
     *
     * @param id Id
     */
    @Override
    public void deleteWalletById(Long id) {
        walletRepository.deleteById(id);
    }

    /**
     * Search Wallet by name like.
     *
     * @param searchString SearchString
     * @return Search result
     */
    @Override
    public List<Wallet> getWalletByNameLike(String searchString) {
        List<Wallet> walletList = walletRepository.findByNameLike(searchString);
        walletList.stream().forEach(w -> {
            w.setCoins(sortCoinString(w.getCoins()));
        });
        return walletList;
    }
}
