package nl.devpieter.falsereality;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderPhase;

public class Target extends RenderPhase {

    public Target(String string, Runnable runnable, Runnable runnable2) {
        super(string, runnable, runnable2);
    }

    public static final nl.devpieter.falsereality.Target WEATHER_TARGET = new nl.devpieter.falsereality.Target("weather_target", () -> {
        if (MinecraftClient.isFabulousGraphicsOrBetter()) {
            MinecraftClient.getInstance().worldRenderer.getWeatherFramebuffer().beginWrite(false);
        }
    }, () -> {
        if (MinecraftClient.isFabulousGraphicsOrBetter()) {
            MinecraftClient.getInstance().getFramebuffer().beginWrite(false);
        }
    });
}
