package com.ineffa.trulytreasures;

import net.minecraft.core.Holder;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.function.Predicate;

/** Which enchantments villagers stop offering in their book trades. */
public enum VillagerEnchantmentRemovalMode {
    TREASURE(e -> e.is(EnchantmentTags.TREASURE)),
    REGULAR(e -> !e.is(EnchantmentTags.TREASURE)),
    CURSES(e -> e.is(EnchantmentTags.CURSE)),
    POSITIVE(e -> !e.is(EnchantmentTags.CURSE)),
    NONE(e -> false);

    private final Predicate<Holder<Enchantment>> removal;

    VillagerEnchantmentRemovalMode(Predicate<Holder<Enchantment>> removal) {
        this.removal = removal;
    }

    public boolean shouldRemove(Holder<Enchantment> enchantment) {
        return removal.test(enchantment);
    }
}
