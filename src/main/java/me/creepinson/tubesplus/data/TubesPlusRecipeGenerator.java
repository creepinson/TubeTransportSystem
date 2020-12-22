package me.creepinson.tubesplus.data;

import me.creepinson.tubesplus.handler.TubesPlusRegistryHandler;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.item.Items;

import java.util.function.Consumer;

public class TubesPlusRecipeGenerator extends RecipeProvider {

    public TubesPlusRecipeGenerator(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(TubesPlusRegistryHandler.TUBE_BLOCK.get())
                .patternLine("sgs")
                .patternLine("geg")
                .patternLine("sgs")
                .key('s', Blocks.STONE)
                .key('g', Blocks.GLASS)
                .key('e', Items.ENDER_PEARL)
                .build(consumer);
    }
}