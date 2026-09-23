package com.duskhorror.registry;

import com.duskhorror.DuskHorrorMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, DuskHorrorMod.MODID);

    public static final RegistryObject<SoundEvent> STALKER_AMBIENT = registerSound("stalker_ambient");
    public static final RegistryObject<SoundEvent> STALKER_SCARE = registerSound("stalker_scare");
    public static final RegistryObject<SoundEvent> WHISPER_1 = registerSound("whisper_1");
    public static final RegistryObject<SoundEvent> WHISPER_2 = registerSound("whisper_2");
    public static final RegistryObject<SoundEvent> DISTANT_SCREAM = registerSound("distant_scream");
    public static final RegistryObject<SoundEvent> HEARTBEAT = registerSound("heartbeat");

    private static RegistryObject<SoundEvent> registerSound(String name) {
        ResourceLocation id = new ResourceLocation(DuskHorrorMod.MODID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }
}
