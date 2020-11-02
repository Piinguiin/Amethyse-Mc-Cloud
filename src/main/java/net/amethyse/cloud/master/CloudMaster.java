package net.amethyse.cloud.master;

import de.piinguiin.netframe.commons.config.NetFrameConfig;
import de.piinguiin.netframe.server.NetFrameServer;
import de.piinguiin.netframe.server.NetFrameServerFactory;
import java.util.logging.SimpleFormatter;
import lombok.Getter;
import net.amethyse.cloud.lib.module.CloudModule;
import net.amethyse.cloud.lib.module.ModuleType;
import net.amethyse.cloud.lib.options.OptionSet;
import net.amethyse.cloud.master.command.CommandManager;
import net.amethyse.cloud.master.file.FileManager;
import net.amethyse.cloud.master.instance.InstanceManager;
import net.amethyse.cloud.master.network.protocol.ProtocolHandler;
import org.jline.reader.LineReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ***************************************************** Copyright (C) 2015-2019 Piinguiin
 * neuraxhd@gmail.com
 *
 * <p>This file is part of Cloud and was created at the 26.10.2020
 *
 * <p>Cloud can not be copied and/or distributed without the express permission of the owner.
 *
 * <p>*****************************************************
 */
public class CloudMaster implements CloudModule {

  @Getter private static CloudMaster instance;
  private boolean shuttingDown;
  private OptionSet options;
  @Getter private FileManager fileManager;
  private SimpleFormatter simpleFormatter = new SimpleFormatter();
  @Getter private Logger logger;
  private ProtocolHandler protocolHandler;
  private NetFrameServer server;
  @Getter private InstanceManager instanceManager;
  private CommandManager commandManager;

  private boolean running;
  private boolean waitingForCommand = false;
  private LineReader lineReader;

  @Override
  public void load(OptionSet optionSet) throws Exception {
    instance = this;
    running = true;
    shuttingDown = false;
    logger = LoggerFactory.getLogger("Master");
    fileManager = new FileManager(logger);
    protocolHandler = new ProtocolHandler();
    instanceManager = new InstanceManager();
    startServer();
  }

  private void startServer() {
    NetFrameConfig netFrameConfig =
        NetFrameConfig.newBuilder()
            .setProtocol(protocolHandler.getMasterProtocol())
            .setServerHost("localhost")
            .setServerPort(8080).createNetFrameConfig();
    server = NetFrameServerFactory.createNetFrameServer(netFrameConfig);
    server.start();
  }

  @Override
  public void unload() throws Exception {
    server.stop();
  }

  @Override
  public ModuleType getModuleType() {
    return ModuleType.MASTER;
  }
}
