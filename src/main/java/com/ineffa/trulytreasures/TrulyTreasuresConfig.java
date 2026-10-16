package com.ineffa.trulytreasures;

import java.util.ArrayList;
import java.util.List;

public class TrulyTreasuresConfig {
    public WanderingTraderSettings wanderingTraderSettings = new WanderingTraderSettings();
    public VillagerSettings villagerSettings = new VillagerSettings();

    public static class WanderingTraderSettings {
        /** Price base: one-level enchantments (Mending) cost twice this, the others this times their level, capped at 64. */
        public int basePrice = 16;
        /** How many times each treasure book offer can be bought. */
        public int maxTrades = 2;
        /** How many entries treasure books get in the wandering trader's rare-trade pool. 0 turns them off. */
        public int tradeWeight = 3;
        public WandererTreasureEnchantmentFilter treasureEnchantmentFilter = WandererTreasureEnchantmentFilter.DEFAULT;
        /** Full enchantment ids that wandering traders never sell, e.g. "minecraft:frost_walker". */
        public List<String> enchantmentBlacklist = new ArrayList<>();
    }

    public static class VillagerSettings {
        public VillagerEnchantmentRemovalMode enchantmentRemovalMode = VillagerEnchantmentRemovalMode.TREASURE;
        /** Full enchantment ids that villagers keep selling whatever the removal mode says. */
        public List<String> enchantmentsToKeep = new ArrayList<>();
    }

    void validate() {
        if (wanderingTraderSettings == null) wanderingTraderSettings = new WanderingTraderSettings();
        if (villagerSettings == null) villagerSettings = new VillagerSettings();
        var w = wanderingTraderSettings;
        w.basePrice = Math.max(1, Math.min(64, w.basePrice));
        w.maxTrades = Math.max(1, Math.min(100, w.maxTrades));
        w.tradeWeight = Math.max(0, Math.min(100, w.tradeWeight));
        if (w.treasureEnchantmentFilter == null) w.treasureEnchantmentFilter = WandererTreasureEnchantmentFilter.DEFAULT;
        if (w.enchantmentBlacklist == null) w.enchantmentBlacklist = new ArrayList<>();
        if (villagerSettings.enchantmentRemovalMode == null) villagerSettings.enchantmentRemovalMode = VillagerEnchantmentRemovalMode.TREASURE;
        if (villagerSettings.enchantmentsToKeep == null) villagerSettings.enchantmentsToKeep = new ArrayList<>();
    }
}
