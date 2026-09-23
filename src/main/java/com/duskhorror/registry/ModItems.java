package com.duskhorror.registry;

import com.duskhorror.DuskHorrorMod;
import com.duskhorror.item.WardingLanternItem;
import com.duskhorror.item.WhisperingAmuletItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, DuskHorrorMod.MODID);

    public static final RegistryObject<Item> CORRUPTED_SHARD = ITEMS.register("corrupted_shard",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> WHISPERING_AMULET = ITEMS.register("whispering_amulet",
            () -> new WhisperingAmuletItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> WARDING_LANTERN = ITEMS.register("warding_lantern",
            () -> new WardingLanternItem(new Item.Properties().stacksTo(1)));
}
