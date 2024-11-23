package de.qwatrum.schrodingerschest.item;

import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;

public class ModFoodComponents {
    public static final FoodComponent SCHRODINGERS_BREAKFAST = new FoodComponent.Builder().nutrition(6).saturationModifier(0.25f)
            .usingConvertsTo(() -> ModItems.PLACE_HOLDER)
            .alwaysEdible().build();

}
