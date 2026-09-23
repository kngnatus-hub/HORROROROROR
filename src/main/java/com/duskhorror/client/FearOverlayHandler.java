package com.duskhorror.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;

/** Draws the fear bar, a darkening vignette, and the jumpscare flash. */
public class FearOverlayHandler {

    public static void render(RenderGuiOverlayEvent.Post event) {
        if (!event.getOverlay().id().equals(VanillaGuiOverlay.HOTBAR.id())) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.options.hideGui) return;

        GuiGraphics gfx = event.getGuiGraphics();
        float fear = FearManager.getFear();
        int w = mc.getWindow().getGuiScaledWidth();
        int h = mc.getWindow().getGuiScaledHeight();

        if (fear > 30f) {
            int alpha = (int) Mth.clamp((fear - 30f) / 70f * 160f, 0, 160);
            gfx.fill(0, 0, w, h, alpha << 24);
        }

        int barWidth = 80, barHeight = 4;
        int x = w / 2 - barWidth / 2;
        int y = h - 40;
        gfx.fill(x, y, x + barWidth, y + barHeight, 0x88000000);
        int filled = (int) (barWidth * (fear / 100f));
        int color = fear > 70 ? 0xFFAA0000 : (fear > 40 ? 0xFFAA6600 : 0xFF556655);
        gfx.fill(x, y, x + filled, y + barHeight, color);

        if (JumpscareHandler.getFlashTicks() > 0) {
            gfx.fill(0, 0, w, h, 0xFFFFFFFF);
        }
    }
}
