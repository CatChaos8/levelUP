package github.catchaos8.levelup.client.screen;

import net.minecraft.client.Minecraft;

public class ClientHooks {

    public static void openLevelUPGui() {
        Minecraft.getInstance().setScreen(new LevelUPScreen());
    }
    public static void openLevelUPLimitGUI() {
        Minecraft.getInstance().setScreen(new LevelUPLimitScreen());
    }
}
