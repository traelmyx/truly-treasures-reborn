package com.ineffa.trulytreasures;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class TrulyTreasures {
    public static final String MOD_ID = "trulytreasures";
    public static final Logger LOGGER = LoggerFactory.getLogger("TrulyTreasures");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static TrulyTreasuresConfig config = new TrulyTreasuresConfig();

    private TrulyTreasures() {
    }

    public static void loadConfig(Path configDir) {
        Path file = configDir.resolve(MOD_ID + ".json");
        try {
            if (Files.exists(file)) {
                TrulyTreasuresConfig read = GSON.fromJson(Files.readString(file), TrulyTreasuresConfig.class);
                if (read != null) {
                    read.validate();
                    config = read;
                }
            } else {
                Files.createDirectories(configDir);
                Files.writeString(file, GSON.toJson(config));
            }
        } catch (IOException | RuntimeException e) {
            LOGGER.error("Could not read {}, using defaults", file, e);
        }
    }

    public static String id(Holder<Enchantment> enchantment) {
        return enchantment.unwrapKey().map(key -> key.location().toString()).orElse("");
    }

    /** Treasure enchantments the wandering trader may sell, in registry order. */
    public static List<Holder<Enchantment>> wandererEnchantments(RegistryAccess registries) {
        List<Holder<Enchantment>> out = new ArrayList<>();
        registries.registryOrThrow(Registries.ENCHANTMENT).holders().forEach(holder -> {
            if (holder.is(EnchantmentTags.TREASURE) && config.wanderingTraderSettings.treasureEnchantmentFilter.test(holder)
                    && !config.wanderingTraderSettings.enchantmentBlacklist.contains(id(holder))) {
                out.add(holder);
            }
        });
        return out;
    }

    /** Whether villagers may still offer this enchantment in their book trades. */
    public static boolean isEnchantmentAllowedForVillagers(Holder<Enchantment> enchantment) {
        return !config.villagerSettings.enchantmentRemovalMode.shouldRemove(enchantment)
            || config.villagerSettings.enchantmentsToKeep.contains(id(enchantment));
    }

    /** One entry in the wandering trader's rare-trade pool: a random treasure enchantment book. */
    public static final class TreasureBookListing implements VillagerTrades.ItemListing {
        @Nullable
        @Override
        public MerchantOffer getOffer(Entity trader, RandomSource random) {
            List<Holder<Enchantment>> pool = wandererEnchantments(trader.level().registryAccess());
            if (pool.isEmpty()) {
                return null;
            }
            Holder<Enchantment> enchantment = pool.get(random.nextInt(pool.size()));
            int maxLevel = enchantment.value().getMaxLevel();
            int level = Mth.nextInt(random, enchantment.value().getMinLevel(), maxLevel);
            ItemStack book = EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, level));
            int basePrice = config.wanderingTraderSettings.basePrice;
            int price = maxLevel == 1 ? Math.min(basePrice * 2, 64) : Mth.clamp(basePrice * level, 1, 64);
            int xp = maxLevel == 1 ? 10 : 5 * level;
            return new MerchantOffer(new ItemCost(Items.EMERALD, Math.max(1, price)), book, config.wanderingTraderSettings.maxTrades, xp, 1.0F);
        }
    }
}
