package com.ineffa.trulytreasures.test.neoforge;

//? if neoforge {
import com.ineffa.trulytreasures.test.ClientSmoke;
import com.ineffa.trulytreasures.test.TreasureTests;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder(NeoForgeGameTests.MOD_ID)
@PrefixGameTestTemplate(false)
@Mod(NeoForgeGameTests.MOD_ID)
public class NeoForgeGameTests {
    public static final String MOD_ID = "trulytreasures_gametest";

    public NeoForgeGameTests(Dist dist) {
        if (dist.isClient() && ClientSmoke.start()) {
            NeoForge.EVENT_BUS.addListener((ClientTickEvent.Post e) -> ClientSmoke.tick(net.minecraft.client.Minecraft.getInstance()));
        }
    }

    @GameTest(template = "empty")
    public static void wandererListingSellsTreasureBooks(GameTestHelper helper) {
        TreasureTests.wandererListingSellsTreasureBooks(helper);
    }

    @GameTest(template = "empty")
    public static void wanderingTradersStockThem(GameTestHelper helper) {
        TreasureTests.wanderingTradersStockThem(helper);
    }

    @GameTest(template = "empty")
    public static void villagersNoLongerSellTreasure(GameTestHelper helper) {
        TreasureTests.villagersNoLongerSellTreasure(helper);
    }

    @GameTest(template = "empty")
    public static void keepListLetsVillagersSellIt(GameTestHelper helper) {
        TreasureTests.keepListLetsVillagersSellIt(helper);
    }
}
//?}
