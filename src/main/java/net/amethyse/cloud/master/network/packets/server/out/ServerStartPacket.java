package net.amethyse.cloud.master.network.packets.server.out;

import de.piinguiin.netframe.commons.buffer.NetFrameBuffer;
import de.piinguiin.netframe.commons.protocol.packet.NetFramePacket;
import lombok.Getter;

/*******************************************************
 * Copyright (C) 2015-2019 Piinguiin neuraxhd@gmail.com
 *
 * This file is part of Cloud and was created at the 29.10.2020
 *
 * Cloud can not be copied and/or distributed without the express
 * permission of the owner.
 *
 *******************************************************/
public class ServerStartPacket implements NetFramePacket {

  @Getter
  private String template;

  public ServerStartPacket(){}

  public ServerStartPacket(String template){
    this.template = template;
  }

  @Override
  public void write(NetFrameBuffer netFrameBuffer) {
    netFrameBuffer.writeString(this.template);
  }

  @Override
  public void read(NetFrameBuffer netFrameBuffer) {
    this.template = netFrameBuffer.readString();
  }
}
