package com.duskhorror.item;

import net.minecraft.world.item.Item;

/**
 * Holding this in either hand keeps the Stalker from ever targeting you
 * (see StalkerEntity's target selector) and reduces fear buildup.
 */
public class WardingLanternItem extends Item {
    public WardingLanternItem(Properties props) {
        super(props);
    }
}
