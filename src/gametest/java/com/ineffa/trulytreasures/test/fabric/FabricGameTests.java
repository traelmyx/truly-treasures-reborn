package com.ineffa.trulytreasures.test.fabric;

//? if fabric {
/*import com.ineffa.trulytreasures.test.TreasureTests;
import net.minecraft.gametest.framework.GameTestHelper;
//? if >=1.21.5 {
import net.fabricmc.fabric.api.gametest.v1.GameTest;
//?} else {
/^import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.gametest.framework.GameTest;
^///?}

//? if >=1.21.5 {
public class FabricGameTests {
	private static final String EMPTY = "trulytreasures_gametest:empty";

	@GameTest(structure = EMPTY, maxTicks = 100)
	public void wandererListingSellsTreasureBooks(GameTestHelper helper) {
		TreasureTests.wandererListingSellsTreasureBooks(helper);
	}

	@GameTest(structure = EMPTY, maxTicks = 100)
	public void wanderingTradersStockThem(GameTestHelper helper) {
		TreasureTests.wanderingTradersStockThem(helper);
	}

	@GameTest(structure = EMPTY, maxTicks = 100)
	public void villagersNoLongerSellTreasure(GameTestHelper helper) {
		TreasureTests.villagersNoLongerSellTreasure(helper);
	}

	@GameTest(structure = EMPTY, maxTicks = 100)
	public void keepListLetsVillagersSellIt(GameTestHelper helper) {
		TreasureTests.keepListLetsVillagersSellIt(helper);
	}
}
//?} else {
/^public class FabricGameTests implements FabricGameTest {
	private static final String EMPTY = "trulytreasures_gametest:empty";

	@GameTest(template = EMPTY, timeoutTicks = 100)
	public void wandererListingSellsTreasureBooks(GameTestHelper helper) {
		TreasureTests.wandererListingSellsTreasureBooks(helper);
	}

	@GameTest(template = EMPTY, timeoutTicks = 100)
	public void wanderingTradersStockThem(GameTestHelper helper) {
		TreasureTests.wanderingTradersStockThem(helper);
	}

	@GameTest(template = EMPTY, timeoutTicks = 100)
	public void villagersNoLongerSellTreasure(GameTestHelper helper) {
		TreasureTests.villagersNoLongerSellTreasure(helper);
	}

	@GameTest(template = EMPTY, timeoutTicks = 100)
	public void keepListLetsVillagersSellIt(GameTestHelper helper) {
		TreasureTests.keepListLetsVillagersSellIt(helper);
	}
}
^///?}
*///?}
