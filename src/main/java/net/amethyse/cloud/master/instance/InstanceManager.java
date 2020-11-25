package net.amethyse.cloud.master.instance;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import lombok.Getter;
import net.amethyse.cloud.master.CloudMaster;
import net.amethyse.cloud.master.file.FileManager;
import net.amethyse.cloud.master.file.configs.ProxyGroupsFile;
import net.amethyse.cloud.master.file.configs.ServerGroupsFile;
import net.amethyse.cloud.master.instance.impl.ProxyImpl;
import net.amethyse.cloud.master.instance.impl.ServerImpl;
import net.amethyse.cloud.master.instance.impl.task.InstanceCheckTask;
import net.amethyse.cloud.master.network.Communicatable;
import net.amethyse.cloud.master.templates.ProxyGroupTemplate;
import net.amethyse.cloud.master.templates.ServerGroupTemplate;

/**
 * ***************************************************** Copyright (C) 2015-2019 Piinguiin
 * neuraxhd@gmail.com
 *
 * <p>This file is part of Cloud and was created at the 29.10.2020
 *
 * <p>Cloud can not be copied and/or distributed without the express permission of the owner.
 *
 * <p>*****************************************************
 */
public class InstanceManager {

  @Getter
  private Map<String, Wrapper> wrappers;
  @Getter
  private Map<String, Map<String, Server>> servers;
  @Getter
  private Map<String, Map<String, Proxy>> proxies;

  public InstanceManager() {
    this.wrappers = new HashMap<>();
    this.servers = new HashMap<>();
    this.proxies = new HashMap<>();
    loadTemplates();
    startInstanceChecker();
  }

  private void startInstanceChecker() {
    new InstanceCheckTask(this).start();
  }

  private void loadTemplates() {

    FileManager fileManager = CloudMaster.getInstance().getFileManager();

    ServerGroupsFile serverGroupsFile = fileManager.getServerGroupsFile();

    for (ServerGroupTemplate templates : serverGroupsFile.getServerTemplates()) {

      if(this.servers.containsKey(templates.getName())){
        continue;
      }

      this.servers.put(templates.getName(), new HashMap<>());
    }

    ProxyGroupsFile proxyGroupsFile = fileManager.getProxyGroupsFile();

    for (ProxyGroupTemplate templates : proxyGroupsFile.getProxyTemplates()) {

      if(this.proxies.containsKey(templates.getName())){
        continue;
      }

      this.proxies.put(templates.getName(), new HashMap<>());
    }

  }

  public void registerWrapper(Wrapper wrapper) {

    if (this.wrappers.containsKey(wrapper.getName())) {
      CloudMaster.getInstance()
              .getLogger()
              .warn("Wrapper with name " + wrapper.getName() + " already registered.");
      CloudMaster.getInstance()
              .getLogger()
              .warn("Prevent starting from Wrapper " + wrapper.getName() + ".");
      return;
    }

    this.wrappers.put(wrapper.getName(), wrapper);
  }

  public void registerServer(Server server) {

    if (!this.servers.containsKey(server.getGroup())) {
      this.servers.put(server.getGroup(), new HashMap<>());
    }

    final Map<String, Server> servers = this.servers.get(server.getGroup());

    if (servers.containsKey(server.getName())) {
      CloudMaster.getInstance()
              .getLogger()
              .warn(
                      "Server with name " + server.getName() + " already existing in group ",
                      server.getGroup() + ".");
      CloudMaster.getInstance()
              .getLogger()
              .warn("Prevent starting from Server " + server.getName() + ".");
      return;
    }

    this.servers.get(server.getGroup()).put(server.getName(), server);
  }

  public void registerProxy(Proxy proxy) {
    if (!this.proxies.containsKey(proxy.getGroup())) {
      this.proxies.put(proxy.getGroup(), new HashMap<>());
    }

    final Map<String, Proxy> proxies = this.proxies.get(proxy.getGroup());

    if (proxies.containsKey(proxy.getName())) {
      CloudMaster.getInstance()
              .getLogger()
              .warn(
                      "Proxy with name " + proxy.getName() + " already existing in group ",
                      proxy.getGroup() + ".");
      CloudMaster.getInstance()
              .getLogger()
              .warn("Prevent starting from Proxy " + proxy.getName() + ".");
      return;
    }

    this.proxies.get(proxy.getGroup()).put(proxy.getName(), proxy);
  }

  public void unregisterProxy(Proxy proxy){

    final Map<String, Proxy> proxies = this.proxies.get(proxy.getGroup());

    if(!proxies.containsKey(proxy.getName())){
      return;
    }

    this.proxies.get(proxy.getGroup()).remove(proxy.getName());
  }

  public void unregisterServer(Server server){

    final Map<String, Server> servers = this.servers.get(server.getGroup());

    if(!servers.containsKey(server.getName())){
      return;
    }

    this.servers.get(server.getGroup()).remove(server.getName());
  }

  public Wrapper getWrapper(String name) throws NullPointerException {
    if (!this.wrappers.containsKey(name)) {
      throw new NullPointerException("No wrapper found with name " + name);
    }
    return this.wrappers.get(name);
  }

  public Server getServer(String group, String name) throws NullPointerException {
    final Map<String, Server> servers = this.servers.get(group);
    if (!servers.containsKey(name)) {
      throw new NullPointerException(
              "No server found with name " + name + " in group " + group + ".");
    }
    return servers.get(name);
  }

  public Server getServer(String name) throws NullPointerException {
    for (String groups : this.servers.keySet()) {
      try {
        return getServer(groups, name);
      } catch (NullPointerException e) {
        CloudMaster.getInstance().getLogger().error(e.getMessage());
      }
    }
    throw new NullPointerException("No server found with name " + name + " in any group.");
  }

  public Proxy getProxy(String group, String name) throws NullPointerException {
    final Map<String, Proxy> proxies = this.proxies.get(group);
    if (!proxies.containsKey(name)) {
      throw new NullPointerException(
              "No proxy found with name " + name + " in group " + group + ".");
    }
    return proxies.get(name);
  }

  public Proxy getProxy(String name) throws NullPointerException {
    for (String groups : this.proxies.keySet()) {
      try {
        return getProxy(groups, name);
      } catch (NullPointerException e) {
        CloudMaster.getInstance().getLogger().error(e.getMessage());
      }
    }
    throw new NullPointerException("No proxy found with name " + name + " in any group.");
  }

  public String generateNewWrapperName() {

    int start = 1;

    while (this.wrappers.containsKey("WRAPPER-" + start)) {
      start++;
    }
    return "WRAPPER-" + start;
  }

  public String generateNewServerName(String group) {

    int start = 1;
    String name = group + "-" + 1;

    while (getServer(group, name) != null) {
      start++;
      name = group + "-" + start;
    }
    return name;
  }

  public String generateNewProxyName(String group) {
    int start = 1;
    String name = group + "-" + 1;

    while (getProxy(group, name) != null) {
      start++;
      name = group + "-" + start;
    }
    return name;
  }

  public Wrapper getFreeWrapper() throws NullPointerException {
    if (this.wrappers.isEmpty()) {
      throw new NullPointerException("No wrapper found. Please start one first.");
    }
    // TODO implement more then 1
    return this.wrappers.get("WRAPPER-1");
  }

  public void stopAllServers() {
    stopWrapper();
  }

  private void stopWrapper() {
    if (this.wrappers.isEmpty()) {
      return;
    }

    this.wrappers.values().forEach(Communicatable::onDisconnect);
  }
}
