package me.ayunami2000.drawer;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.math.Direction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;

public class Drawer implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("drawer");

	public static int maxSize = 250;
	public static int speed = 100;
	public static Direction dir = Direction.UP;
	public static boolean big = false;
	public static boolean ae = false;
	public static boolean dead = false;
	public static Rectangle screenSize = null;

	public static boolean drawing = false;

	@Override
	public void onInitialize() {
		System.setProperty("java.awt.headless","false");
		LOGGER.info("Drawer by ayunami2000 initialized!");
	}
}
