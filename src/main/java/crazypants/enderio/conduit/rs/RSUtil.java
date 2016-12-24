package crazypants.enderio.conduit.rs;

import crazypants.enderio.config.Config;
import net.minecraftforge.fml.common.Loader;

public class RSUtil {

  private static boolean useCheckPerformed = false;
  private static boolean isRsConduitEnabled = false;

  public static boolean isRSEnabled() {
    if(!useCheckPerformed) {
      isRsConduitEnabled = Loader.isModLoaded("refinedstorage") && Config.enableRSConduits;
      useCheckPerformed = true;
    }
    return isRsConduitEnabled;
  }
}
