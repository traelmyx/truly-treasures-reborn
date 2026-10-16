package com.ineffa.trulytreasures.mixin;

import com.ineffa.trulytreasures.TrulyTreasures;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.npc.VillagerTrades;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mixin(VillagerTrades.EnchantBookForEmeralds.class)
public class EnchantBookForEmeraldsMixin {

    // Villagers pick a random enchantment from a tag; pick from the same tag minus the ones they should no longer sell.
    @Redirect(method = "getOffer", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/Registry;getRandomElementOf(Lnet/minecraft/tags/TagKey;Lnet/minecraft/util/RandomSource;)Ljava/util/Optional;"))
    private <T> Optional<Holder<T>> trulytreasures$filterVillagerEnchantments(Registry<T> registry, TagKey<T> tag, RandomSource random) {
        List<Holder<T>> allowed = new ArrayList<>();
        registry.getTagOrEmpty(tag).forEach(holder -> {
            @SuppressWarnings("unchecked")
            Holder<net.minecraft.world.item.enchantment.Enchantment> enchantment = (Holder<net.minecraft.world.item.enchantment.Enchantment>) (Holder<?>) holder;
            if (TrulyTreasures.isEnchantmentAllowedForVillagers(enchantment)) allowed.add(holder);
        });
        return allowed.isEmpty() ? Optional.empty() : Optional.of(allowed.get(random.nextInt(allowed.size())));
    }
}
