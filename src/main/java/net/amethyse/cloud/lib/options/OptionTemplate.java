package net.amethyse.cloud.lib.options;

/*******************************************************
 * Copyright (C) 2015-2019 Piinguiin neuraxhd@gmail.com
 *
 * This file is part of Cloud and was created at the 26.10.2020
 *
 * Cloud can not be copied and/or distributed without the express
 * permission of the owner.
 *
 *******************************************************/
public class OptionTemplate {
  private String[] names;

  public OptionTemplate(String[] names) {
    this.names = names;
  }

  public String[] getNames() {
    return names;
  }

  public void setNames(String[] names) {
    this.names = names;
  }
}
