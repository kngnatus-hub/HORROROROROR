package com.duskhorror.client;

import com.duskhorror.DuskHorrorMod;
import com.duskhorror.registry.ModEntities;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/** One-time client bootstrapping: model layers, entity renderer, custom dimension effects. */
@Mod.EventBusSubscriber(modid = DuskHorrorMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.STALKER, HumanoidModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.STALKER.get(), StalkerRenderer::new);
    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> DimensionSpecialEffects.EFFECTS.put(
                new ResourceLocation(DuskHorrorMod.MODID, "the_hollow"), new TheHollowEffects()));
    }
}
