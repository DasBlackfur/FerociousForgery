package me.blackfur.ferociousforgery.client;

import me.blackfur.ferociousforgery.FerociousForgery;
import me.blackfur.ferociousforgery.screens.FormingAnvilScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class FerociousForgeryClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(FerociousForgery.FORMING_ANVIL_SCREEN_HANDLER, FormingAnvilScreen::new);
    }
}
