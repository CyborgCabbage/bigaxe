package net.cyborgcabbage.bigaxe.events;

import com.google.common.collect.Maps;
import net.cyborgcabbage.bigaxe.Bigaxe;
import net.cyborgcabbage.bigaxe.items.DiamondBigaxe;
import net.minecraft.block.BlockBase;
import net.minecraft.item.ItemBase;
import net.minecraft.item.tool.ToolMaterial;
import net.modificationstation.stationloader.api.common.event.item.ItemRegister;

import java.util.Map;
import java.util.Set;

public class ItemListener implements ItemRegister {
    private static final Map<String, ItemBase> ITEMS = Maps.newHashMap();
    private static Set<Integer> occupiedIDs;
    private static int startID = 4400;

    @Override
    public void registerItems() {
        System.out.println("BIGAXE ITEM");
        Bigaxe.configItems.load();
        occupiedIDs = Bigaxe.configItems.getSet("items");
        System.out.println("BIGAXE ITEM1");
        ITEMS.put("diamondBigaxe", new DiamondBigaxe(getID("diamondBigaxe"), ToolMaterial.IRON).setName("diamondBigaxe"));
        System.out.println("BIGAXE ITEM2");
        occupiedIDs = null;
        Bigaxe.configItems.save();
        System.out.println("BIGAXE ITEM3");
    }

    private static int getID(String name) {
        while ((ItemBase.byId[startID + BlockBase.BY_ID.length] != null || occupiedIDs.contains(startID)) && startID < ItemBase.byId.length) {
            startID++;
        }
        return Bigaxe.configBlocks.getInt("items." + name, startID);
    }

    @SuppressWarnings("unchecked")
    public static <T extends ItemBase> T getItem(String name) {
        return (T) ITEMS.get(name);
    }
}
