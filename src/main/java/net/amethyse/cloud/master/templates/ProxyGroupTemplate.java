package net.amethyse.cloud.master.templates;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.amethyse.cloud.lib.misc.ProxyChooseStrategy;

/*******************************************************
 * Copyright (C) 2015-2019 Piinguiin neuraxhd@gmail.com
 *
 * This file is part of Cloud and was created at the 29.10.2020
 *
 * Cloud can not be copied and/or distributed without the express
 * permission of the owner.
 *
 *******************************************************/
@Data
@AllArgsConstructor
public class ProxyGroupTemplate {

  private String name;
  private int ram;
  private int maxPlayers;
  private int onlineAmount;
  private String messageOfTheDay;
  private ProxyChooseStrategy proxyChooseStrategy;
  private boolean staticProxy;
  private String wrapperName;

}
