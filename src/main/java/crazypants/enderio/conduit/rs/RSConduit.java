package crazypants.enderio.conduit.rs;

import static crazypants.enderio.ModObject.itemRSConduit;

import java.util.EnumSet;
import java.util.List;

import com.enderio.core.common.BlockEnder;
import com.enderio.core.common.util.BlockCoord;
import com.enderio.core.common.vecmath.Vector4f;

import appeng.api.AEApi;
import appeng.api.networking.IGridConnection;
import appeng.api.networking.IGridHost;
import appeng.api.networking.IGridNode;
import appeng.api.parts.IPart;
import appeng.api.parts.IPartHost;
import appeng.api.util.AEPartLocation;
import com.raoulvdberge.refinedstorage.api.network.INetworkMaster;
import com.raoulvdberge.refinedstorage.api.network.INetworkNode;
import com.raoulvdberge.refinedstorage.api.network.INetworkNodeGraph;
import crazypants.enderio.conduit.AbstractConduit;
import crazypants.enderio.conduit.AbstractConduitNetwork;
import crazypants.enderio.conduit.ConduitUtil;
import crazypants.enderio.conduit.ConnectionMode;
import crazypants.enderio.conduit.IConduit;
import crazypants.enderio.conduit.IConduitBundle;
import crazypants.enderio.conduit.RaytraceResult;
import crazypants.enderio.conduit.TileConduitBundle;
import crazypants.enderio.conduit.geom.CollidableComponent;
import crazypants.enderio.render.registry.TextureRegistry;
import crazypants.enderio.tool.ToolUtil;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.common.Optional.Method;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RSConduit extends AbstractConduit implements IRSConduit {

  protected
  @Nullable
  RSConduitNetwork network;
  protected final
  @Nonnull
  RSNetworkNode node;

  public static TextureRegistry.TextureSupplier coreTextureN = TextureRegistry.registerTexture("blocks/rsConduitCore");
  public static TextureRegistry.TextureSupplier longTextureN = TextureRegistry.registerTexture("blocks/rsConduit");

  public RSConduit() {
    this(0);
  }

  public RSConduit(int itemDamage) {
    super();

    node = new RSNetworkNode(this);
  }

  @SideOnly(Side.CLIENT)
  public static void initIcons() {
  }

  @Override
  public Class<? extends IConduit> getBaseConduitType() {
    return IRSConduit.class;
  }

  @Override
  public ItemStack createItem() {
    return new ItemStack(itemRSConduit.getItem(), 1, 0);
  }

  @Override
  public AbstractConduitNetwork<?, ?> getNetwork() {
    return network;
  }

  @Override
  public boolean setNetwork(AbstractConduitNetwork<?, ?> network) {
    this.network = (RSConduitNetwork) network;
    return true;
  }

  @CapabilityInject(INetworkNode.class)
  public static Capability<INetworkNode> NETWORK_NODE_CAPABILITY = null;

  @Override
  @Method(modid = "refinedstorage")
  public boolean canConnectToExternal(EnumFacing dir, boolean ignoreDisabled) {
    World world = getBundle().getBundleWorldObj();
    TileEntity te = world.getTileEntity(getLocation().getLocation(dir).getBlockPos());
    if (te == null) {
      return false;
    }

    if (te instanceof INetworkNode) {
      return ((INetworkNode) te).canConduct(dir.getOpposite());
    }

    // Controllers are INetworkMasters
    if (te instanceof INetworkMaster) {
      return true;
    }

    if (NETWORK_NODE_CAPABILITY == null) {
      return false;
    }
    INetworkNode externalNode = te.getCapability(NETWORK_NODE_CAPABILITY, dir.getOpposite());
    if (externalNode == null) {
      return false;
    }

    return externalNode.canConduct(dir.getOpposite());
  }

  @Override
  public void connectionsChanged() {
    super.connectionsChanged();

    INetworkNode networkNode = getNode();
    INetworkMaster network = networkNode != null ? networkNode.getNetwork() : null;
    INetworkNodeGraph graph = network != null ? network.getNodeGraph() : null;
    if (graph != null) {
      System.out.println("RSConduit.connectionsChanged graph.rebuild()");
      graph.rebuild();
    } else {
      System.out.println("RSConduit.connectionsChanged missing graph");
    }
  }

  @Override
  public TextureAtlasSprite getTextureForState(CollidableComponent component) {
    if(component.dir == null) {
      return coreTextureN.get(TextureAtlasSprite.class);
    } else {
      return longTextureN.get(TextureAtlasSprite.class);
    }
  }

  @Override
  public TextureAtlasSprite getTransmitionTextureForState(CollidableComponent component) {
    return null;
  }

  @Override
  public Vector4f getTransmitionTextureColorForState(CollidableComponent component) {
    return null;
  }

  @Override
  public ConnectionMode getNextConnectionMode(EnumFacing dir) {
    ConnectionMode mode = getConnectionMode(dir);
    mode = mode == ConnectionMode.IN_OUT ? ConnectionMode.DISABLED : ConnectionMode.IN_OUT;
    return mode;
  }

  @Override
  public ConnectionMode getPreviousConnectionMode(EnumFacing dir) {
    return getNextConnectionMode(dir);
  }

  @Override
  public boolean canConnectToConduit(EnumFacing direction, IConduit conduit) {
    if(!super.canConnectToConduit(direction, conduit)) {
      return false;
    }
    return conduit instanceof IRSConduit;
  }

  @Override
  @Method(modid = "refinedstorage")
  public INetworkNode getNode() {
    return node;
  }
}
