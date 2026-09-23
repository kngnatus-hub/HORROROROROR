package com.duskhorror.client;

import com.duskhorror.DuskHorrorMod;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/** Ticks the fear/ambient/jumpscare systems and draws the overlay. Client-only. */
@Mod.EventBusSubscriber(modid = DuskHorrorMod.MODID, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null || mc.isPaused()) return;
        FearManager.tick(mc);
        AmbientHorrorHandler.tick(mc);
        JumpscareHandler.tick(mc);
    }

    @SubscribeEvent
    public static void onRenderGuiOverlay(RenderGuiOverlayEvent.Post event) {
        FearOverlayHandler.render(event);
    }
}
