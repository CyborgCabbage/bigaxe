package net.cyborgcabbage.bigaxe.events;

import net.minecraft.block.BlockBase;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.modificationstation.stationloader.api.common.event.recipe.RecipeRegister;
import net.modificationstation.stationloader.api.common.recipe.CraftingRegistry;

public class RecipeListener implements RecipeRegister {
    @Override
    public void registerRecipes(String recipeType) {
        System.out.println("BIGAXE RECIPE");
        RecipeRegister.Vanilla type = RecipeRegister.Vanilla.fromType(recipeType);
        if (type == Vanilla.CRAFTING_SHAPED) {
            CraftingRegistry.INSTANCE.addShapedRecipe( new ItemInstance(ItemListener.getItem("diamondBigaxe"),1,0),
                    "e",
                    "e",
                    "e",
                    'e', new ItemInstance(ItemBase.seeds)
            );
            CraftingRegistry.INSTANCE.addShapedRecipe( new ItemInstance(ItemBase.seeds),
                    "e",
                    "e",
                    'e', new ItemInstance(ItemBase.seeds)
            );
            CraftingRegistry.INSTANCE.addShapedRecipe( new ItemInstance(ItemListener.getItem("diamondBigaxe"),1,0),
                    "pp",
                    'p', new ItemInstance(ItemBase.diamondPickaxe,1,0)
            );
        }
    }
}
