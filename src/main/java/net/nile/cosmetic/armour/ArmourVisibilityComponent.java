package net.nile.cosmetic.armour;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.nbt.CompoundTag;

public class ArmourVisibilityComponent implements AutoSyncedComponent {
//TODO finish this

private final String helmet_key = "ncahelmetvisible";
private final String torso_key = "ncatorsovisible";
private final String leggings_key = "ncaleggingsvisible";
private final String boots_key = "ncabootsvisible";

public String prettyPrint()
{
    return String.format("Head: {0} Torso: {1} Legs: {2} Boots: {3}", isHelmetVisible, isTorsoVisible, isLeggingsVisible, isBootsVisible);
}

    private boolean isHelmetVisible;
    private boolean isTorsoVisible;
    private boolean isLeggingsVisible;
    private boolean isBootsVisible;

    public boolean isHelmetVisible()
    {
        return isHelmetVisible;
    }

    public boolean isTorsoVisible()
    {
        return isTorsoVisible;
    }

    public boolean isLeggingsVisible()
    {
        return isLeggingsVisible;
    }

    public boolean isBootsVisible()
    {
        return isBootsVisible;
    }

    public void setHelmetVisible(boolean visible)
    {
        isHelmetVisible = visible;
    }

    public void setTorsoVisible(boolean visible)
    {
        isTorsoVisible = visible;
    }

    public void setLeggingsVisible(boolean visible)
    {
        isLeggingsVisible = visible;
    }

    public void setBootsVisible(boolean visible)
    {
        isBootsVisible = visible;
    }

    public boolean isVisible(EquipmentSlot slot)
    {
        switch(slot)
        {
            case HEAD: return isHelmetVisible;
            case CHEST: return isTorsoVisible;
            case LEGS: return isLeggingsVisible;
            case FEET: return isBootsVisible;
            default:
            return true;
        }
    }

    @Override
    public void readFromNbt(CompoundTag tag) {
        isHelmetVisible = tag.getBoolean(helmet_key);
        isTorsoVisible = tag.getBoolean(torso_key);
        isLeggingsVisible = tag.getBoolean(leggings_key);
        isBootsVisible = tag.getBoolean(boots_key);
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        tag.putBoolean(helmet_key, isHelmetVisible);
        tag.putBoolean(torso_key, isTorsoVisible);
        tag.putBoolean(leggings_key, isLeggingsVisible);
        tag.putBoolean(boots_key, isBootsVisible);
    }

    
}
