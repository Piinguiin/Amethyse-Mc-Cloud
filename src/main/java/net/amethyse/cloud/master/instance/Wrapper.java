package net.amethyse.cloud.master.instance;

import java.util.Set;
import net.amethyse.cloud.master.network.Communicatable;

/*******************************************************
 * Copyright (C) 2015-2019 Piinguiin neuraxhd@gmail.com
 *
 * This file is part of Cloud and was created at the 29.10.2020
 *
 * Cloud can not be copied and/or distributed without the express
 * permission of the owner.
 *
 *******************************************************/
public interface Wrapper extends Communicatable {

  String getName();

  Set<Server> getServers();

  Set<Proxy> getProxies();

  default void addServer(Server server){
    if(getServers().contains(server)){
      return;
    }
    getServers().add(server);
  }

  default void removeServer(Server server){

    if(!getServers().contains(server)){
      return;
    }

    getServers().remove(server);
  }

  default void addProxy(Proxy proxy){

    if(getProxies().contains(proxy)){
      return;
    }

    getProxies().add(proxy);
  }

  default void removeProxy(Proxy proxy){

    if(!getProxies().contains(proxy)){
      return;
    }

    getProxies().remove(proxy);
  }

}
