package com.duskhorror.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.world.phys.Vec3;

/** Perpetually dark, close, sickly-fogged sky for the Hollow. */
public class TheHollowEffects extends DimensionSpecialEffects {

    public TheHollowEffects() {
        super(Float.NaN, false, DimensionSpecialEffects.SkyType.NONE, false, false);
    }

    @Override
    public Vec3 getBrightnessDependentFogColor(Vec3 color, float brightness) {
        return new Vec3(0.03D, 0.01D, 0.04D);
    }

    @Override
    public boolean isFoggyAt(int x, int y) {
        return true;
    }
}
