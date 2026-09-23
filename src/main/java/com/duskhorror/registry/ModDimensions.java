package com.duskhorror.registry;

import com.duskhorror.DuskHorrorMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class ModDimensions {

    public static final ResourceKey<Level> THE_HOLLOW = ResourceKey.create(
            Registries.DIMENSION, new ResourceLocation(DuskHorrorMod.MODID, "the_hollow"));
}
