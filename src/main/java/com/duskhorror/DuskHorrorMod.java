package com.duskhorror;

import com.duskhorror.registry.ModEntities;
import com.duskhorror.registry.ModItems;
import com.duskhorror.registry.ModSounds;
import com.duskhorror.entity.StalkerEntity;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * Main mod entry point.
 * Mod id: duskhorror | Target: Forge, Minecraft 1.20.1
 */
@Mod(DuskHorrorMod.MODID)
public class DuskHorrorMod {

    public static final String MODID = "duskhorror";

    public DuskHorrorMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModEntities.ENTITY_TYPES.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModSounds.SOUND_EVENTS.register(modEventBus);

        modEventBus.addListener(this::onAttributeCreate);
        modEventBus.addListener(this::onSpawnPlacementRegister);
        modEventBus.addListener(this::buildCreativeTabs);
    }

    private void onAttributeCreate(EntityAttributeCreationEvent event) {
        event.put(ModEntities.STALKER.get(), StalkerEntity.createAttributes().build());
    }

    private void onSpawnPlacementRegister(SpawnPlacementRegisterEvent event) {
        event.register(
                ModEntities.STALKER.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                StalkerEntity::checkStalkerSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND
        );
    }

    private void buildCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.CORRUPTED_SHARD);
        }
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ModItems.WHISPERING_AMULET);
            event.accept(ModItems.WARDING_LANTERN);
        }
    }
}
