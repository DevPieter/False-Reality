package nl.devpieter.falsereality.Mixins;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderEffect;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.world.Heightmap;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {

    @Shadow
    protected abstract void renderWeather(LightmapTextureManager manager, float f, double d, double e, double g);

    @Shadow
    private @Nullable ShaderEffect transparencyShader;

    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    private ClientWorld world;

    @Shadow
    private int ticks;

    @Shadow
    @Final
    private float[] field_20794;

    @Shadow
    @Final
    private float[] field_20795;

    @Shadow
    @Final
    private static Identifier RAIN;

    @Shadow
    @Final
    private static Identifier SNOW;

    @Inject(at = @At("TAIL"), method = "render")
    public void onRender(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, CallbackInfo ci) {
//        if (!FalseReality.TEST_3) return;
//
//        Profiler profiler = this.world.getProfiler();
//        Vec3d vec3d = camera.getPos();
//        double cameraX = vec3d.getX();
//        double cameraY = vec3d.getY();
//        double cameraZ = vec3d.getZ();
//
//        if (this.transparencyShader != null) {
//            Target.WEATHER_TARGET.startDrawing();
//            profiler.swap("weather");
//            this.testRain(lightmapTextureManager, tickDelta, cameraX, cameraY, cameraZ);
//            Target.WEATHER_TARGET.endDrawing();
//            this.transparencyShader.render(tickDelta);
//            this.client.getFramebuffer().beginWrite(false);
//        } else {
//            RenderSystem.depthMask(false);
//            profiler.swap("weather");
//            this.testRain(lightmapTextureManager, tickDelta, cameraX, cameraY, cameraZ);
//            RenderSystem.depthMask(true);
//        }
    }

    private void testRain(LightmapTextureManager manager, float tickDelta, double cameraX, double cameraY, double cameraZ) {
        float rainGradient = 1.0F;//this.client.world.getRainGradient(tickDelta);
        //if (rainGradient <= 0.0f) return;

        manager.enable();

        ClientWorld world = this.client.world;
        int cameraXRounded = MathHelper.floor(cameraX);
        int cameraYRounded = MathHelper.floor(cameraY);
        int cameraZRounded = MathHelper.floor(cameraZ);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        RenderSystem.disableCull();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();

        int graphicsSetting = MinecraftClient.isFancyGraphicsOrBetter() ? 10 : 5;
        RenderSystem.depthMask(MinecraftClient.isFabulousGraphicsOrBetter());

        int m = -1;// ?

        RenderSystem.setShader(GameRenderer::getParticleShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

        BlockPos.Mutable mutable = new BlockPos.Mutable();

        for (int z = cameraZRounded - graphicsSetting; z <= cameraZRounded + graphicsSetting; ++z) {
            for (int x = cameraXRounded - graphicsSetting; x <= cameraXRounded + graphicsSetting; ++x) {

                int y;
                int q = (z - cameraZRounded + 16) * 32 + x - cameraXRounded + 16;
                double r = (double) this.field_20794[q] * 0.5;
                double s = (double) this.field_20795[q] * 0.5;
                mutable.set(x, 0, z);

                //Biome biome = world.getBiome(mutable);
                //if (biome.getPrecipitation() == Biome.Precipitation.NONE) continue;

                int topPositionY = world.getTopPosition(Heightmap.Type.MOTION_BLOCKING, mutable).getY();
                int u = cameraYRounded - graphicsSetting;
                int v = cameraYRounded + graphicsSetting;

                if (u < topPositionY) u = topPositionY;
                if (v < topPositionY) v = topPositionY;
                if ((y = topPositionY) < cameraYRounded) y = cameraYRounded;

                if (u == v) continue;
                mutable.set(x, u, z);

                if (m != 0) {
                    m = 0;
                    RenderSystem.setShaderTexture(0, RAIN);
                    bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR_LIGHT);
                }

                double aa = (double) x + 0.5 - cameraX;
                double ab = (double) z + 0.5 - cameraZ;
                float ac = (float) Math.sqrt(aa * aa + ab * ab) / (float) graphicsSetting;
                mutable.set(x, y, z);

                Random seededRandom = new Random(x * x * 3121 + x * 45238971L ^ z * z * 418711 + z * 13761L);
                int tickRandom = this.ticks + x * x * 3121 + x * 45238971 + z * z * 418711 + z * 13761 & 0x1F;
                float randomTexture = -((float) tickRandom + tickDelta) / 32.0f * (3.0f + seededRandom.nextFloat());

                float alpha = ((1.0f - ac * ac) * 0.5f + 0.5f) * rainGradient;
                int light = WorldRenderer.getLightmapCoordinates(world, mutable);

                bufferBuilder.vertex((double) x - cameraX - r + 0.5, (double) v - cameraY, (double) z - cameraZ - s + 0.5).texture(0.0f, (float) u * 0.25f + randomTexture).color(1.0f, 1.0f, 1.0f, alpha).light(light).next();
                bufferBuilder.vertex((double) x - cameraX + r + 0.5, (double) v - cameraY, (double) z - cameraZ + s + 0.5).texture(1.0f, (float) u * 0.25f + randomTexture).color(1.0f, 1.0f, 1.0f, alpha).light(light).next();
                bufferBuilder.vertex((double) x - cameraX + r + 0.5, (double) u - cameraY, (double) z - cameraZ + s + 0.5).texture(1.0f, (float) v * 0.25f + randomTexture).color(1.0f, 1.0f, 1.0f, alpha).light(light).next();
                bufferBuilder.vertex((double) x - cameraX - r + 0.5, (double) u - cameraY, (double) z - cameraZ - s + 0.5).texture(0.0f, (float) v * 0.25f + randomTexture).color(1.0f, 1.0f, 1.0f, alpha).light(light).next();
            }
        }
        if (m >= 0) tessellator.draw();

        RenderSystem.enableCull();
        RenderSystem.disableBlend();
        manager.disable();
    }
}
