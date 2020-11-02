package net.amethyse.cloud.wrapper;

import de.piinguiin.netframe.commons.protocol.context.NetFramePacketContext;
import de.piinguiin.netframe.commons.protocol.handler.PacketHandler;
import de.piinguiin.netframe.commons.protocol.handler.PacketHandlerMethod;
import net.amethyse.cloud.master.network.packets.wrapper.out.WrapperConnectedPacket;
import net.amethyse.cloud.master.network.packets.wrapper.out.WrapperStopPacket;

/*******************************************************
 * Copyright (C) 2015-2019 Piinguiin neuraxhd@gmail.com
 *
 * This file is part of Cloud and was created at the 31.10.2020
 *
 * Cloud can not be copied and/or distributed without the express
 * permission of the owner.
 *
 *******************************************************/
public class WrapperPacketHandler implements PacketHandler {

  @PacketHandlerMethod
  public void onSuccessPacket(NetFramePacketContext packetContext, WrapperConnectedPacket wrapperConnectedPacket){
    System.out.println("Received success: "+wrapperConnectedPacket.isSuccess());
  }

  @PacketHandlerMethod
  public void onWrapperStop(NetFramePacketContext context, WrapperStopPacket packet){
    CloudWrapper.getLogger().info("Stopping Wrapper, Reason: "+packet.getReason());
    System.exit(1);
  }

}
