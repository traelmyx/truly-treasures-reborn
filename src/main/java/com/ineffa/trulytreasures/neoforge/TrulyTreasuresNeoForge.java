package com.ineffa.trulytreasures.neoforge;

//? if neoforge {
import com.ineffa.trulytreasures.TrulyTreasures;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.village.WandererTradesEvent;

@Mod(TrulyTreasures.MOD_ID)
public class TrulyTreasuresNeoForge {
    public TrulyTreasuresNeoForge() {
        TrulyTreasures.loadConfig(FMLPaths.CONFIGDIR.get());
        NeoForge.EVENT_BUS.addListener((WandererTradesEvent event) -> {
            for (int i = 0; i < TrulyTreasures.config.wanderingTraderSettings.tradeWeight; i++) {
                event.getRareTrades().add(new TrulyTreasures.TreasureBookListing());
            }
        });
    }
}
//?}
