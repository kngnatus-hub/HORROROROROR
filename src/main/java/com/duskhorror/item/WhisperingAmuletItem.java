package com.duskhorror.item;

import com.duskhorror.registry.ModDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * Teleports the holder between the Overworld and the Hollow. No portal frame
 * required - right click to travel, with a short cooldown between uses.
 */
public class WhisperingAmuletItem extends Item {

    public WhisperingAmuletItem(Properties props) {
        super(props);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer && !player.getCooldowns().isOnCooldown(this)) {
            MinecraftServer server = serverPlayer.getServer();
            if (server != null) {
                boolean inHollow = serverPlayer.serverLevel().dimension() == ModDimensions.THE_HOLLOW;
                ServerLevel targetLevel = server.getLevel(inHollow ? Level.OVERWORLD : ModDimensions.THE_HOLLOW);
                if (targetLevel != null) {
                    BlockPos spawn = targetLevel.getSharedSpawnPos();
                    serverPlayer.teleportTo(targetLevel, spawn.getX() + 0.5D, spawn.getY() + 1.0D, spawn.getZ() + 0.5D,
                            serverPlayer.getYRot(), serverPlayer.getXRot());
                    targetLevel.playSound(null, spawn, SoundEvents.PORTAL_TRAVEL, SoundSource.PLAYERS, 1.0F, 1.0F);
                    player.getCooldowns().addCooldown(this, 200);
                }
            }
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }
}
