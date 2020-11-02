package net.amethyse.cloud.master.instance;

import lombok.AllArgsConstructor;
import lombok.Getter;

/*******************************************************
 * Copyright (C) 2015-2019 Piinguiin neuraxhd@gmail.com
 *
 * This file is part of Cloud and was created at the 31.10.2020
 *
 * Cloud can not be copied and/or distributed without the express
 * permission of the owner.
 *
 *******************************************************/
@AllArgsConstructor
@Getter
public enum  InstanceType {

  UNKNOWN(-1),
  SERVER(0),
  PROXY(1),
  WRAPPER(2);

  private int id;

  public static InstanceType getTypeById(int id){

    for(InstanceType types : InstanceType.values()){
      if(types.getId() == id){
        return types;
      }
    }
    return UNKNOWN;
  }

}
