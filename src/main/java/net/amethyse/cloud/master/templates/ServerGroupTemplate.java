package net.amethyse.cloud.master.templates;

import lombok.AllArgsConstructor;
import lombok.Data;

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
public class ServerGroupTemplate {

  private String name;
  private int ram;
  private int onlineAmount;
  private int maxPlayers;
  private boolean staticServer;
  private String wrapperName;

}
