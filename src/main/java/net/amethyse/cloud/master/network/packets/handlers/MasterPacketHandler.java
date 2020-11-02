package net.amethyse.cloud.master.network.packets.handlers;

import de.piinguiin.netframe.commons.protocol.context.NetFramePacketContext;
import de.piinguiin.netframe.commons.protocol.handler.PacketHandler;
import de.piinguiin.netframe.commons.protocol.handler.PacketHandlerMethod;
import net.amethyse.cloud.master.CloudMaster;
import net.amethyse.cloud.master.instance.Wrapper;
import net.amethyse.cloud.master.instance.impl.WrapperImpl;
import net.amethyse.cloud.master.network.packets.HandshakePacket;
import net.amethyse.cloud.master.network.packets.wrapper.out.WrapperConnectedPacket;

/*******************************************************
 * Copyright (C) 2015-2019 Piinguiin neuraxhd@gmail.com
 *
 * This file is part of Cloud and was created at the 31.10.2020
 *
 * Cloud can not be copied and/or distributed without the express
 * permission of the owner.
 *
 *******************************************************/
public class MasterPacketHandler implements PacketHandler {

  @PacketHandlerMethod
  public void onHandshakePacket(NetFramePacketContext connection, HandshakePacket handshakePacket) {

    switch (handshakePacket.getAsInstanceType()) {

      case WRAPPER:
        Wrapper wrapper = new WrapperImpl();
        wrapper.onConnect(connection.getConnection());
        CloudMaster.getInstance().getInstanceManager().registerWrapper(wrapper);
        connection.getConnection().sendPackets(new WrapperConnectedPacket(true));
        CloudMaster.getInstance().getLogger().info(
            wrapper.getName() + " connected! Registered wrapper: " + CloudMaster.getInstance()
                .getInstanceManager().getWrappers().size() + ".");
        break;

      case SERVER:
        CloudMaster.getInstance().getLogger().info("Handshake from server.");
        break;

      case PROXY:
        CloudMaster.getInstance().getLogger().info("Handshake from proxy.");
        break;

      default:
        CloudMaster.getInstance().getLogger()
            .warn("Cannot accept HandshakePacket cause type is invalid.");
        break;

    }


  }

}
