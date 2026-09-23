package com.duskhorror.client;

import com.duskhorror.DuskHorrorMod;
import com.duskhorror.entity.StalkerEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

public class StalkerRenderer extends HumanoidMobRenderer<StalkerEntity, HumanoidModel<StalkerEntity>> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(DuskHorrorMod.MODID, "textures/entity/stalker/stalker.png");

    public StalkerRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModModelLayers.STALKER)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(StalkerEntity entity) {
        return TEXTURE;
    }
}
