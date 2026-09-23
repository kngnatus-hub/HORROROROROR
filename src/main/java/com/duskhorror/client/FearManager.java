package com.duskhorror.client;

import com.duskhorror.entity.StalkerEntity;
import com.duskhorror.registry.ModDimensions;
import com.duskhorror.registry.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.Mth;

/**
 * Client-side-only "fear" meter, 0-100. Rises in darkness, in the Hollow, and
 * near a Stalker; decays elsewhere. Drives the HUD bar, screen vignette,
 * and how often ambient scares/jumpscares fire. Purely cosmetic/atmospheric -
 * not synced, not saved.
 */
public class FearManager {

    private static float fear = 0f;

    public static float getFear() {
        return fear;
    }

    public static void tick(Minecraft mc) {
        LocalPlayer player = mc.player;
        ClientLevel level = mc.level;
        if (player == null || level == null) return;

        float target = 0f;
        int light = level.getMaxLocalRawBrightness(player.blockPosition());
        if (light <= 3) target += 25f;
        if (!level.canSeeSky(player.blockPosition())) target += 5f;
        if (level.dimension() == ModDimensions.THE_HOLLOW) target += 40f;
        if (!level.isDay()) target += 10f;

        double nearestStalker = Double.MAX_VALUE;
        for (StalkerEntity stalker : level.getEntitiesOfClass(StalkerEntity.class, player.getBoundingBox().inflate(40))) {
            nearestStalker = Math.min(nearestStalker, stalker.distanceTo(player));
        }
        if (nearestStalker < 40) {
            target += (float) Mth.clamp(60 - nearestStalker, 0, 60);
        }

        boolean warded = player.getMainHandItem().is(ModItems.WARDING_LANTERN.get())
                || player.getOffhandItem().is(ModItems.WARDING_LANTERN.get());
        if (warded) target *= 0.4f;

        target = Mth.clamp(target, 0f, 100f);
        fear += (target - fear) * 0.02f;
        fear = Mth.clamp(fear, 0f, 100f);
    }
}
