package github.catchaos8.levelup.stats;

import net.minecraft.nbt.CompoundTag;

import java.util.Arrays;

public class PlayerStats {

    private final int min_stat = 0;

    private int[] stats = {
            0, //Con
            0, //Dex
            0, //Str
            0, //Vit
            0, //End
            0, //Freepoints
            0, //Class XP
            0  //Class Level
    };

    private int[] limitedStats = {
            0,
            0,
            0,
            0,
            0
    };

    public int[] getStatArr() {
        return this.stats;
    }

    public int getStat(int type) {
        return stats[type];
    }

    public void addStat(int statType, int add) {
        this.stats[statType] = stats[statType] + add;
    }

    public void subStat(int statType, int sub) {
        this.stats[statType] = Math.max(stats[statType] - sub, min_stat);
    }

    public void setStat(int statType, int amount) {
        this.stats[statType] = Math.max(amount, min_stat);
    }

    public int[] getLimitedStatsArr() {
        return this.stats;
    }


    public int getLimitedStats(int type) {
        return limitedStats[type];
    }

    public void addLimitedStats(int statType, int add) {
        this.limitedStats[statType] = Math.min(limitedStats[statType] + add, stats[statType]);
    }
    public void subLimitedStats(int statType, int sub) {
        this.limitedStats[statType] = Math.max(limitedStats[statType] - sub, stats[statType]);
    }
    public void setLimitedStats(int statType, int amount) {
        this.limitedStats[statType] = Math.max(amount, min_stat);
    }


    public void copyFrom(PlayerStats source){
        this.stats = Arrays.copyOf(source.stats, source.stats.length);
    }


    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("constitution", stats[0]);
        nbt.putInt("dexterity", stats[1]);
        nbt.putInt("strength", stats[2]);
        nbt.putInt("vitality", stats[3]);
        nbt.putInt("endurance", stats[4]);

        nbt.putInt("freepoints", stats[5]);
        nbt.putInt("classxp", stats[6]);
        nbt.putInt("classLevel", stats[7]);

        nbt.putIntArray("limited_stats", limitedStats);
    }


    public void loadNBTData(CompoundTag nbt) {
        stats[0] = nbt.getInt("constitution");
        stats[1] = nbt.getInt("dexterity");
        stats[2] = nbt.getInt("strength");
        stats[3] = nbt.getInt("vitality");
        stats[4] = nbt.getInt("endurance");

        stats[5] = nbt.getInt("freepoints");
        stats[6] = nbt.getInt("classxp");
        stats[7] = nbt.getInt("classLevel");

        limitedStats = nbt.getIntArray("limited_stats");
    }

}
