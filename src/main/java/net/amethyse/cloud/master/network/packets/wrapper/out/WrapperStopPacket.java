package net.amethyse.cloud.master.network.packets.wrapper.out;

import de.piinguiin.netframe.commons.buffer.NetFrameBuffer;
import de.piinguiin.netframe.commons.protocol.packet.NetFramePacket;
import de.piinguiin.netframe.commons.protocol.packet.NetFramePacketMeta;
import lombok.Getter;

/*******************************************************
 * Copyright (C) 2015-2019 Piinguiin neuraxhd@gmail.com
 *
 * This file is part of Cloud and was created at the 01.11.2020
 *
 * Cloud can not be copied and/or distributed without the express
 * permission of the owner.
 *
 *******************************************************/
@NetFramePacketMeta(id = 3)
public class WrapperStopPacket implements NetFramePacket {

  @Getter
  private String reason;

  public WrapperStopPacket() {}

  public WrapperStopPacket(String reason) {
    this.reason = reason;
  }

  @Override
  public void write(NetFrameBuffer netFrameBuffer) {
    netFrameBuffer.writeString(reason);
  }

  @Override
  public void read(NetFrameBuffer netFrameBuffer) {
    this.reason = netFrameBuffer.readString();
  }
}
