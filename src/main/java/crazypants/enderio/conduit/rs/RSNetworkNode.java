package crazypants.enderio.conduit.rs;

import com.raoulvdberge.refinedstorage.api.network.INetworkMaster;
import com.raoulvdberge.refinedstorage.api.network.INetworkNode;
import crazypants.enderio.conduit.ConnectionMode;

/** Delete for 1.11! */
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Uncomment for 1.11! */
// import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * INetworkNode implementation, tied to a TileConduitBundle.
 *
 * For the Minecraft 1.10 branch of Refined Storage, this will
 * have to be exposed as a capability on the TileConduitBundle.
 *
 * Ideally each RSConduitNetwork would then have only one RSNetworkNode and
 * all of its associated conduits would expose the same node, but RS isn't
 * quite there yet.
 */
public class RSNetworkNode implements INetworkNode {

  private final @Nonnull IRSConduit conduit;

  private @Nullable INetworkMaster network;

  public RSNetworkNode(IRSConduit conduit) {
    this.conduit = conduit;
  }

  @Override
  public int getEnergyUsage() {
    return 0;
  }

  /** Delete for 1.11! */
  @Override
  public BlockPos getPosition() {
    return conduit.getLocation().getBlockPos();
  }

  /** Uncomment for 1.11! */
  // @Override
  // @Nonnull
  // ItemStack getItemStack();

  @Override
  public void onConnected(INetworkMaster network) {
    this.network = network;
  }

  @Override
  public void onDisconnected(INetworkMaster network) {
    this.network = null;
  }

  @Override
  public boolean isConnected() {
    return network != null;
  }

  @Override
  public boolean canUpdate() {
    return false;
  }

  /** Delete for 1.11! Replaced by a capability. */
  @Override
  public boolean canConduct(EnumFacing direction) {
    return conduit.getConnectionMode(direction) != ConnectionMode.DISABLED;
  }

  @Override
  public INetworkMaster getNetwork() {
    return network;
  }

  /** Delete for 1.11! */
  @Override
  public World getNodeWorld() {
    return conduit.getBundle().getBundleWorldObj();
  }
}
