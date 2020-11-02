package net.amethyse.cloud.master.network.protocol;

import de.piinguiin.netframe.commons.protocol.Protocol;
import net.amethyse.cloud.master.CloudMaster;
import net.amethyse.cloud.master.network.packets.HandshakePacket;
import net.amethyse.cloud.master.network.packets.handlers.MasterPacketHandler;
import net.amethyse.cloud.master.network.packets.wrapper.out.WrapperConnectedPacket;
import net.amethyse.cloud.master.network.packets.wrapper.out.WrapperStopPacket;

public class MasterProtocol extends Protocol {

  public MasterProtocol() {
    CloudMaster.getInstance().getLogger().info("Registering packets...");
    registerPackets();
    CloudMaster.getInstance().getLogger().info("Registering handler1...");
    registerHandler();
  }

  private void registerPackets() {
    registerPacket(HandshakePacket.class);
    registerPacket(WrapperStopPacket.class);
    registerPacket(WrapperConnectedPacket.class);
  }

  private void registerHandler() {
    registerListener(new MasterPacketHandler());
  }
}
