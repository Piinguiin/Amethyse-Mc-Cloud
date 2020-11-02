package net.amethyse.cloud.master.network.packets;

import de.piinguiin.netframe.commons.buffer.NetFrameBuffer;
import de.piinguiin.netframe.commons.protocol.packet.NetFramePacket;
import de.piinguiin.netframe.commons.protocol.packet.NetFramePacketMeta;
import lombok.Getter;
import net.amethyse.cloud.master.instance.InstanceType;

/*******************************************************
 * Copyright (C) 2015-2019 Piinguiin neuraxhd@gmail.com
 *
 * This file is part of Cloud and was created at the 31.10.2020
 *
 * Cloud can not be copied and/or distributed without the express
 * permission of the owner.
 *
 *******************************************************/
@NetFramePacketMeta(id = 1)
public class HandshakePacket implements NetFramePacket {

  @Getter
  private int type;

  public HandshakePacket(){}

  public HandshakePacket(int type){
    this.type = type;
  }

  @Override
  public void read(NetFrameBuffer netFrameBuffer) {
    type = netFrameBuffer.readInt();
  }

  @Override
  public void write(NetFrameBuffer netFrameBuffer) {
    netFrameBuffer.writeInt(type);
  }

  public InstanceType getAsInstanceType(){
    return InstanceType.getTypeById(type);
  }

}
