package net.amethyse.cloud.lib.utils;

/*******************************************************
 * Copyright (C) 2015-2019 Piinguiin neuraxhd@gmail.com
 *
 * This file is part of Cloud and was created at the 29.10.2020
 *
 * Cloud can not be copied and/or distributed without the express
 * permission of the owner.
 *
 *******************************************************/
public class DoAfterAmount {

  private int current;
  private int amount;
  private Runnable runnable;

  public DoAfterAmount(int amount, Runnable runnable) {
    current = 0;
    this.amount = amount;
    this.runnable = runnable;
  }

  public void addOne() {
    current++;
    if (current == amount) runnable.run();
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }
}
