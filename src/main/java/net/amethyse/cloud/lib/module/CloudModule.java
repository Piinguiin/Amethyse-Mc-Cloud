package net.amethyse.cloud.lib.module;

import net.amethyse.cloud.lib.options.OptionSet;

/*******************************************************
 * Copyright (C) 2015-2019 Piinguiin neuraxhd@gmail.com
 *
 * This file is part of Cloud and was created at the 26.10.2020
 *
 * Cloud can not be copied and/or distributed without the express
 * permission of the owner.
 *
 *******************************************************/
public interface CloudModule {

  void load(OptionSet optionSet) throws Exception;
  void unload() throws Exception;
  ModuleType getModuleType();

}
