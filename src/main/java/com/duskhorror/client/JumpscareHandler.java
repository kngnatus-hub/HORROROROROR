package com.duskhorror.client;

import com.duskhorror.registry.ModSounds;
import net.minecraft.client.Minecraft;

/** Rare, fear-scaled jump scares: a stinger sound plus a brief white screen flash. */
public class JumpscareHandler {

    private static int flashTicks = 0;
    private static int cooldown = 600;

    public static void tick(Minecraft mc) {
        if (mc.player == null) return;
        if (cooldown > 0) cooldown--;
        if (flashTicks > 0) flashTicks--;

        float fear = FearManager.getFear();
        if (cooldown == 0 && fear > 50f) {
            float chance = (fear - 50f) / 100f * 0.01f;
            if (mc.player.getRandom().nextFloat() < chance) {
                flashTicks = 6;
                mc.player.playSound(ModSounds.STALKER_SCARE.get(), 1.0f,
                        0.9f + mc.player.getRandom().nextFloat() * 0.2f);
                cooldown = 1200 + mc.player.getRandom().nextInt(1200);
            }
        }
    }

    public static int getFlashTicks() {
        return flashTicks;
    }
}
