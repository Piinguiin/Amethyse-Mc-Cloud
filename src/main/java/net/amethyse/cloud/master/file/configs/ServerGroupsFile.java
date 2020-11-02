package net.amethyse.cloud.master.file.configs;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.amethyse.cloud.master.templates.ServerGroupTemplate;

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
public class ServerGroupsFile {

    private List<ServerGroupTemplate> serverTemplates;

    public static ServerGroupsFile getDefault(){
      return new ServerGroupsFile(new ArrayList<>());
    }

}
