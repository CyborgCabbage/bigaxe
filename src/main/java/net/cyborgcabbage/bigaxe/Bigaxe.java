package net.cyborgcabbage.bigaxe;

import net.cyborgcabbage.bigaxe.events.ItemListener;
import net.cyborgcabbage.bigaxe.events.RecipeListener;
import net.cyborgcabbage.bigaxe.events.TextureListener;
import net.modificationstation.stationloader.api.client.event.texture.TextureRegister;
import net.modificationstation.stationloader.api.common.event.item.ItemRegister;
import net.modificationstation.stationloader.api.common.event.recipe.RecipeRegister;
import net.modificationstation.stationloader.api.common.mod.StationMod;

import java.util.Locale;

public class Bigaxe implements StationMod {
    public static final String MOD_ID = "bigaxe";
    public static JsonConfig configBlocks = new JsonConfig("blocks");
    public static JsonConfig configItems = new JsonConfig("items");

    @Override
    public void preInit() {
        TextureRegister.EVENT.register(new TextureListener());
        ItemRegister.EVENT.register(new ItemListener());
        RecipeRegister.EVENT.register(new RecipeListener());
    }

    public static String getTexturePath(String category) {
        return String.format(Locale.ROOT, "/assets/%s/textures/%s/", MOD_ID, category);
    }
}
