package net.amethyse.cloud.master.network.protocol;

import lombok.Getter;

public class ProtocolHandler {

  @Getter
  private final MasterProtocol masterProtocol;

  public ProtocolHandler(){
    masterProtocol = new MasterProtocol();
  }

}
