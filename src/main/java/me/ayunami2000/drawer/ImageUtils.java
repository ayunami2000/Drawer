package me.ayunami2000.drawer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageUtils {
    public static MinecraftClient mc = MinecraftClient.getInstance();

    public static Robot robot = null;

    static {
        try {
            robot=new Robot();
        } catch (AWTException e) {
            //fard
        }
    }

    public static BufferedImage getImageFromClipboard(){
        Clipboard c= Toolkit.getDefaultToolkit().getSystemClipboard();
        if(c.isDataFlavorAvailable(DataFlavor.imageFlavor)){
            try {
                Image img = (Image)c.getData(DataFlavor.imageFlavor);
                return ImageUtils.convertImageToBuffered(img);
            }catch(UnsupportedFlavorException | IOException e){
                return null;
            }
        }else{
            //no image found
            return null;
        }
    }
    public static void loadItem(ItemStack itemStack){
        loadItem(itemStack,mc.player.getInventory().selectedSlot);
    }
    public static void loadItem(ItemStack itemStack, int slot){
        mc.interactionManager.clickCreativeStack(itemStack,slot+36);
        //mc.player.networkHandler.sendPacket(new CreativeInventoryActionC2SPacket(slot+36,itemStack));
    }
    public static void rightClickBlock(BlockPos block, Direction side){
        mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, new BlockHitResult(Vec3d.of(block), side, block, false));
        mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);
    }
    public static BufferedImage convertImageToBuffered(Image image){
        BufferedImage newImage=new BufferedImage(image.getWidth(null),image.getHeight(null),BufferedImage.TYPE_INT_ARGB);
        Graphics2D g=newImage.createGraphics();
        g.drawImage(image,0,0,null);
        g.dispose();
        return newImage;
    }
    public static BufferedImage scaleImage(BufferedImage image,int maxSize){
        Integer imgWidth=image.getWidth(),
                imgHeight=image.getHeight();
        if(imgWidth>maxSize||imgHeight>maxSize) {
            Integer aspectRatio=imgWidth/imgHeight;
            image = convertImageToBuffered(image.getScaledInstance(aspectRatio<1?-1:maxSize, aspectRatio<1?maxSize:-1, Image.SCALE_FAST));//Image.SCALE_DEFAULT
        }
        return image;
    }
    public static BufferedImage screenshot(){
        Rectangle screenRect=Drawer.screenSize==null?new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()):Drawer.screenSize;
        return robot.createScreenCapture(screenRect);
    }
    public static void chatMsg(String s){
        mc.player.sendMessage(Text.of("§2§l[§a§lDrawer§2§l]§r §d"+s),false);
    }
}