package net.amethyse.cloud.master.file;

import java.io.File;
import lombok.Getter;
import net.amethyse.cloud.lib.gson.GsonFileReader;
import net.amethyse.cloud.master.file.configs.ProxyGroupsFile;
import net.amethyse.cloud.master.file.configs.ServerGroupsFile;
import org.slf4j.Logger;

/**
 * ***************************************************** Copyright (C) 2015-2019 Piinguiin
 * neuraxhd@gmail.com
 *
 * <p>This file is part of Cloud and was created at the 28.10.2020
 *
 * <p>Cloud can not be copied and/or distributed without the express permission of the owner.
 *
 * <p>*****************************************************
 */
@Getter
public class FileManager {

  private final Logger logger;
  private File baseDirectory;
  private File configsDirectory;
  private File templatesDirectory;
  private File serverTemplatesDirectory;
  private File serverGlobalDirectory;
  private File proxyTemplatesDirectory;
  private File proxyGlobalDirectory;
  private File temporaryDirectory;
  private File logsDirectory;
  private File debugDirectory;
  private File pluginsDirectory;

  private ServerGroupsFile serverGroupsFile;
  private ProxyGroupsFile proxyGroupsFile;

  public FileManager(Logger logger) {
    this.logger = logger;
    load();
  }

  public void load() {

    try {

      baseDirectory = new File("master/");
      if (baseDirectory.mkdirs()) {
        logger.info("Master directory created.");
      }

      configsDirectory = new File(baseDirectory, "configs/");

      if (configsDirectory.mkdirs()) {
        logger.info("Configs directory created.");
      }

      templatesDirectory = new File(baseDirectory, "templates/");

      if (templatesDirectory.mkdirs()) {
        logger.info("Templates directory created.");
      }

      serverTemplatesDirectory = new File(templatesDirectory, "server");

      if (serverTemplatesDirectory.mkdirs()) {
        logger.info("Server template directory created.");
      }

      serverGlobalDirectory = new File(serverTemplatesDirectory, "global/");

      if (serverGlobalDirectory.mkdirs()) {
        logger.info("Server global directory created.");
      }

      final boolean mkdirs = new File(serverGlobalDirectory, "plugins/").mkdirs();

      proxyTemplatesDirectory = new File(templatesDirectory, "proxy/");

      if (proxyTemplatesDirectory.mkdirs()) {
        logger.info("Proxy templates directory created.");
      }

      proxyGlobalDirectory = new File(proxyTemplatesDirectory, "global/");

      if (proxyGlobalDirectory.mkdirs()) {
        logger.info("Proxy global directory created.");
      }

      final boolean mkdirs1 = new File(proxyGlobalDirectory, "plugins/").mkdirs();

      temporaryDirectory = new File(baseDirectory, "temporary/");

      if (temporaryDirectory.mkdirs()) {
        logger.info("Temporary directory created.");
      }

      logsDirectory = new File(baseDirectory, "logs/");

      if (logsDirectory.mkdirs()) {
        logger.info("Logs directory created.");
      }

      debugDirectory = new File(baseDirectory, "debug/");

      if (debugDirectory.mkdirs()) {
        logger.info("Debug directory created.");
      }

      pluginsDirectory = new File(baseDirectory, "plugins/");

      if (pluginsDirectory.mkdirs()) {
        logger.info("Plugins directory created.");
      }

      this.serverGroupsFile =
              new GsonFileReader(logger, getBaseDirectory(), "ServerGroups")
                      .read(ServerGroupsFile.class, ServerGroupsFile.getDefault());
      this.proxyGroupsFile =
              new GsonFileReader(logger, getBaseDirectory(), "ProxyGroups")
                      .read(ProxyGroupsFile.class, ProxyGroupsFile.getDefault());

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private File getBaseDirectory() {
    if (baseDirectory == null) {
      return new File("master/");
    }
    return baseDirectory;
  }

  //TODO: Repair class definition
  public ServerGroupsFile getServerGroupsFile() {
    return serverGroupsFile;
  }

  //TODO: Repair class definition
  public ProxyGroupsFile getProxyGroupsFile() {
    return proxyGroupsFile;
  }
}
