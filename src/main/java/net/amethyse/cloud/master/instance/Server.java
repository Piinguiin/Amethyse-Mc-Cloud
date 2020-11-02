package net.amethyse.cloud.master.instance;

import net.amethyse.cloud.master.network.Communicatable;

/*******************************************************
 * Copyright (C) 2015-2019 Piinguiin neuraxhd@gmail.com
 *
 * This file is part of Cloud and was created at the 29.10.2020
 *
 * Cloud can not be copied and/or distributed without the express
 * permission of the owner.
 *
 *******************************************************/
public interface Server extends Communicatable {

  String getName();

  String getGroup();

  void start(Wrapper wrapper);

  void stop();

}
