package net.amethyse.cloud.master.instance.impl;

import de.piinguiin.netframe.commons.connection.NetFrameConnection;
import java.net.InetAddress;
import java.util.Set;
import lombok.Getter;
import net.amethyse.cloud.master.CloudMaster;
import net.amethyse.cloud.master.instance.Proxy;
import net.amethyse.cloud.master.instance.Server;
import net.amethyse.cloud.master.instance.Wrapper;
import net.amethyse.cloud.master.network.packets.wrapper.out.WrapperStopPacket;

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
public class WrapperImpl implements Wrapper {

  @Getter
  private String name;
  private InetAddress address;
  @Getter
  private NetFrameConnection connection;

  public WrapperImpl() {
    this.name = CloudMaster.getInstance().getInstanceManager().generateNewWrapperName();
  }

  @Override
  public void onConnect(NetFrameConnection connection) {
    this.connection = connection;
  }

  @Override
  public void onDisconnect() {
    this.connection.sendPackets(new WrapperStopPacket("Master stopped."));
  }

  @Override
  public void onHandshakeSuccess() {
  }

  public InetAddress getAddress() {

    if (isConnected()) {
      CloudMaster.getInstance()
          .getLogger()
          .error("Cannot return address from Wrapper cause its not connected.");
      return null;
    }
    return address;
  }

  @Override
  public int getPort() {
    return 0;
  }

  @Override
  public Set<Server> getServers() {
    return null;
  }

  @Override
  public Set<Proxy> getProxies() {
    return null;
  }
}
