package net.amethyse.cloud.master.instance.impl.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import net.amethyse.cloud.master.CloudMaster;
import net.amethyse.cloud.master.file.FileManager;
import net.amethyse.cloud.master.file.configs.ProxyGroupsFile;
import net.amethyse.cloud.master.file.configs.ServerGroupsFile;
import net.amethyse.cloud.master.instance.InstanceManager;
import net.amethyse.cloud.master.instance.Proxy;
import net.amethyse.cloud.master.instance.Server;
import net.amethyse.cloud.master.instance.Wrapper;
import net.amethyse.cloud.master.instance.impl.ProxyImpl;
import net.amethyse.cloud.master.instance.impl.ServerImpl;
import net.amethyse.cloud.master.templates.ProxyGroupTemplate;
import net.amethyse.cloud.master.templates.ServerGroupTemplate;

/*******************************************************
 * Copyright (C) 2015-2019 Piinguiin neuraxhd@gmail.com
 *
 * This file is part of Cloud and was created at the 05.11.2020
 *
 * Cloud can not be copied and/or distributed without the express
 * permission of the owner.
 *
 *******************************************************/
@AllArgsConstructor
public class InstanceCheckTask extends Thread {

  private InstanceManager instanceManager;

  //TODO: Repair class definition
  private final HashMap<String, HashMap<String, Server>> servers;
  //TODO: Repair class definition
  private final HashMap<String, HashMap<String, Proxy>> proxies;

  //TODO: Repair class definition
  public InstanceCheckTask(InstanceManager instanceManager) {
    instanceManager = instanceManager;
    servers = new HashMap<>();
    proxies = new HashMap<>();
  }

  @Override
  public void run() {

    checkInstances();

    try {
      Thread.sleep(TimeUnit.SECONDS.toMillis(5));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private void checkInstances() {

    checkProxies();
    checkServers();

  }

  private void checkProxies() {

  }

  private void checkServers() {

  }

  private void startServers(FileManager fileManager) {
    //TODO: Repair class definition
    ServerGroupsFile serverGroupsFile = ServerGroupsFile.getDefault();
    //TODO: Repair class definition
    int toStart = 1;

    for (ServerGroupTemplate templates : serverGroupsFile.getServerTemplates()) {
      this.servers.put(templates.getName(), new HashMap<>());
      for (int i = 0; i < toStart; i++) {
        Server server = new ServerImpl(templates);

        Wrapper wrapper;

        try {
          wrapper = instanceManager.getFreeWrapper();
        } catch (NullPointerException e) {
          CloudMaster.getInstance().getLogger().error("Cannot start server.");
          CloudMaster.getInstance().getLogger().error(e.getMessage());
          return;
        }

        server.start(wrapper);
        this.servers.get(templates.getName()).put(server.getName(), server);
      }
    }

  }

  private void startProxies(FileManager fileManager) {
    //TODO: Repair class definition
    ProxyGroupsFile proxyGroupsFile = ProxyGroupsFile.getDefault();

    for (ProxyGroupTemplate templates : proxyGroupsFile.getProxyTemplates()) {
      int toStart = templates.getOnlineAmount();
      this.proxies.put(templates.getName(), new HashMap<>());
      for (int i = 0; i < toStart; i++) {
        Proxy proxy = new ProxyImpl(templates);

        Wrapper wrapper;

        try {
          wrapper = instanceManager.getFreeWrapper();
        } catch (NullPointerException e) {
          CloudMaster.getInstance().getLogger().error("Cannot start proxy.");
          CloudMaster.getInstance().getLogger().error(e.getMessage());
          return;
        }

        proxy.start(wrapper);
        this.proxies.get(templates.getName()).put(proxy.getName(), proxy);
      }
    }
  }
}
