package net.amethyse.cloud.master.network.packets.wrapper.out;

import de.piinguiin.netframe.commons.buffer.NetFrameBuffer;
import de.piinguiin.netframe.commons.protocol.packet.NetFramePacket;
import de.piinguiin.netframe.commons.protocol.packet.NetFramePacketMeta;
import lombok.Getter;

/*******************************************************
 * Copyright (C) 2015-2019 Piinguiin neuraxhd@gmail.com
 *
 * This file is part of Cloud and was created at the 31.10.2020
 *
 * Cloud can not be copied and/or distributed without the express
 * permission of the owner.
 *
 *******************************************************/
@NetFramePacketMeta(id = 2)
public class WrapperConnectedPacket implements NetFramePacket {

  @Getter
  private boolean success;

  public WrapperConnectedPacket() { }

  public WrapperConnectedPacket(boolean success) {
    this.success = success;
  }

  @Override
  public void read(NetFrameBuffer netFrameBuffer) {
    this.success = netFrameBuffer.readBoolean();
  }

  @Override
  public void write(NetFrameBuffer netFrameBuffer) {
    netFrameBuffer.writeBoolean(this.success);
  }
}
