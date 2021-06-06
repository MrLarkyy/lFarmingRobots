package cz.larkyy.lfarmingrobots.robots;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;

import java.util.ArrayList;
import java.util.List;

public class Robot {

    private final Location location;
    private final List<Location> field;
    private CropType cropType;
    private ArmorStand as;

    public Robot(Location location, CropType cropType) {
        this.location = location;
        this.cropType = cropType;
        this.field = new ArrayList<>();
        setAs(RobotStorage.spawnAs(location));
    }

    public Robot(Location location, CropType cropType, List<Location> field) {
        this.location = location;
        this.cropType = cropType;
        this.field = field;
        setAs(RobotStorage.spawnAs(location));
    }

    public Location getLocation() {
        return location;
    }

    public ArmorStand getAs() {
        return as;
    }

    public void setAs(ArmorStand as) {
        this.as = as;
    }

    public String getLocationString() {
        return location.getWorld().getName()+"||"+location.getBlockX()+"||"+location.getBlockY()+"||"+location.getBlockZ();
    }

    public CropType getCropType() {
        return cropType;
    }

    public List<Location> getField() {
        return field;
    }

    public void setCropType(CropType cropType) {
        this.cropType = cropType;
        RobotStorage.getDataCfg().set("robots."+getLocationString()+".cropType",cropType.toString());
        RobotStorage.DATA.save();
    }

    public void addFieldLoc(Location loc) {
        field.add(loc);
        saveFields();
    }

    public void removeFieldLoc(Location loc) {
        field.remove(loc);
        saveFields();
    }

    public void saveFields() {
        List<String> locStrings = new ArrayList<>();
        for (Location loc : field) {
            locStrings.add(loc.getWorld().getName()+"||"+loc.getBlockX()+"||"+loc.getBlockY()+"||"+loc.getBlockZ());
        }

        RobotStorage.getDataCfg().set("robots."+getLocationString()+".field",locStrings);
        RobotStorage.DATA.save();
    }

    public boolean isFieldLoc(Location loc) {
        return field.contains(loc);
    }
}
