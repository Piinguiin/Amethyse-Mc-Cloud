package net.amethyse.cloud.master.network.packets.proxy.out;

import de.piinguiin.netframe.commons.buffer.NetFrameBuffer;
import de.piinguiin.netframe.commons.protocol.packet.NetFramePacket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*******************************************************
 * Copyright (C) 2015-2019 Piinguiin neuraxhd@gmail.com
 *
 * This file is part of Cloud and was created at the 29.10.2020
 *
 * Cloud can not be copied and/or distributed without the express
 * permission of the owner.
 *
 *******************************************************/
@NoArgsConstructor
@AllArgsConstructor
public class ProxyStartPacket implements NetFramePacket {

  @Getter
  private String template;

  @Override
  public void write(NetFrameBuffer netFrameBuffer) {
    netFrameBuffer.writeString(this.template);
  }

  @Override
  public void read(NetFrameBuffer netFrameBuffer) {
    this.template = netFrameBuffer.readString();
  }
}
