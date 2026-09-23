package com.duskhorror.client;

import com.duskhorror.registry.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvent;

/** Random whispers/screams/heartbeats that fire more often as fear rises. */
public class AmbientHorrorHandler {

    private static int cooldown = 0;

    public static void tick(Minecraft mc) {
        if (mc.player == null) return;
        if (cooldown > 0) {
            cooldown--;
            return;
        }
        float fear = FearManager.getFear();
        float chance = 0.0005f + (fear / 100f) * 0.004f;
        if (mc.player.getRandom().nextFloat() < chance) {
            SoundEvent[] pool = {
                    ModSounds.WHISPER_1.get(), ModSounds.WHISPER_2.get(), ModSounds.DISTANT_SCREAM.get()
            };
            SoundEvent sound = pool[mc.player.getRandom().nextInt(pool.length)];
            mc.player.playSound(sound, 0.6f + mc.player.getRandom().nextFloat() * 0.3f,
                    0.8f + mc.player.getRandom().nextFloat() * 0.4f);
            cooldown = 200 + mc.player.getRandom().nextInt(400);
        } else if (fear > 70f && mc.player.getRandom().nextFloat() < 0.01f) {
            mc.player.playSound(ModSounds.HEARTBEAT.get(), 0.5f, 1.0f);
            cooldown = 40;
        }
    }
}
