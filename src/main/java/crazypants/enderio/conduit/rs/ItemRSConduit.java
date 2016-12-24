package crazypants.enderio.conduit.rs;

import appeng.api.AEApi;
import crazypants.enderio.EnderIO;
import crazypants.enderio.ModObject;
import crazypants.enderio.conduit.AbstractItemConduit;
import crazypants.enderio.conduit.ConduitDisplayMode;
import crazypants.enderio.conduit.IConduit;
import crazypants.enderio.conduit.ItemConduitSubtype;
import crazypants.enderio.gui.IconEIO;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemRSConduit extends AbstractItemConduit {

  private static ItemConduitSubtype[] subtypes = new ItemConduitSubtype[] {
      new ItemConduitSubtype(ModObject.itemRSConduit.name(), EnderIO.DOMAIN + ":itemRsConduit")
  };

  public static ItemRSConduit create() {
    if (RSUtil.isRSEnabled()) {
      ItemRSConduit result = new ItemRSConduit();
      result.init();
      ConduitDisplayMode.registerDisplayMode(new ConduitDisplayMode(IRSConduit.class, IconEIO.WRENCH_OVERLAY_RS, IconEIO.WRENCH_OVERLAY_RS_OFF));
      return result;
    }
    return null;
  }

  protected ItemRSConduit() {
    super(ModObject.itemRSConduit, subtypes);
  }

  @Override
  public Class<? extends IConduit> getBaseConduitType() {
    return IRSConduit.class;
  }

  @Override
  public IConduit createConduit(ItemStack item, EntityPlayer player) {
    return new RSConduit(item.getItemDamage());
  }
  
  @Override
  public boolean shouldHideFacades(ItemStack stack, EntityPlayer player) {
    return true;
  }
}
