package com.duskhorror.registry;

import com.duskhorror.DuskHorrorMod;
import com.duskhorror.entity.StalkerEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, DuskHorrorMod.MODID);

    public static final RegistryObject<EntityType<StalkerEntity>> STALKER = ENTITY_TYPES.register("stalker",
            () -> EntityType.Builder.of(StalkerEntity::new, MobCategory.MONSTER)
                    .sized(0.6f, 1.95f)
                    .clientTrackingRange(10)
                    .build("duskhorror:stalker"));
}
