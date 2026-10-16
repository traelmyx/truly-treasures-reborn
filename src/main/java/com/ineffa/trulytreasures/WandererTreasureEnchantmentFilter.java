package com.ineffa.trulytreasures;

import net.minecraft.core.Holder;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.function.Predicate;

/** Which treasure enchantments wandering traders can sell. "Normally sold by villagers" means in the #minecraft:tradeable tag. */
public enum WandererTreasureEnchantmentFilter {
    DEFAULT(e -> e.is(EnchantmentTags.TRADEABLE) && !e.is(EnchantmentTags.CURSE)),
    VILLAGER(e -> e.is(EnchantmentTags.TRADEABLE)),
    POSITIVE(e -> !e.is(EnchantmentTags.CURSE)),
    ALL(e -> true),
    CURSES(e -> e.is(EnchantmentTags.CURSE)),
    NONE(e -> false);

    private final Predicate<Holder<Enchantment>> filter;

    WandererTreasureEnchantmentFilter(Predicate<Holder<Enchantment>> filter) {
        this.filter = filter;
    }

    public boolean test(Holder<Enchantment> enchantment) {
        return filter.test(enchantment);
    }
}
