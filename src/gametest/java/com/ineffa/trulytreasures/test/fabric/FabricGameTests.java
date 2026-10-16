package com.ineffa.trulytreasures.test.fabric;

//? if fabric {
/*import com.ineffa.trulytreasures.test.TreasureTests;
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;

public class FabricGameTests implements FabricGameTest {
    private static final String EMPTY = "trulytreasures_gametest:empty";

    @GameTest(template = EMPTY)
    public void wandererListingSellsTreasureBooks(GameTestHelper helper) {
        TreasureTests.wandererListingSellsTreasureBooks(helper);
    }

    @GameTest(template = EMPTY)
    public void wanderingTradersStockThem(GameTestHelper helper) {
        TreasureTests.wanderingTradersStockThem(helper);
    }

    @GameTest(template = EMPTY)
    public void villagersNoLongerSellTreasure(GameTestHelper helper) {
        TreasureTests.villagersNoLongerSellTreasure(helper);
    }

    @GameTest(template = EMPTY)
    public void keepListLetsVillagersSellIt(GameTestHelper helper) {
        TreasureTests.keepListLetsVillagersSellIt(helper);
    }
}
*///?}
