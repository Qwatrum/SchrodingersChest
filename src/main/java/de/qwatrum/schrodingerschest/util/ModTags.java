package de.qwatrum.schrodingerschest.util;

import de.qwatrum.schrodingerschest.Schrodingerschest;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> INCORRECT_FOR_RELIC_TOOL = createTag("incorrect_for_relic_tool");

        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of(Schrodingerschest.MOD_ID, name));
        }
    }
}
