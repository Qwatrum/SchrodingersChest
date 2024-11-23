package de.qwatrum.schrodingerschest.item;

import de.qwatrum.schrodingerschest.Schrodingerschest;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item RELIC = registerItem("relic", new Item(new Item.Settings()));

    public static final Item PLACE_HOLDER = registerItem("place_holder", new Item(new Item.Settings()));
    public static final Item SCHRODINGERS_BREAKFAST = registerItem("schrodingers_breakfast", new Item(new Item.Settings()));
    public static final Item SCHRODINGERS_STAFF = registerItem("schrodingers_staff", new Item(new Item.Settings().maxCount(1)));
    public static final Item RELIC_SWORD = registerItem("relic_sword",
            new SwordItem(ModToolMaterials.RELIC, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolMaterials.RELIC, 1, -2.4f))));
    public static final Item RELIC_PICKAXE = registerItem("relic_pickaxe",
            new PickaxeItem(ModToolMaterials.RELIC, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolMaterials.RELIC, 1, -2.8f))));
    public static final Item RELIC_AXE = registerItem("relic_axe",
            new AxeItem(ModToolMaterials.RELIC, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolMaterials.RELIC, 1, -3.2f))));
//public static final Item SCHRODINGERS_BREAKFAST = registerItem("schrodingers_breakfast", new Item(new Item.Settings().food(ModFoodComponents.SCHRODINGERS_BREAKFAST).maxCount(1)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(Schrodingerschest.MOD_ID, name), item);

    }


    public static void registerModItems() {
        Schrodingerschest.LOGGER.info("Registering Mod Items for " + Schrodingerschest.MOD_ID);


        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(RELIC);
            entries.add(SCHRODINGERS_STAFF);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
            entries.add(RELIC_SWORD);
            entries.add(RELIC_PICKAXE);
            entries.add(RELIC_AXE);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> {
            entries.add(SCHRODINGERS_BREAKFAST);
        });

    }
}
