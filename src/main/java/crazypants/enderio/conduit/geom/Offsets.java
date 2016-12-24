package crazypants.enderio.conduit.geom;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import crazypants.enderio.conduit.IConduit;
import crazypants.enderio.conduit.item.IItemConduit;
import crazypants.enderio.conduit.liquid.ILiquidConduit;
import crazypants.enderio.conduit.me.IMEConduit;
import crazypants.enderio.conduit.oc.IOCConduit;
import crazypants.enderio.conduit.power.IPowerConduit;
import crazypants.enderio.conduit.redstone.IRedstoneConduit;
import crazypants.enderio.conduit.rs.IRSConduit;
import net.minecraft.util.EnumFacing;

public class Offsets {

  /**
   * Registers a set of offsets for a new conduit. (API method)
   * 
   * @param type
   *          The class of the conduit
   * @param none
   *          The offset for the node
   * @param x
   *          The offset for conduit arms on the X axis
   * @param y
   *          The offset for conduit arms on the Y axis
   * @param z
   *          The offset for conduit arms on the Z axis
   * @return true if the offset was registered. false if the conduit already is registered of if one of the axis are already in use.
   */
  public static boolean registerOffsets(Class<? extends IConduit> type, Offset none, Offset x, Offset y, Offset z) {
    OffsetKey keyNone = key(type, Axis.NONE);
    OffsetKey keyX = key(type, Axis.X);
    OffsetKey keyY = key(type, Axis.Y);
    OffsetKey keyZ = key(type, Axis.Z);
    if (OFFSETS.containsKey(keyNone) || OFFSETS.containsKey(keyX) || OFFSETS.containsKey(keyY) || OFFSETS.containsKey(keyZ)) {
      return false;
    }
    for (Entry<OffsetKey, Offset> elem : OFFSETS.entrySet()) {
      if (elem.getKey().axis == Axis.NONE && elem.getValue() == none) {
        return false;
      }
      if (elem.getKey().axis == Axis.X && elem.getValue() == x) {
        return false;
      }
      if (elem.getKey().axis == Axis.Y && elem.getValue() == y) {
        return false;
      }
      if (elem.getKey().axis == Axis.Z && elem.getValue() == z) {
        return false;
      }
    }
    OFFSETS.put(keyNone, none);
    OFFSETS.put(keyX, x);
    OFFSETS.put(keyY, y);
    OFFSETS.put(keyZ, z);
    return true;
  }

  private static Map<OffsetKey, Offset> OFFSETS = new HashMap<OffsetKey, Offset>();

  static {
    OFFSETS.put(key(IRedstoneConduit.class, Axis.NONE), Offset.UP);
    OFFSETS.put(key(IRedstoneConduit.class, Axis.X), Offset.UP);
    OFFSETS.put(key(IRedstoneConduit.class, Axis.Y), Offset.NORTH);
    OFFSETS.put(key(IRedstoneConduit.class, Axis.Z), Offset.UP);

    OFFSETS.put(key(IPowerConduit.class, Axis.NONE), Offset.DOWN);
    OFFSETS.put(key(IPowerConduit.class, Axis.X), Offset.DOWN);
    OFFSETS.put(key(IPowerConduit.class, Axis.Y), Offset.SOUTH);
    OFFSETS.put(key(IPowerConduit.class, Axis.Z), Offset.DOWN);

    OFFSETS.put(key(ILiquidConduit.class, Axis.NONE), Offset.WEST);
    OFFSETS.put(key(ILiquidConduit.class, Axis.X), Offset.NORTH);
    OFFSETS.put(key(ILiquidConduit.class, Axis.Y), Offset.WEST);
    OFFSETS.put(key(ILiquidConduit.class, Axis.Z), Offset.WEST);

    OFFSETS.put(key(IItemConduit.class, Axis.NONE), Offset.EAST);
    OFFSETS.put(key(IItemConduit.class, Axis.X), Offset.SOUTH);
    OFFSETS.put(key(IItemConduit.class, Axis.Y), Offset.EAST);
    OFFSETS.put(key(IItemConduit.class, Axis.Z), Offset.EAST);

//    OFFSETS.put(key(IGasConduit.class, Axis.NONE), Offset.NORTH_UP);
//    OFFSETS.put(key(IGasConduit.class, Axis.X), Offset.NORTH_UP);
//    OFFSETS.put(key(IGasConduit.class, Axis.Y), Offset.NORTH_WEST);
//    OFFSETS.put(key(IGasConduit.class, Axis.Z), Offset.WEST_UP);

    OFFSETS.put(key(IMEConduit.class, Axis.NONE), Offset.SOUTH_UP);
    OFFSETS.put(key(IMEConduit.class, Axis.X), Offset.SOUTH_UP);
    OFFSETS.put(key(IMEConduit.class, Axis.Y), Offset.NORTH_EAST);
    OFFSETS.put(key(IMEConduit.class, Axis.Z), Offset.EAST_UP);

    OFFSETS.put(key(IOCConduit.class, Axis.NONE), Offset.NORTH_DOWN);
    OFFSETS.put(key(IOCConduit.class, Axis.X), Offset.NORTH_DOWN);
    OFFSETS.put(key(IOCConduit.class, Axis.Y), Offset.SOUTH_WEST);
    OFFSETS.put(key(IOCConduit.class, Axis.Z), Offset.WEST_DOWN);

    OFFSETS.put(key(IRSConduit.class, Axis.NONE), Offset.SOUTH_DOWN);
    OFFSETS.put(key(IRSConduit.class, Axis.X), Offset.SOUTH_DOWN);
    OFFSETS.put(key(IRSConduit.class, Axis.Y), Offset.SOUTH_EAST);
    OFFSETS.put(key(IRSConduit.class, Axis.Z), Offset.EAST_DOWN);

    // TODO: Change this example to something not already in use
    // OFFSETS.put(key(IxConduit.class, Axis.NONE), Offset.SOUTH_DOWN);
    // OFFSETS.put(key(IxConduit.class, Axis.X), Offset.SOUTH_DOWN);
    // OFFSETS.put(key(IxConduit.class, Axis.Y), Offset.SOUTH_EAST);
    // OFFSETS.put(key(IxConduit.class, Axis.Z), Offset.EAST_DOWN);

  }

  public static Offset get(Class<? extends IConduit> type, EnumFacing dir) {
    Offset res = OFFSETS.get(key(type, getAxisForDir(dir)));
    if(res == null) {
      res = Offset.NONE;
    }
    return res;
  }

  public static OffsetKey key(Class<? extends IConduit> type, Axis axis) {
    return new OffsetKey(type, axis);
  }

  public static Axis getAxisForDir(EnumFacing dir) {
    if(dir == null) {
      return Axis.NONE;
    }
    if(dir == EnumFacing.EAST || dir == EnumFacing.WEST) {
      return Axis.X;
    }
    if(dir == EnumFacing.UP || dir == EnumFacing.DOWN) {
      return Axis.Y;
    }
    if(dir == EnumFacing.NORTH || dir == EnumFacing.SOUTH) {
      return Axis.Z;
    }
    return Axis.NONE;
  }

  public static enum Axis {
    NONE,
    X,
    Y,
    Z
  }

  public static class OffsetKey {

    String typeName;
    Axis axis;

    private OffsetKey(Class<? extends IConduit> type, Axis axis) {
      this.typeName = type.getCanonicalName();
      this.axis = axis;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((axis == null) ? 0 : axis.hashCode());
      result = prime * result + ((typeName == null) ? 0 : typeName.hashCode());
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if(this == obj) {
        return true;
      }
      if(obj == null) {
        return false;
      }
      if(getClass() != obj.getClass()) {
        return false;
      }
      OffsetKey other = (OffsetKey) obj;
      if(axis != other.axis) {
        return false;
      }
      if(typeName == null) {
        if(other.typeName != null) {
          return false;
        }
      } else if(!typeName.equals(other.typeName)) {
        return false;
      }
      return true;
    }

    @Override
    public String toString() {
      return "OffsetKey [typeName=" + typeName + ", axis=" + axis + "]";
    }

  }

}
