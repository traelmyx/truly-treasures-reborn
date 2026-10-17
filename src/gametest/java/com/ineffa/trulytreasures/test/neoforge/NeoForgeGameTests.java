package com.ineffa.trulytreasures.test.neoforge;

//? if neoforge {
import com.ineffa.trulytreasures.test.ClientSmoke;
import com.ineffa.trulytreasures.test.TreasureTests;
import net.minecraft.gametest.framework.GameTestHelper;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;
//? if >=1.21.5 {
/*import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.gametest.framework.TestEnvironmentDefinition;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Consumer;
*///?} else {
import net.minecraft.gametest.framework.GameTest;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
//?}

//? if <1.21.5 {
@GameTestHolder(NeoForgeGameTests.MOD_ID)
@PrefixGameTestTemplate(false)
//?}
@Mod(NeoForgeGameTests.MOD_ID)
public class NeoForgeGameTests {
	public static final String MOD_ID = "trulytreasures_gametest";

	public NeoForgeGameTests(IEventBus modBus, Dist dist) {
		//? if >=1.21.5 {
		/*FUNCTIONS.register(modBus);
		modBus.addListener(NeoForgeGameTests::registerTests);
		*///?}
		if (dist.isClient() && ClientSmoke.start()) {
			NeoForge.EVENT_BUS.addListener((ClientTickEvent.Post e) -> ClientSmoke.tick(net.minecraft.client.Minecraft.getInstance()));
		}
	}

	//? if >=1.21.5 {
	/*private static final DeferredRegister<Consumer<GameTestHelper>> FUNCTIONS = DeferredRegister.create(Registries.TEST_FUNCTION, MOD_ID);
	private static final Identifier EMPTY = Identifier.fromNamespaceAndPath(MOD_ID, "empty");

	static {
		FUNCTIONS.register("wanderer_listing_sells_treasure_books", () -> TreasureTests::wandererListingSellsTreasureBooks);
		FUNCTIONS.register("wandering_traders_stock_them", () -> TreasureTests::wanderingTradersStockThem);
		FUNCTIONS.register("villagers_no_longer_sell_treasure", () -> TreasureTests::villagersNoLongerSellTreasure);
		FUNCTIONS.register("keep_list_lets_villagers_sell_it", () -> TreasureTests::keepListLetsVillagersSellIt);
	}

	private static void registerTests(RegisterGameTestsEvent event) {
		//? if >=26 {
		Holder<TestEnvironmentDefinition<?>> env = event.registerEnvironment(Identifier.fromNamespaceAndPath(MOD_ID, "default"));
		//?} else
		/^Holder<TestEnvironmentDefinition> env = event.registerEnvironment(Identifier.fromNamespaceAndPath(MOD_ID, "default"));^/
		register(event, env, "wanderer_listing_sells_treasure_books", 100, 1, 1);
		register(event, env, "wandering_traders_stock_them", 100, 1, 1);
		register(event, env, "villagers_no_longer_sell_treasure", 100, 1, 1);
		register(event, env, "keep_list_lets_villagers_sell_it", 100, 1, 1);
	}

	//? if >=26 {
	private static void register(RegisterGameTestsEvent event, Holder<TestEnvironmentDefinition<?>> env, String name, int maxTicks, int attempts, int successes) {
	//?} else
	/^private static void register(RegisterGameTestsEvent event, Holder<TestEnvironmentDefinition> env, String name, int maxTicks, int attempts, int successes) {^/
		Identifier id = Identifier.fromNamespaceAndPath(MOD_ID, name);
		event.registerTest(id, new FunctionGameTestInstance(ResourceKey.create(Registries.TEST_FUNCTION, id),
			//? if >=26.3 {
			new TestData<>(env, net.minecraft.world.level.Level.OVERWORLD, EMPTY, maxTicks, 0, true,
				net.minecraft.world.level.block.Rotation.NONE, false, attempts, successes, false, 0)));
			//?} else if >=26 {
			/^new TestData<>(env, EMPTY, maxTicks, 0, true,
				net.minecraft.world.level.block.Rotation.NONE, false, attempts, successes, false, 0)));
			^///?} else {
			/^new TestData<>(env, EMPTY, maxTicks, 0, true,
				net.minecraft.world.level.block.Rotation.NONE, false, attempts, successes, false)));
			^///?}
	}
	*///?} else {
	@GameTest(template = "empty", timeoutTicks = 100)
	public static void wandererListingSellsTreasureBooks(GameTestHelper helper) {
		TreasureTests.wandererListingSellsTreasureBooks(helper);
	}

	@GameTest(template = "empty", timeoutTicks = 100)
	public static void wanderingTradersStockThem(GameTestHelper helper) {
		TreasureTests.wanderingTradersStockThem(helper);
	}

	@GameTest(template = "empty", timeoutTicks = 100)
	public static void villagersNoLongerSellTreasure(GameTestHelper helper) {
		TreasureTests.villagersNoLongerSellTreasure(helper);
	}

	@GameTest(template = "empty", timeoutTicks = 100)
	public static void keepListLetsVillagersSellIt(GameTestHelper helper) {
		TreasureTests.keepListLetsVillagersSellIt(helper);
	}
	//?}
}
//?}
