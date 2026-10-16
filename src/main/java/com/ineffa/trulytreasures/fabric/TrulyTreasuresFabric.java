package com.ineffa.trulytreasures.fabric;

//? if fabric {
/*import com.ineffa.trulytreasures.TrulyTreasures;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.loader.api.FabricLoader;

public class TrulyTreasuresFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        TrulyTreasures.loadConfig(FabricLoader.getInstance().getConfigDir());
        TradeOfferHelper.registerWanderingTraderOffers(2, factories -> {
            for (int i = 0; i < TrulyTreasures.config.wanderingTraderSettings.tradeWeight; i++) {
                factories.add(new TrulyTreasures.TreasureBookListing());
            }
        });
    }
}
*///?}
