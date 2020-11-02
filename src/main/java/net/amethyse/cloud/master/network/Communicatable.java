package net.amethyse.cloud.master.network;

import de.piinguiin.netframe.commons.connection.NetFrameConnection;
import java.net.InetAddress;

/**
 * ***************************************************** Copyright (C) 2015-2019 Piinguiin
 * neuraxhd@gmail.com
 *
 * <p>This file is part of Cloud and was created at the 29.10.2020
 *
 * <p>Cloud can not be copied and/or distributed without the express permission of the owner.
 *
 * <p>*****************************************************
 */
public interface Communicatable {

  InetAddress getAddress();

  int getPort();

  void onConnect(NetFrameConnection connection);

  void onDisconnect();

  NetFrameConnection getConnection();

  void onHandshakeSuccess();

  default boolean isConnected(){
    return getConnection() != null;
  }
}
