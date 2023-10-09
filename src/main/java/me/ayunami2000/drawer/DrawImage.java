package me.ayunami2000.drawer;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class DrawImage {
    private static final String deadText = "Health:0f,DeathTime:99999,";

    public static void drawWithArmorStands(BufferedImage image){
        new Thread(() -> {
            try {
                BlockPos blockPos = ImageUtils.mc.player.getBlockPos().add(ImageUtils.mc.player.getHorizontalFacing().getVector());
                Vec3d blockPosVec3d = Vec3d.of(blockPos);
                for (int y = 0; y < image.getHeight(); y++) {
                    if(!Drawer.drawing)return;
                    ItemStack theItem = new ItemStack(Items.ARMOR_STAND);
                    String restxt = "";
                    int ay=Drawer.dir==Direction.DOWN?y:(image.getHeight() - 1 - y);
                    for (int ax = 0; ax < image.getWidth(); ax++) {
                        if(!Drawer.drawing)return;
                        int ap = image.getRGB(ax, ay),
                                imgA = (ap >> 24) & 0xff,
                                imgR = (ap >> 16) & 0xff,
                                imgG = (ap >> 8) & 0xff,
                                imgB = ap & 0xff;
                        restxt += ("{\"text\":\""+(Drawer.big?"⬛":"·")+"\",\"color\":\"" + String.format("#%02x%02x%02x", imgR, imgG, imgB) + "\"},");
                        if(!Drawer.drawing)return;
                    }
                    restxt = restxt.substring(0, restxt.length() - 1);
                    Vec3d pos = blockPosVec3d.add(Vec3d.of(Drawer.dir.getVector()).multiply(y / (Drawer.big?5.0:19.0)));
                    theItem.setNbt(StringNbtReader.parse("{EntityTag:{Pos:[" + pos.x + "," + pos.y + "," + pos.z + "]," + (Drawer.dead ? deadText : "") + "CustomNameVisible:1b,Marker:1b,Invisible:1b,PersistenceRequired:1b,CustomName:'[" + restxt + "]'}}"));
                    ImageUtils.loadItem(theItem);
                    ImageUtils.rightClickBlock(blockPos, Direction.UP);
                    if(!Drawer.drawing)return;
                    Thread.sleep(Drawer.speed);
                }
            }catch(InterruptedException | CommandSyntaxException e){
                //lol
            }
            ImageUtils.chatMsg("Finished drawing!");
            Drawer.drawing=false;
        }).start();
    }
    public static void drawWithAE(BufferedImage image){
        new Thread(() -> {
            try {
                BlockPos blockPos = ImageUtils.mc.player.getBlockPos().add(ImageUtils.mc.player.getHorizontalFacing().getVector());
                Vec3d blockPosVec3d = Vec3d.of(blockPos);
                for (int y = 0; y < image.getHeight(); y++) {
                    if(!Drawer.drawing)return;
                    ItemStack theItem = new ItemStack(Items.AXOLOTL_SPAWN_EGG);
                    String restxt = "";
                    int ay=Drawer.dir==Direction.DOWN?y:(image.getHeight() - 1 - y);
                    for (int ax = 0; ax < image.getWidth(); ax++) {
                        if(!Drawer.drawing)return;
                        int ap = image.getRGB(ax, ay),
                                imgA = (ap >> 24) & 0xff,
                                imgR = (ap >> 16) & 0xff,
                                imgG = (ap >> 8) & 0xff,
                                imgB = ap & 0xff;
                        restxt += ("{\"text\":\""+(Drawer.big?"⬛":"·")+"\",\"color\":\"" + String.format("#%02x%02x%02x", imgR, imgG, imgB) + "\"},");
                        if(!Drawer.drawing)return;
                    }
                    restxt = restxt.substring(0, restxt.length() - 1);
                    Vec3d pos = blockPosVec3d.add(Vec3d.of(Drawer.dir.getVector()).multiply(y / (Drawer.big?5.0:19.0)));
                    theItem.setNbt(StringNbtReader.parse("{EntityTag:{id:\"area_effect_cloud\",Particle:\"block air\",Radius:0f,Duration:0,WaitTime:"+(6000+(((image.getHeight()-1)-y)*Drawer.speed/50.0))+",Pos:[" + pos.x + "," + pos.y + "," + pos.z + "],CustomNameVisible:1b,CustomName:'[" + restxt + "]'}}"));
                    ImageUtils.loadItem(theItem);
                    ImageUtils.rightClickBlock(blockPos, Direction.UP);
                    if(!Drawer.drawing)return;
                    Thread.sleep(Drawer.speed);
                }
            }catch(InterruptedException | CommandSyntaxException e){
                //lol
            }
            ImageUtils.chatMsg("Finished drawing!");
            Drawer.drawing=false;
        }).start();
    }
    public static void screenToAE(){
        new Thread(() -> {
            try {
                BlockPos blockPos = ImageUtils.mc.player.getBlockPos().add(ImageUtils.mc.player.getHorizontalFacing().getVector());
                while(Drawer.drawing) {
                    BufferedImage image = ImageUtils.scaleImage(ImageUtils.screenshot(), Drawer.maxSize);
                    Vec3d blockPosVec3d = Vec3d.of(blockPos);
                    for (int y = 0; y < image.getHeight(); y++) {
                        if (!Drawer.drawing) return;
                        ItemStack theItem = new ItemStack(Items.AXOLOTL_SPAWN_EGG);
                        String restxt = "";
                        int ay = Drawer.dir == Direction.DOWN ? y : (image.getHeight() - 1 - y);
                        for (int ax = 0; ax < image.getWidth(); ax++) {
                            if (!Drawer.drawing) return;
                            int ap = image.getRGB(ax, ay),
                                    imgA = (ap >> 24) & 0xff,
                                    imgR = (ap >> 16) & 0xff,
                                    imgG = (ap >> 8) & 0xff,
                                    imgB = ap & 0xff;
                            restxt += ("{\"text\":\"" + (Drawer.big ? "⬛" : "·") + "\",\"color\":\"" + String.format("#%02x%02x%02x", imgR, imgG, imgB) + "\"},");
                            if (!Drawer.drawing) return;
                        }
                        restxt = restxt.substring(0, restxt.length() - 1);
                        Vec3d pos = blockPosVec3d.add(Vec3d.of(Drawer.dir.getVector()).multiply(y / (Drawer.big ? 5.0 : 19.0)));
                        theItem.setNbt(StringNbtReader.parse("{EntityTag:{id:\"area_effect_cloud\",Particle:\"block air\",Radius:0f,Duration:0,WaitTime:" + ((image.getHeight() - 1) * Drawer.speed / 50.0) + ",Pos:[" + pos.x + "," + pos.y + "," + pos.z + "],CustomNameVisible:1b,CustomName:'[" + restxt + "]'}}"));
                        ImageUtils.loadItem(theItem);
                        ImageUtils.rightClickBlock(blockPos, Direction.UP);
                        if (!Drawer.drawing) return;
                        Thread.sleep(Drawer.speed);
                    }
                }
            }catch(InterruptedException | CommandSyntaxException e){
                //lol
            }
            ImageUtils.chatMsg("Finished drawing!");
            Drawer.drawing=false;
        }).start();
    }
    public static List<Entity> armorStands = new ArrayList<Entity>();
    public static List<String> cachedLines = new ArrayList<String>();
    public static void screenToArmorStands(){
        armorStands.clear();
        cachedLines.clear();
        BlockPos blockPos = ImageUtils.mc.player.getBlockPos().add(ImageUtils.mc.player.getHorizontalFacing().getVector());
        new Thread(() -> {
            try {
                while(Drawer.drawing) {
                    boolean rescanArmorStands=armorStands.size()==0;
                    for (Entity armorStand : armorStands) {
                        if(armorStand==null||armorStand.isRemoved()){
                            rescanArmorStands=true;
                            break;
                        }
                    }
                    if(rescanArmorStands) {
                        List<Entity> list =
                                StreamSupport.stream(ImageUtils.mc.world.getEntities().spliterator(), true)
                                        .filter(e -> !e.isRemoved())
                                        .filter(e -> e.getType() == EntityType.ARMOR_STAND)
                                        .filter(e -> {
                                            ArmorStandEntity ae = (ArmorStandEntity)e;
                                            return !ae.isMarker()&&ae.isSmall()&&ae.hasNoGravity()&&!ae.isInvisible()&&ae.hasCustomName()&&ae.isCustomNameVisible();
                                        })
                                        .filter(e -> {
                                            NbtCompound nbt = new NbtCompound();
                                            e.writeNbt(nbt);
                                            return nbt.contains("ArmorItems")&&nbt.get("ArmorItems").asString().matches(".*tag:\\{drawer:\""+ImageUtils.mc.player.getUuidAsString()+"-\\d+\"}.*");
                                        }).toList();
                        if (!Drawer.drawing) return;
                        armorStands.clear();
                        for (int i = 0; i < list.size(); i++) {
                            int finalI = i;
                            List<Entity> resEntity = list.stream().filter(e -> {
                                NbtCompound nbt = new NbtCompound();
                                e.writeNbt(nbt);
                                return nbt.get("ArmorItems").asString().matches(".*tag:\\{drawer:\""+ImageUtils.mc.player.getUuidAsString()+"-"+finalI+"\"}.*");
                            }).collect(Collectors.toList());
                            if (resEntity.size() == 0) {
                                armorStands.add(null);
                            } else {
                                while(resEntity.size()>1) {
                                    if (!Drawer.drawing) return;
                                    //lets remove excess armorstands
                                    ImageUtils.mc.interactionManager.attackEntity(ImageUtils.mc.player, resEntity.get(1));
                                    resEntity.remove(1);
                                    Thread.sleep(Drawer.speed);
                                }
                                armorStands.add(resEntity.get(0));
                            }
                            if (!Drawer.drawing) return;
                        }
                    }
                    if (!Drawer.drawing) return;
                    BufferedImage image = ImageUtils.scaleImage(ImageUtils.screenshot(), Drawer.maxSize);
                    Vec3d blockPosVec3d = Vec3d.of(blockPos);
                    for (int y = 0; y < image.getHeight(); y++) {
                        if (!Drawer.drawing) return;
                        ItemStack theItem = new ItemStack(Items.ARMOR_STAND);
                        String restxt = "";
                        int ay = Drawer.dir == Direction.DOWN ? y : (image.getHeight() - 1 - y);
                        for (int ax = 0; ax < image.getWidth(); ax++) {
                            if (!Drawer.drawing) return;
                            int ap = image.getRGB(ax, ay),
                                    imgA = (ap >> 24) & 0xff,
                                    imgR = (ap >> 16) & 0xff,
                                    imgG = (ap >> 8) & 0xff,
                                    imgB = ap & 0xff;
                            restxt += ("{\"text\":\"" + (Drawer.big ? "⬛" : "·") + "\",\"color\":\"" + String.format("#%02x%02x%02x", imgR, imgG, imgB) + "\"},");
                            if (!Drawer.drawing) return;
                        }
                        restxt = restxt.substring(0, restxt.length() - 1);
                        //greater than or equal incase old armor stands exist
                        if(armorStands.size()>=image.getHeight()&&armorStands.get(y)!=null&&cachedLines.size()==image.getHeight()&&cachedLines.get(y).equals(restxt)){
                            //dont update
                        }else{
                            //update
                            while(cachedLines.size()<image.getHeight())cachedLines.add("");
                            cachedLines.set(y,restxt);
                            while(armorStands.size()<image.getHeight())armorStands.add(null);
                            if(armorStands.get(y)!=null) {
                                //destroy armorstand if exist
                                ImageUtils.mc.interactionManager.attackEntity(ImageUtils.mc.player, armorStands.get(y));
                            }
                            armorStands.set(y,null);
                            if (!Drawer.drawing) return;
                            Vec3d pos = blockPosVec3d.add(Vec3d.of(Drawer.dir.getVector()).multiply(y / (Drawer.big ? 5.0 : 19.0)));
                            theItem.setNbt(StringNbtReader.parse("{EntityTag:{Pos:[" + pos.x + "," + pos.y + "," + pos.z + "]," + (Drawer.dead ? deadText : "") + "CustomNameVisible:1b,Small:1b,NoGravity:1b,Pose:{LeftLeg:[180f,0f,0f],RightLeg:[180f,0f,0f],Head:[180f,0f,0f]},ArmorItems:[{id:\"sponge\",Count:1b,tag:{drawer:\""+ImageUtils.mc.player.getUuidAsString()+"-"+y+"\"}}],PersistenceRequired:1b,CustomName:'[" + restxt + "]'}}"));
                            ImageUtils.loadItem(theItem);
                            ImageUtils.rightClickBlock(ImageUtils.mc.player.getBlockPos().add(ImageUtils.mc.player.getHorizontalFacing().getVector()), Direction.UP);
                            if (!Drawer.drawing) return;
                            Thread.sleep(Drawer.speed);
                        }
                    }
                }
            }catch(InterruptedException | CommandSyntaxException e){
                //lol
            }
        }).start();
    }
}
