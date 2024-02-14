package me.felix.skywarsffa;

import lombok.Getter;
import me.felix.gamemodule.module.Module;
import me.felix.skywarsffa.listener.player.FoodLevelChangeListener;
import me.felix.skywarsffa.listener.player.PlayerJoinListener;
import me.felix.skywarsffa.listener.player.PlayerQuitListener;
import me.felix.skywarsffa.listener.player.SimulatePlayerJoinListener;
import me.felix.skywarsffa.listener.world.BlockBreakListener;
import me.felix.skywarsffa.listener.world.WeatherChangeListener;

public class SkyWarsFFA extends Module {

    @Getter
    private static SkyWarsFFA instance;

    @Getter
    private final String prefix = "<dark_gray>┃</dark_gray> <gradient:#ff1749:#ff0000>SkyWarsFFA</gradient> <dark_gray>» <white>";

    @Override
    public void enableModule() {
        instance = this;

        setup();
    }

    private void setup() {
        registerListener(
                new PlayerJoinListener(),
                new BlockBreakListener(),
                new PlayerQuitListener(),
                new WeatherChangeListener(),
                new FoodLevelChangeListener(),
                new SimulatePlayerJoinListener()
        );
    }

    @Override
    public void disableModule() {
        super.disableModule();
    }

}