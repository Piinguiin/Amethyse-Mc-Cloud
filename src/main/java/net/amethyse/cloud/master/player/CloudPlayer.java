package net.amethyse.cloud.master.player;

import java.util.UUID;
import net.amethyse.cloud.master.instance.Proxy;
import net.amethyse.cloud.master.instance.Server;

/*******************************************************
 * Copyright (C) 2015-2019 Piinguiin neuraxhd@gmail.com
 *
 * This file is part of Cloud and was created at the 29.10.2020
 *
 * Cloud can not be copied and/or distributed without the express
 * permission of the owner.
 *
 *******************************************************/
public interface CloudPlayer {

  UUID getUUID();

  Server getCurrentServer();

  Proxy getCurrentProxy();

  void sendToServer(String server);



}
