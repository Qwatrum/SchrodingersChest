package de.qwatrum.schrodingerschest.datagen;

import de.qwatrum.schrodingerschest.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import de.qwatrum.schrodingerschest.item.ModItems;
import net.minecraft.data.client.*;


public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {}

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.RELIC, Models.GENERATED);
        itemModelGenerator.register(ModItems.SCHRODINGERS_BREAKFAST, Models.GENERATED);

        itemModelGenerator.register(ModItems.RELIC_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.RELIC_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.RELIC_AXE, Models.HANDHELD);
    }
}
