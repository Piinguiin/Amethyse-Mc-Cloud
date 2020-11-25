package net.amethyse.cloud.wrapper;

import de.piinguiin.netframe.client.NetFrameClient;
import de.piinguiin.netframe.client.NetFrameClientFactory;
import de.piinguiin.netframe.commons.config.NetFrameConfig;
import de.piinguiin.netframe.commons.protocol.Protocol;
import lombok.Getter;
import net.amethyse.cloud.lib.module.CloudModule;
import net.amethyse.cloud.lib.module.ModuleType;
import net.amethyse.cloud.lib.options.OptionSet;
import net.amethyse.cloud.master.instance.InstanceType;
import net.amethyse.cloud.master.network.packets.HandshakePacket;
import net.amethyse.cloud.master.network.packets.wrapper.out.WrapperConnectedPacket;
import net.amethyse.cloud.master.network.packets.wrapper.out.WrapperStopPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*******************************************************
 * Copyright (C) 2015-2019 Piinguiin neuraxhd@gmail.com
 *
 * This file is part of Cloud and was created at the 26.10.2020
 *
 * Cloud can not be copied and/or distributed without the express
 * permission of the owner.
 *
 *******************************************************/
public class CloudWrapper implements CloudModule {

  @Getter
  private static CloudWrapper instance;
  @Getter
  private static Logger logger;
  private NetFrameClient client;

  @Override
  public void load(OptionSet optionSet) throws Exception {
    instance = this;
    logger = LoggerFactory.getLogger("Wrapper");
    startClient();
  }

  private void startClient() {

    Protocol protocol = new Protocol();
    protocol.registerPacket(HandshakePacket.class);
    protocol.registerPacket(WrapperConnectedPacket.class);
    protocol.registerPacket(WrapperStopPacket.class);
    protocol.registerListener(new WrapperPacketHandler());

    NetFrameConfig config =
            NetFrameConfig.newBuilder()
                    .setProtocol(protocol)
                    .setServerHost("localhost")
                    .setServerPort(8080)
                    .createNetFrameConfig();
    this.client = NetFrameClientFactory.createNetFrameClient(config);
    client.connect();
    client.sendPacket(new HandshakePacket(InstanceType.WRAPPER.getId()));
  }

  @Override
  public void unload() throws Exception {}

  @Override
  public ModuleType getModuleType() {
    return ModuleType.WRAPPER;
  }

  public static void main(String[] args) {
    CloudWrapper cloudWrapper = new CloudWrapper();
    try {
      cloudWrapper.load(null);
    }
    catch (Exception exception) {
      exception.printStackTrace();
    }
  }
}
