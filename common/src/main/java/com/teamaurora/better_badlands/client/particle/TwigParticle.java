package com.teamaurora.better_badlands.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class TwigParticle extends TextureSheetParticle {

    public TwigParticle(ClientLevel clientLevel, double x, double y, double z) {
        super(clientLevel, x, y, z);
        this.xd *= 0.8F;
        this.yd *= 0.8F;
        this.zd *= 0.8F;
        this.yd = this.random.nextFloat() * 0.15F + 0.05F;
        this.quadSize *= this.random.nextFloat() * 2.0F + 0.2F;
        this.lifetime = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public int getLightColor(float partialTick) {
        int i = super.getLightColor(partialTick);
        int k = i >> 16 & 255;
        return 240 | k << 16;
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        float f = (float)this.age / (float)this.lifetime;

        if (0.75 < f && (random.nextInt(15) ==0 )) {
            this.level.addParticle(ParticleTypes.SMOKE, this.x, this.y, this.z, this.xd, this.yd, this.zd);
        }

        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.yd -= 0.03D;
            this.move(this.xd, this.yd, this.zd);
            this.xd *= 0.999F;
            this.yd *= 0.999F;
            this.zd *= 0.999F;
            if (this.onGround) {
                this.xd *= 0.7F;
                this.zd *= 0.7F;
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType particleOptions, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            TwigParticle twigparticle = new TwigParticle(clientLevel, d, e, f);
            twigparticle.pickSprite(this.spriteSet);

            return twigparticle;
        }
    }
}
