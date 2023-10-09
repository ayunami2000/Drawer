package me.ayunami2000.drawer;

import net.minecraft.util.math.Direction;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Command {
    public static boolean parse(String cmd){
        if(cmd.charAt(0)==']'){
            cmd=cmd.substring(1);
            String[] args=cmd.toLowerCase().split(" ");
            switch(args[0]){
                case "help":
                    ImageUtils.chatMsg("Commands:\n - ]help\n - ]draw\n - ]size [1-5000]\n - ]speed [1-10000]\n - ]dir[ection] [up|down|north|east|south|west]\n - ]big\n - ]method\n - ]screen\n - ]stop\n - ]screensize [<x> <y> <width> <height>]");
                    break;
                case "screensize":
                    boolean resetCond=args.length==2&&args[1].equals("reset");
                    if(args.length==1){
                        ImageUtils.chatMsg("Current screen size: "+(Drawer.screenSize==null?"fullscreen":Drawer.screenSize.toString())+"!");
                    }else if(args.length<5&&!resetCond){
                        ImageUtils.chatMsg("Error: Not enough arguments!");
                    }else {
                        if (Drawer.drawing) {
                            ImageUtils.chatMsg("Error: Currently drawing!");
                        } else {
                            if (resetCond) {
                                Drawer.screenSize = null;
                                ImageUtils.chatMsg("Screen size has been reset!");
                            } else {
                                try {
                                    int x=Integer.parseInt(args[1]);
                                    int y=Integer.parseInt(args[2]);
                                    int w=Integer.parseInt(args[3]);
                                    int h=Integer.parseInt(args[4]);
                                    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                                    x=(int)Math.max(0,Math.min(dim.getWidth()-1,x));
                                    y=(int)Math.max(0,Math.min(dim.getHeight()-1,y));
                                    w=(int)Math.max(1,Math.min(dim.getWidth(),w));
                                    h=(int)Math.max(1,Math.min(dim.getHeight(),h));
                                    if(Drawer.screenSize==null){
                                        Drawer.screenSize=new Rectangle(x,y,w,h);
                                    }else{
                                        Drawer.screenSize.setBounds(x,y,w,h);
                                    }
                                    ImageUtils.chatMsg("New screen size: "+Drawer.screenSize.toString());
                                } catch (NumberFormatException e) {
                                    ImageUtils.chatMsg("Error: Invalid values!");
                                }
                            }
                        }
                    }
                    break;
                case "big":
                    if(Drawer.drawing){
                        ImageUtils.chatMsg("Error: Currently drawing!");
                    }else{
                        Drawer.big=!Drawer.big;
                        ImageUtils.chatMsg("Big mode is now "+(Drawer.big?"en":"dis")+"abled!");
                    }
                    break;
                case "method":
                    if(Drawer.drawing){
                        ImageUtils.chatMsg("Error: Currently drawing!");
                    }else{
                        Drawer.ae=!Drawer.ae;
                        ImageUtils.chatMsg("No"+(Drawer.ae?"w":" longer")+" using spawn eggs and area effect clouds!");
                    }
                    break;
                case "dead":
                    if(Drawer.drawing){
                        ImageUtils.chatMsg("Error: Currently drawing!");
                    }else{
                        Drawer.dead=!Drawer.dead;
                        ImageUtils.chatMsg("No"+(Drawer.dead?"w":" longer")+" using dead entities!");
                    }
                    break;
                case "dir":
                case "direction":
                    if(args.length==1){

                    }else{
                        Direction tempdir = Direction.byName(args[1]);
                        if(tempdir==null){
                            ImageUtils.chatMsg("Error: Direction must be one of: up, down, north, east, south, west");
                        }else{
                            Drawer.dir=tempdir;
                            ImageUtils.chatMsg("Set build direction to "+tempdir.getName()+"!");
                        }
                    }
                    break;
                case "stop":
                    if(Drawer.drawing){
                        Drawer.drawing=false;
                        ImageUtils.chatMsg("No longer drawing!");
                    }else{
                        ImageUtils.chatMsg("Error: Not drawing!");
                    }
                    break;
                case "screen":
                    if(Drawer.drawing){
                        ImageUtils.chatMsg("Error: Already drawing!");
                    }else {
                        Drawer.drawing = true;
                        ImageUtils.chatMsg("Now drawing!");
                        if (Drawer.ae) {
                            DrawImage.screenToAE();
                        } else {
                            DrawImage.screenToArmorStands();
                        }
                    }
                    break;
                case "draw":
                    if(Drawer.drawing){
                        ImageUtils.chatMsg("Error: Already drawing!");
                    }else {
                        BufferedImage img = ImageUtils.getImageFromClipboard();
                        if (img == null) {
                            //failed
                            ImageUtils.chatMsg("Error: No image found on clipboard!");
                        } else {
                            Drawer.drawing = true;
                            ImageUtils.chatMsg("Now drawing!");
                            BufferedImage scaled = ImageUtils.scaleImage(img, Drawer.maxSize);
                            if (Drawer.ae) {
                                DrawImage.drawWithAE(scaled);
                            } else {
                                DrawImage.drawWithArmorStands(scaled);
                            }
                        }
                    }
                    break;
                case "size":
                    if(args.length==1){
                        ImageUtils.chatMsg("Current max size value: "+Drawer.maxSize);
                    }else{
                        if(Drawer.drawing){
                            //do nothing
                            ImageUtils.chatMsg("Error: Currently drawing!");
                        }else {
                            try {
                                //set new value
                                Drawer.maxSize = Math.max(1,Math.min(5000,Integer.parseInt(args[1])));
                                ImageUtils.chatMsg("Set new size value to "+Drawer.maxSize+"!");
                            } catch (NumberFormatException e) {
                                ImageUtils.chatMsg("Error: Size must be an integer between 1-5000!");
                            }
                        }
                    }
                    break;
                case "speed":
                    if(args.length==1){
                        ImageUtils.chatMsg("Current speed value: "+Drawer.speed);
                    }else{
                        try {
                            //set new value
                            Drawer.speed = Math.max(1,Math.min(10000,Integer.parseInt(args[1])));
                            ImageUtils.chatMsg("Set new speed value to "+Drawer.speed+"!");
                        } catch (NumberFormatException e) {
                            ImageUtils.chatMsg("Error: Speed must be an integer between 1-10000!");
                        }
                    }
                    break;
                default:
                    ImageUtils.chatMsg("Error: Command not found!");
            }
            return true;
        }
        return false;
    }
}
