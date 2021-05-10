package com.mt.wallet.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * PaymentDetails Class.
 */
@Entity
public class PaymentDetails {

  /**
   * Id.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * Name.
   */
  private String name;

  /**
   * Amount of payment
   */
  private Integer amount;

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
  public Integer getAmount() {
    return amount;
  }

  /**
   * Set number  of moons.
   * @param amount
   */
  public void setAmount(final Integer amount) {
    this.amount = amount;
  }

  @Override
  public String toString() {
    return "PaymentDetails{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", amount=" + amount +
            '}';
  }
}
