package com.mt.wallet.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import com.mt.wallet.entity.Wallet;
import com.mt.wallet.service.WalletService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Wallet Controller Tests.
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(value = WalletController.class)
class WalletControllerTest {

  /**
   * Mock Mvc.
   */
  @Autowired
  private MockMvc mockMvc;

  /**
   * Rest Template for testing the api.
   */
  @MockBean
  private WalletService walletService;

  /**
   * List all Wallets test.
   * @throws Exception exception
   */
  @Test
  @DisplayName("List all Wallets test")
  void listAllWalletsTest() throws Exception {
    Wallet wallet1 = new Wallet();
    wallet1.setName("limutong");
    wallet1.setCoins("2, 3, 1, 2, 1");

    when(walletService.getAllWallets()).thenReturn(List.of(wallet1));
    MockHttpServletResponse response = mockMvc.perform(get("/wallet")
            .accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();

    List<Object> result = Lists.newArrayList();
    result.addAll(List.of(wallet1));
    result.add("Success");
    /* Assert */
    assertThat(response.getStatus(), equalTo(HttpStatus.OK.value()));
    assertThat(response.getContentAsString(),
            equalTo(getJsonFromObject(result)));
    verify(walletService, times(1)).getAllWallets();
  }

  /**
   * Get wallet by id test.
   * @throws Exception exception
   */
  @Test
  @DisplayName("Get wallet by id test")
  void getWalletByIdTest() throws Exception {
    Wallet wallet1 = new Wallet();
    wallet1.setName("limutong");
    wallet1.setCoins("2, 3, 1, 2, 1");
    when(walletService.getWalletById(1L)).thenReturn(wallet1);
    MockHttpServletResponse response = mockMvc.perform(get("/wallet/id/1")
            .accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();

    /* Assert */
    assertThat(response.getStatus(), equalTo(HttpStatus.OK.value()));
    assertThat(response.getContentAsString(),
            equalTo(getJsonFromObject(wallet1)));
    verify(walletService, times(1)).getWalletById(1L);
  }

  /**
   * Save wallet test.
   * @throws Exception exception
   */
  @Test
  @DisplayName("Save wallet test")
  void saveWalletTest() throws Exception {
    Wallet wallet1 = new Wallet();
    wallet1.setName("limutong");
    wallet1.setCoins("2, 3, 1, 2, 1");
    when(walletService.initializeWallet(any())).thenAnswer(
            invocation -> {
              Object[] args = invocation.getArguments();
              Wallet walletToSave = (Wallet) args[0];
              walletToSave.setId(1L);
              return walletToSave;
            });
    MockHttpServletResponse response = mockMvc.perform(post("/wallet")
            .contentType(MediaType.APPLICATION_JSON)
            .content(getJsonFromObject(wallet1))
            .accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();

    /* Assert */
    assertThat(response.getStatus(), equalTo(HttpStatus.CREATED.value()));
    // update expected
    wallet1.setId(1L);
    assertThat(response.getContentAsString(),
            equalTo("Success"));
    verify(walletService, times(1)).initializeWallet(any());
  }

  /**
   * Update wallet test.
   * @throws Exception exception
   */
  @Test
  @DisplayName("Update wallet test")
  void updateWalletByIdTest() throws Exception {
    Wallet wallet1 = new Wallet();
    wallet1.setName("limutong");
    wallet1.setCoins("2, 3, 1, 2, 1");
    wallet1.setId(1L);
    when(walletService.updateWalletById(any(), any())).thenAnswer(
            invocation -> {
              Object[] args = invocation.getArguments();
              Wallet walletToSave = (Wallet) args[1];
              return walletToSave;
            });
    MockHttpServletResponse response = mockMvc.perform(put("/wallet/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(getJsonFromObject(wallet1))
            .accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();

    /* Assert */
    assertThat(response.getStatus(), equalTo(HttpStatus.OK.value()));
    assertThat(response.getContentAsString(),
            equalTo(getJsonFromObject(wallet1)));
    verify(walletService, times(1)).updateWalletById(any(), any());
  }

  /**
   * Delete wallet by id test.
   * @throws Exception exception
   */
  @Test
  @DisplayName("Delete wallet by id test")
  void deleteWalletByIdTest() throws Exception {
    MockHttpServletResponse response = mockMvc.perform(delete("/wallet/1")
            .accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();

    /* Assert */
    assertThat(response.getStatus(), equalTo(HttpStatus.OK.value()));
    assertThat(response.getContentAsString(), equalTo("Success"));
    verify(walletService, times(1)).deleteWalletById(1L);
  }

  /**
   * Get Wallet by name like test.
   * @throws Exception exception
   */
  @Test
  @DisplayName("Get Wallet by name like test")
  void getWalletByNameLikeTest() throws Exception {
    Wallet wallet1 = new Wallet();
    wallet1.setName("limutong");
    wallet1.setCoins("2, 3, 1, 2, 1");
    when(walletService.getWalletByNameLike("mut"))
            .thenReturn(List.of(wallet1));
    MockHttpServletResponse response
            = mockMvc.perform(get("/wallet/search/mut")
            .accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();

    /* Assert */
    assertThat(response.getStatus(), equalTo(HttpStatus.OK.value()));
    assertThat(response.getContentAsString(),
            equalTo(getJsonFromObject(List.of(wallet1))));
    verify(walletService, times(1)).getWalletByNameLike("mut");
  }

  /**
   * To Convert an Object to JSON String.
   * @param o Object
   * @return Object as String
   * @throws JsonProcessingException throws JsonProcessingException
   */
  private static String getJsonFromObject(
          final Object o) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    return mapper.writeValueAsString(o);
  }
}
