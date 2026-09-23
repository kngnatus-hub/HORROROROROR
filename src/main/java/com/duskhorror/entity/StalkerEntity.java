package com.duskhorror.entity;

import com.duskhorror.registry.ModItems;
import com.duskhorror.registry.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.Vec3;

/**
 * The Stalker: a horror mob that freezes in place while any nearby player is
 * looking directly at it (Weeping-Angel style), and closes in the moment
 * nobody is watching. Holding the Warding Lantern keeps it from targeting you.
 */
public class StalkerEntity extends Monster {

    public StalkerEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.xpReward = 15;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 40.0)
                .add(Attributes.MOVEMENT_SPEED, 0.30)
                .add(Attributes.ATTACK_DAMAGE, 9.0)
                .add(Attributes.FOLLOW_RANGE, 48.0)
                .add(Attributes.ARMOR, 4.0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 0.6D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 32.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(
                this, Player.class, 10, true, false,
                (target) -> target instanceof Player player && !isHeldByWard(player)));
    }

    public static boolean checkStalkerSpawnRules(EntityType<StalkerEntity> type, ServerLevelAccessor level,
                                                  MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return Monster.checkMonsterSpawnRules(type, level, spawnType, pos, random);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide && isWatchedByAnyPlayer(32.0D)) {
            // Freeze completely while observed - it only advances when no one is looking.
            this.getNavigation().stop();
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.0D, 1.0D, 0.0D));
        }
    }

    private boolean isWatchedByAnyPlayer(double range) {
        for (Player player : this.level().getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(range))) {
            if (isHeldByWard(player)) continue;
            Vec3 toEntity = this.position().subtract(player.getEyePosition()).normalize();
            Vec3 look = player.getLookAngle();
            if (toEntity.dot(look) > 0.85D) {
                return true;
            }
        }
        return false;
    }

    private static boolean isHeldByWard(Player player) {
        return player.getMainHandItem().is(ModItems.WARDING_LANTERN.get())
                || player.getOffhandItem().is(ModItems.WARDING_LANTERN.get());
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);
        int count = 1 + this.random.nextInt(3) + looting;
        this.spawnAtLocation(new ItemStack(ModItems.CORRUPTED_SHARD.get(), count));
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.STALKER_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ZOMBIE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ZOMBIE_DEATH;
    }
}
