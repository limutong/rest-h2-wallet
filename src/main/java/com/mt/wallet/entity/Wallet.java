package com.mt.wallet.entity;

import javax.persistence.*;
import java.sql.Array;
import java.util.List;

/**
 * Entity Class.
 */
@Entity
public class Wallet {

  /**
   * Id.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * Name.
   */
  @Column(unique = true)
  private String name;

  /**
   *
   */
  private String coins;

  /**
   * Get id.
   * @return Get Id.
   */
  public Long getId() {
    return id;
  }

  /**
   * Set id.
   * @param idA Id to Set
   */
  public void setId(final Long idA) {
    this.id = idA;
  }

  /**
   * Get name.
   * @return Name
   */
  public String getName() {
    return name;
  }

  /**
   * Set name.
   * @param nameA Name to Set
   */
  public void setName(final String nameA) {
    this.name = nameA;
  }

  /**
   * Get Number of moons.
   * @return Number of Moons.
   */
  public String getCoins() {
    return coins;
  }

  /**
   * Set number  of moons.
   * @param coins Number of moons to Set
   */
  public void setCoins(final String coins) {
    this.coins = coins;
  }
}
