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
public class Option {
  private String[] names;
  private boolean set;
  private String value;

  public Option(String[] names) {
    this.names = names;
  }

  public Option(OptionTemplate template) {
    this(template.getNames());
  }

  public String[] getNames() {
    return names;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    if (value != null) this.set = true;
    this.value = value;
  }

  public boolean isSet() {
    return set;
  }

  public void setSet(boolean set) {
    this.set = set;
  }
}
