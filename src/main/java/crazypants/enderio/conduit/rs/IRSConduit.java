package crazypants.enderio.conduit.rs;

import com.raoulvdberge.refinedstorage.api.network.INetworkNode;
import crazypants.enderio.conduit.IConduit;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.Optional.Method;

import javax.annotation.Nullable;

public interface IRSConduit extends IConduit {

  @Method(modid = "refinedstorage")
  @Nullable INetworkNode getNode();

}
