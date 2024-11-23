package de.qwatrum.schrodingerschest;

import de.qwatrum.schrodingerschest.item.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;


public class Schrodingerschest implements ModInitializer {

    public static final String MOD_ID = "schrodingerschest";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    @Override
    public void onInitialize() {
        ModItems.registerModItems();

        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (player.getMainHandStack().getItem() == ModItems.RELIC_SWORD) {
                if (!world.isClient()) {
                    Hand activeHand = player.getActiveHand();
                    Random random = new Random();
                    int randomValue = random.nextInt(100);
                    ItemStack newSword;

                    if (randomValue < 30) { // 30%
                        newSword = new ItemStack(Items.WOODEN_SWORD);
                    } else if (randomValue < 60) { // 30%
                        newSword = new ItemStack(Items.GOLDEN_SWORD);
                    } else if (randomValue < 90) { // 30%
                        newSword = new ItemStack(Items.IRON_SWORD);
                    } else { // 10%
                        newSword = new ItemStack(Items.DIAMOND_SWORD);
                    }
                    playSound(player);
                    player.setStackInHand(activeHand, newSword);
                } else {
                    specialEffects(player);
                }

            }
            if (player.getMainHandStack().getItem() == ModItems.RELIC_AXE) {
                if (!world.isClient()) {
                    Hand activeHand = player.getActiveHand();
                    Random random = new Random();
                    int randomValue = random.nextInt(100);
                    ItemStack newSword;

                    if (randomValue < 30) { // 30%
                        newSword = new ItemStack(Items.WOODEN_AXE);
                    } else if (randomValue < 60) { // 30%
                        newSword = new ItemStack(Items.GOLDEN_AXE);
                    } else if (randomValue < 90) { // 30%
                        newSword = new ItemStack(Items.IRON_AXE);
                    } else { // 10%
                        newSword = new ItemStack(Items.DIAMOND_AXE);
                    }
                    playSound(player);
                    player.setStackInHand(activeHand, newSword);
                } else {
                    specialEffects(player);
                }

            }
            if (player.getMainHandStack().getItem() == ModItems.RELIC_PICKAXE) {
                if (!world.isClient()) {
                    Hand activeHand = player.getActiveHand();
                    Random random = new Random();
                    int randomValue = random.nextInt(100);
                    ItemStack newSword;

                    if (randomValue < 30) { // 30%
                        newSword = new ItemStack(Items.WOODEN_PICKAXE);
                    } else if (randomValue < 60) { // 30%
                        newSword = new ItemStack(Items.GOLDEN_PICKAXE);
                    } else if (randomValue < 90) { // 30%
                        newSword = new ItemStack(Items.IRON_PICKAXE);
                    } else { // 10%
                        newSword = new ItemStack(Items.DIAMOND_PICKAXE);
                    }
                    playSound(player);
                    player.setStackInHand(activeHand, newSword);
                } else {
                    specialEffects(player);
                }

            }

            return ActionResult.PASS;
        });


        UseItemCallback.EVENT.register((player, world, hand) -> {

            if (player.getStackInHand(hand).getItem() == ModItems.SCHRODINGERS_BREAKFAST && !world.isClient()) {
                player.getMainHandStack().decrement(1);
                world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS);
                applyRandomEffects(player);
                ItemStack itemStack = player.getStackInHand(hand);
                return TypedActionResult.success(itemStack);
            }
            ItemStack itemStack = player.getStackInHand(hand);
            return TypedActionResult.pass(itemStack);
        });

        UseBlockCallback.EVENT.register(((player, world, hand, hitResult) -> {
            BlockPos pos = hitResult.getBlockPos();
            Block block = world.getBlockState(pos).getBlock();
            // Notfalls mit trapped chest alles machen und immer random einf.
            if (block == Blocks.CHEST) {
                ItemStack itemHand = player.getStackInHand(hand);
                if (itemHand.getItem() == ModItems.SCHRODINGERS_STAFF) {

                    if (world.getBlockEntity(pos) instanceof ChestBlockEntity chestBlockEntity) {
                        if (!world.isClient()) {
                            newLoot((ServerWorld) world, chestBlockEntity, player);
                        } else {
                            chestEffects(world, chestBlockEntity, player);
                        }

                        return ActionResult.SUCCESS;
                    }
                }
            } else if (block == Blocks.TRAPPED_CHEST) {
                ItemStack itemHand = player.getStackInHand(hand);
                if (itemHand.getItem() == ModItems.SCHRODINGERS_STAFF) {

                    if (world.getBlockEntity(pos) instanceof ChestBlockEntity chestBlockEntity) {
                        if (!world.isClient()) {
                            newLoot((ServerWorld) world, chestBlockEntity, player);
                        } else {
                            chestEffects(world, chestBlockEntity, player);
                        }
                        return ActionResult.SUCCESS;
                    }
                }
            }

            return ActionResult.PASS;
        }));
    }

    private void newLoot(ServerWorld world, ChestBlockEntity chest, PlayerEntity player) {

        if (chest.getLootTable() != null) {
            chest.clear();
            chest.setLootTable(chest.getLootTable(), world.getRandom().nextLong());
            playSound(player);
            relic_spawn(world, player);

        }
    }

    private void relic_spawn(ServerWorld world, PlayerEntity player) {
        Random random = new Random();

        player.giveItemStack(ModItems.RELIC.getDefaultStack());

        int i = random.nextInt(2);

        // Whether the player gets damage
        if (i == 0) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.INSTANT_DAMAGE, 1, 0));
        }

    }

    public void specialEffects(PlayerEntity player) {
        World world = player.getWorld();
        if (world.isClient()) {

            int particleCount = 12;
            double radius = 0.1;

            for(int i=0;i<particleCount;i++) {
                double angle = 2* Math.PI * i / particleCount;
                double offsetX = radius*Math.cos(angle);
                double offsetZ = radius*Math.sin(angle);

                world.addParticle(ParticleTypes.DRAGON_BREATH, player.getX(), player.getY()+1,player.getZ(), offsetX, 0.0, offsetZ);
            }

        }

    }
    public void chestEffects(World world, ChestBlockEntity chest, PlayerEntity player) {

        if (chest.getLootTable() != null) {
            if (world.isClient()) {

                int particleCount = 12;
                double radius = 0.05;

                for(int i=0;i<particleCount;i++) {
                    double angle = 2* Math.PI * i / particleCount;
                    double offsetX = radius*Math.cos(angle);
                    double offsetZ = radius*Math.sin(angle);

                    world.addParticle(ParticleTypes.DRAGON_BREATH, player.getX(), player.getY()+1,player.getZ(), offsetX, 0.0, offsetZ);
                }

            }

        }
    }
    public void playSound(PlayerEntity player) {
        World world = player.getWorld();
        world.playSound(null, player.getBlockPos(), SoundEvents.BLOCK_AMETHYST_BLOCK_STEP, SoundCategory.BLOCKS, 1000, 1);

    }

    public static void applyRandomEffects(PlayerEntity player) {
        Random random = new Random();

        int i = random.nextInt(10);

        switch (i) {
            case 0 -> player.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 200));
            case 1 -> player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 400));
            case 2 -> player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200, 5));
            case 3 -> player.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 200, 4));
            case 4 -> player.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 200));
            case 5 -> player.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 200, 2));
            case 6 -> player.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200));
            case 7 -> player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 200, 1));
            case 8 -> player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 200, 4));
            case 9 -> player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 200, 4));
        }



    }
}
