package net.amethyse.cloud.lib.options;

import java.util.HashMap;
import java.util.Map;

/*******************************************************
 * Copyright (C) 2015-2019 Piinguiin neuraxhd@gmail.com
 *
 * This file is part of Cloud and was created at the 26.10.2020
 *
 * Cloud can not be copied and/or distributed without the express
 * permission of the owner.
 *
 *******************************************************/
public class OptionSet {

  private Map<String, Option> options;

  public OptionSet() {
    options = new HashMap<>();
  }

  public OptionSet(Map<String, Option> options) {
    this.options = options;
  }

  public void add(Option option) {
    for (String name : option.getNames()) {
      options.put(name, option);
    }
  }

  public boolean has(String option) {
    return option.contains(option) && get(option).isSet();
  }

  public Option get(String option) {
    return options.get(option);
  }
}
