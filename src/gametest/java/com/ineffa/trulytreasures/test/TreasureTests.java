package com.ineffa.trulytreasures.test;

import com.ineffa.trulytreasures.TrulyTreasures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.item.trading.MerchantOffer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class TreasureTests {
    private TreasureTests() {
    }

    private static Set<String> enchantmentsOn(ItemStack book) {
        Set<String> ids = new HashSet<>();
        ItemEnchantments stored = book.getOrDefault(DataComponents.STORED_ENCHANTMENTS, ItemEnchantments.EMPTY);
        for (Holder<Enchantment> h : stored.keySet()) ids.add(TrulyTreasures.id(h));
        return ids;
    }

    public static void wandererListingSellsTreasureBooks(GameTestHelper helper) {
        WanderingTrader trader = helper.spawnWithNoFreeWill(EntityType.WANDERING_TRADER, new BlockPos(1, 2, 1));
        var listing = new TrulyTreasures.TreasureBookListing();
        Set<String> seen = new HashSet<>();
        for (int i = 0; i < 200; i++) {
            MerchantOffer offer = listing.getOffer(trader, trader.getRandom());
            helper.assertTrue(offer != null && offer.getResult().is(Items.ENCHANTED_BOOK), "no book offered");
            helper.assertTrue(offer.getBaseCostA().is(Items.EMERALD) && offer.getBaseCostA().getCount() <= 64, "bad price " + offer.getBaseCostA());
            seen.addAll(enchantmentsOn(offer.getResult()));
        }
        helper.assertTrue(seen.equals(Set.of("minecraft:mending", "minecraft:frost_walker")), "wandering trader books: " + seen);
        trader.discard();
        helper.succeed();
    }

    public static void wanderingTradersStockThem(GameTestHelper helper) {
        List<WanderingTrader> traders = new ArrayList<>();
        boolean found = false;
        for (int i = 0; i < 60 && !found; i++) {
            WanderingTrader trader = helper.spawnWithNoFreeWill(EntityType.WANDERING_TRADER, new BlockPos(1, 2, 1));
            traders.add(trader);
            for (MerchantOffer offer : trader.getOffers()) {
                if (offer.getResult().is(Items.ENCHANTED_BOOK) && !enchantmentsOn(offer.getResult()).isEmpty()) found = true;
            }
        }
        traders.forEach(t -> t.discard());
        var rare = VillagerTrades.WANDERING_TRADER_TRADES.get(2);
        long ours = rare == null ? -1 : java.util.Arrays.stream(rare).filter(l -> l instanceof TrulyTreasures.TreasureBookListing).count();
        helper.assertTrue(found, "60 wandering traders and none sold a treasure book; rare pool " + (rare == null ? "null" : rare.length) + " entries, " + ours + " ours");
        helper.succeed();
    }

    private static Set<String> villagerBooks(GameTestHelper helper, int tries) {
        Villager villager = helper.spawnWithNoFreeWill(EntityType.VILLAGER, new BlockPos(1, 2, 1));
        var listing = new VillagerTrades.EnchantBookForEmeralds(1, EnchantmentTags.TRADEABLE);
        Set<String> seen = new HashSet<>();
        for (int i = 0; i < tries; i++) {
            MerchantOffer offer = listing.getOffer(villager, villager.getRandom());
            if (offer != null) seen.addAll(enchantmentsOn(offer.getResult()));
        }
        villager.discard();
        return seen;
    }

    public static void villagersNoLongerSellTreasure(GameTestHelper helper) {
        Set<String> seen = villagerBooks(helper, 600);
        helper.assertTrue(seen.size() > 10, "villagers sold too few kinds of books: " + seen);
        helper.assertFalse(seen.contains("minecraft:mending") || seen.contains("minecraft:frost_walker"), "villagers still sell treasure: " + seen);
        helper.succeed();
    }

    public static void keepListLetsVillagersSellIt(GameTestHelper helper) {
        List<String> before = TrulyTreasures.config.villagerSettings.enchantmentsToKeep;
        TrulyTreasures.config.villagerSettings.enchantmentsToKeep = List.of("minecraft:mending");
        try {
            Set<String> seen = villagerBooks(helper, 1500);
            helper.assertTrue(seen.contains("minecraft:mending"), "mending on the keep list but never offered: " + seen);
            helper.assertFalse(seen.contains("minecraft:frost_walker"), "frost walker offered: " + seen);
        } finally {
            TrulyTreasures.config.villagerSettings.enchantmentsToKeep = before;
        }
        helper.succeed();
    }
}
