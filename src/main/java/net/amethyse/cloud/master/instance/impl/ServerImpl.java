package net.amethyse.cloud.master.instance.impl;

import de.piinguiin.netframe.commons.connection.NetFrameConnection;
import java.net.InetAddress;
import lombok.Getter;
import lombok.Setter;
import net.amethyse.cloud.master.CloudMaster;
import net.amethyse.cloud.master.instance.Server;
import net.amethyse.cloud.master.instance.Wrapper;
import net.amethyse.cloud.master.templates.ServerGroupTemplate;

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
public class ServerImpl implements Server {

  @Getter private String name;
  @Getter private String group;
  @Getter private int ram;
  private InetAddress address;
  private int port;
  @Getter private NetFrameConnection connection;
  @Getter @Setter private Wrapper wrapper;
  @Getter private int maxPlayers;
  @Getter private final boolean staticServer;
  private final String wrapperName;

  public ServerImpl(ServerGroupTemplate serverGroupTemplate) {
    this.group = serverGroupTemplate.getName();
    this.name = CloudMaster.getInstance().getInstanceManager().generateNewServerName(this.group);
    this.ram = serverGroupTemplate.getRam();
    this.staticServer = serverGroupTemplate.isStaticServer();
    this.maxPlayers = serverGroupTemplate.getMaxPlayers();
    this.wrapperName = serverGroupTemplate.getWrapperName();
  }

  @Override
  public void start(Wrapper wrapper) {

    if(isStaticServer()){
      try {
        this.wrapper =
            CloudMaster.getInstance()
                .getInstanceManager()
                .getWrapper(wrapperName);
      } catch (NullPointerException e) {
        CloudMaster.getInstance().getLogger().error(e.getMessage());
        return;
      }
    }else{
      this.wrapper = wrapper;
    }
    //TODO send start packet
  }

  @Override
  public void stop() {
    CloudMaster.getInstance().getInstanceManager().unregisterServer(this);
  }

  @Override
  public void onConnect(NetFrameConnection connection) {
    this.connection = connection;
    //TODO set port and address
  }

  @Override
  public void onDisconnect() {
    stop();
  }

  @Override
  public void onHandshakeSuccess() {}

  public InetAddress getAddress() {

    if (isConnected()) {
      CloudMaster.getInstance()
          .getLogger()
          .error("Cannot return address from Server cause its not connected.");
      return null;
    }
    return address;
  }

  public int getPort() {

    if (isConnected()) {
      CloudMaster.getInstance()
          .getLogger()
          .error("Cannot return port from Server cause its not connected.");
      return -1;
    }
    return port;
  }
}
