package net.amethyse.cloud.master.instance.impl;

import de.piinguiin.netframe.commons.connection.NetFrameConnection;
import java.net.InetAddress;
import lombok.Getter;
import lombok.Setter;
import net.amethyse.cloud.lib.misc.ProxyChooseStrategy;
import net.amethyse.cloud.master.CloudMaster;
import net.amethyse.cloud.master.instance.Proxy;
import net.amethyse.cloud.master.instance.Wrapper;
import net.amethyse.cloud.master.network.packets.proxy.out.ProxyStartPacket;
import net.amethyse.cloud.master.network.packets.server.out.ServerStartPacket;
import net.amethyse.cloud.master.templates.ProxyGroupTemplate;

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
public class ProxyImpl implements Proxy {

  @Getter private String name;
  @Getter private String group;
  @Getter private int ram;
  @Getter private NetFrameConnection connection;
  private InetAddress address;
  private int port;
  @Getter private int maxPlayers;
  @Getter String messageOfTheDay;
  @Getter private ProxyChooseStrategy proxyChooseStrategy;
  @Getter @Setter private Wrapper wrapper;
  private final boolean staticProxy;
  private final String wrapperName;

  public ProxyImpl(ProxyGroupTemplate proxyGroupTemplate) {
    this.group = proxyGroupTemplate.getName();
    this.name = CloudMaster.getInstance().getInstanceManager().generateNewProxyName(this.group);
    this.ram = proxyGroupTemplate.getRam();
    this.maxPlayers = proxyGroupTemplate.getMaxPlayers();
    this.messageOfTheDay = proxyGroupTemplate.getMessageOfTheDay();
    this.proxyChooseStrategy = proxyGroupTemplate.getProxyChooseStrategy();
    this.staticProxy = proxyGroupTemplate.isStaticProxy();
    this.wrapperName = proxyGroupTemplate.getWrapperName();
  }

  // sends start packets
  @Override
  public void start(Wrapper wrapper) {
    try {
      if (staticProxy) {
        this.wrapper = CloudMaster.getInstance().getInstanceManager().getWrapper(wrapperName);
      } else {
        this.wrapper = CloudMaster.getInstance().getInstanceManager().getWrapper(wrapperName);
      }
    }catch (NullPointerException e){
      CloudMaster.getInstance().getLogger().info(e.getMessage());
    }

    this.wrapper.getConnection().sendPackets(new ProxyStartPacket(group));
  }

  // executed when server stops
  @Override
  public void stop() {
    CloudMaster.getInstance().getInstanceManager().unregisterProxy(this);
  }

  // executed when master get the handshake packet
  @Override
  public void onConnect(NetFrameConnection connection) {
    this.connection = connection;
    // TODO set address and port
  }

  // executed when master get the disconnect packet
  @Override
  public void onDisconnect() {
    stop();
  }

  // executed after handshake complete, after connect
  @Override
  public void onHandshakeSuccess() {}

  public InetAddress getAddress() {

    if (isConnected()) {
      CloudMaster.getInstance()
          .getLogger()
          .error("Cannot return address from Proxy cause its not connected.");
      return null;
    }
    return address;
  }

  public int getPort() {

    if (isConnected()) {
      CloudMaster.getInstance()
          .getLogger()
          .error("Cannot return port from Proxy cause its not connected.");
      return -1;
    }
    return port;
  }
}
