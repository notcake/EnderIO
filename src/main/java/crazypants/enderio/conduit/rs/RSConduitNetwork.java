package crazypants.enderio.conduit.rs;

import crazypants.enderio.conduit.AbstractConduitNetwork;

public class RSConduitNetwork extends AbstractConduitNetwork<IRSConduit, IRSConduit> {

  public RSConduitNetwork() {
    super(IRSConduit.class, IRSConduit.class);
  }

}
