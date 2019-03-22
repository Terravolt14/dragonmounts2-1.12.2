/*
 ** 2013 May 30
 **
 ** The author disclaims copyright to this source code.  In place of
 ** a legal notice, here is a blessing:
 **    May you do good and not evil.
 **    May you find forgiveness for yourself and forgive others.
 **    May you share freely, never taking more than you give.
 */
package com.TheRPGAdventurer.ROTD;

import com.google.common.collect.Lists;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.util.List;

public class DragonMountsConfig {

	private static Configuration config;

	public static final String CATEGORY_MAIN = "main";
	public static final String CATEGORY_WORLDGEN = "worldGen";
	public static final String CATEGORY_CLIENTDM2 = "clientDM2";

	// config properties
	private static boolean disableBlockOverride = false;
	private static boolean debug = false;
	public static boolean shouldChangeBreedViaHabitatOrBlock = true;
	public static boolean canDragonDespawn = true;

	public static boolean canIceBreathBePermanent = false;
	public static boolean canFireBreathAffectBlocks = true;

	public static boolean useCommandingPlayer = false;
	public static boolean allowOtherPlayerControl = true;
	public static boolean allowBreeding = true;


	public static boolean canSpawnSurfaceDragonNest = true;
	public static boolean canSpawnUnderGroundNest = true;
	public static boolean canSpawnNetherNest = true;
	public static boolean canSpawnEndNest = true;


	public static double BASE_ARMOR  = 12.0F;
	public static double BASE_DAMAGE  = 5.0F;
	public static int FACTOR  = 75;

	// chances
	public static int AllNestRarity  = 110;
	public static int AllNestRarity1  = 180;
	public static int ForestNestRarity1  = 555;
	public static int SunlightNestRarity  = 40;
	public static int OceanNestRarity  = 2400;
	public static int EnchantNestRarity  = 100;
	public static int JungleNestRarity  = 555;

	public static int netherNestRarity = 500;
	public static int netherNestRarerityInX = 32;
	public static int netherNestRarerityInZ = 32;

	public static double ThirdPersonZoom = 20;

	public static int dragonFolloOwnerFlyingHeight = 50;
	public static int dragonanderFromHomeDist = 50;

	public static double maxFLightHeight = 40;

	public static int[] dragonBlacklistedDimensions = new int[]{1, -1};
	public static int[] dragonWhitelistedDimensions = new int[]{0};

	public static int minimumDistance = 16;

	public static boolean useDimensionBlackList = true;

	public static void PreInit() {
		File configFile = new File(Loader.instance().getConfigDir(), DragonMounts.MODID + ".cfg");
		config = new Configuration(configFile);
		syncFromFiles();
	}

	public static void clientPreInit() {
		MinecraftForge.EVENT_BUS.register(new ConfigEventHadler());
	}

	public static void syncFromFiles() {
		syncconfigs(true, true);
	}

	public static void syncFromGui() {
		syncconfigs(false, true);
	}

	public static void syncFromFields() {
		syncconfigs(false, false);
	}

	public static Configuration getConfig() {return config;}

	public static boolean isDebug() {return debug;}

	public static boolean isDisableBlockOverride() {
		return disableBlockOverride;
	}

	private static void syncconfigs(boolean loadFromConfigFile, boolean readFromConfig) {
		if(loadFromConfigFile)
			config.load();

		List<String> propOrder = Lists.newArrayList();
		Property prop;

		/*
		 *  MAIN
		 */
		prop = config.get(CATEGORY_MAIN, "debug", debug);
		prop.setComment("Debug mode. Unless you're a developer or are told to activate it, you don't want to set this to true.");
		debug = prop.getBoolean();
		propOrder.add(prop.getName());

		prop = config.get(CATEGORY_MAIN, "can eggs change breeds", shouldChangeBreedViaHabitatOrBlock);
		prop.setComment("Enables changing of egg breeds via block or environment");
		shouldChangeBreedViaHabitatOrBlock = prop.getBoolean();
		propOrder.add(prop.getName());

		prop = config.get(CATEGORY_MAIN, "disable block override", disableBlockOverride);
		prop.setComment("Disables right-click override on the vanilla dragon egg block. May help to fix issues with other mods.");
		disableBlockOverride = prop.getBoolean();
		propOrder.add(prop.getName());

		prop = config.get(CATEGORY_MAIN, "Armor", BASE_ARMOR);
		prop.setComment("Makes Dragons Tougher or Not");
		BASE_ARMOR = prop.getDouble();
		propOrder.add(prop.getName());

		prop = config.get(CATEGORY_MAIN, "Damage", BASE_DAMAGE);
		prop.setComment("Damage for dragon attack");
		BASE_DAMAGE = prop.getDouble();
		propOrder.add(prop.getName());

		prop = config.get(CATEGORY_MAIN, "Health Regen Speed", FACTOR);
		prop.setComment("Lesser numbers slower regen for dragons");
		FACTOR = prop.getInt();
		propOrder.add(prop.getName());

		prop = config.get(CATEGORY_MAIN, "can dragons despawn", canDragonDespawn);
		prop.setComment("Enables or Disables dragons ability to despawn, works only for adult non tamed dragons");
		canDragonDespawn = prop.getBoolean();
		propOrder.add(prop.getName());

		prop = config.get(CATEGORY_MAIN, "can ice breath be permanent", canIceBreathBePermanent);
		prop.setComment("refers to the ice breath for the dragon in water, set true if you want the ice block to be permanent. false otherwise.");
		canIceBreathBePermanent = prop.getBoolean();
		propOrder.add(prop.getName());

		prop = config.get(CATEGORY_MAIN, "fire breath affect blocks", canFireBreathAffectBlocks);
		prop.setComment("refers to the fire breath to affect blocks");
		canFireBreathAffectBlocks = prop.getBoolean();
		propOrder.add(prop.getName());

		prop = config.get(CATEGORY_MAIN, "use CommandingPlayer", useCommandingPlayer);
		prop.setComment("Use a commanding player method(Experimental) to make dragons land on multiple players");
		useCommandingPlayer = prop.getBoolean();
		propOrder.add(prop.getName());

		prop = config.get(CATEGORY_MAIN, "Allow Other Player's Control", allowOtherPlayerControl);
		prop.setComment("Disable or enable the dragon's ability to obey other players");
		allowOtherPlayerControl = prop.getBoolean();
		propOrder.add(prop.getName());

		prop = config.get(CATEGORY_MAIN, "Allow Other Breeding", allowBreeding);
		prop.setComment("Allow or disallow breeding");
		allowBreeding = prop.getBoolean();
		propOrder.add(prop.getName());

		prop = config.get(CATEGORY_CLIENTDM2, "Max Flight Height", maxFLightHeight);
		prop.setComment("Max flight for dragons circling players on a whistle");
		maxFLightHeight = prop.getDouble();
		propOrder.add(prop.getName());

		prop = config.get(CATEGORY_CLIENTDM2, "Third Person Zoom BACK", ThirdPersonZoom);
		prop.setComment("Zoom out for third person 2 while riding the the dragon and dragon carriages DO NOT EXXAGERATE IF YOU DONT WANT CORRUPTED WORLDS");
		ThirdPersonZoom = prop.getDouble();
		propOrder.add(prop.getName());

		prop = config.get(CATEGORY_CLIENTDM2, "Wander From HomeDist", dragonanderFromHomeDist);
		prop.setComment("Wander From HomeDist");
		dragonanderFromHomeDist = prop.getInt();
		propOrder.add(prop.getName());

		/*
		 *  WORLDGEN
		 */

		dragonBlacklistedDimensions = config.get("all", "Blacklisted Dragon Dimensions", new int[]{-1, 1}, "Dragons cannot spawn in these dimensions' IDs").getIntList();
		dragonWhitelistedDimensions = config.get("all", "Whitelisted Dragon Dimensions", new int[]{0}, "Dragons can only spawn in these dimensions' IDs").getIntList();
		useDimensionBlackList = config.getBoolean("use Dimension Blacklist", "all", true, "true to use dimensional blacklist, false to use the whitelist.");

		prop = config.get(CATEGORY_WORLDGEN, "canSpawnSurfaceDragonNest", canSpawnSurfaceDragonNest);
		prop.setComment("Enables spawning of nests in extreme hills");
		canSpawnSurfaceDragonNest = prop.getBoolean();
		propOrder.add(prop.getName());

		prop = config.get(CATEGORY_WORLDGEN, "canSpawnNetherNest", canSpawnNetherNest);
		prop.setComment("Enables spawning of nether, zombie, and skeleton dragon nests in the nether");
		canSpawnNetherNest = prop.getBoolean();
		propOrder.add(prop.getName());

		prop = config.get(CATEGORY_WORLDGEN, "canSpawnEnchantNest", canSpawnEndNest);
		prop.setComment("Enables spawning of end dragon nests in end cities");
		canSpawnEndNest = prop.getBoolean();
		propOrder.add(prop.getName());

		// surface world nest
		prop = config.get(CATEGORY_WORLDGEN, "Common Nest Rarity", AllNestRarity);
		prop.setComment("Determines how rare Water, Fire dragon nests will mainly spawn. Higher numbers = higher rarity (in other words  how many blocks for another nest to spawn), "
				+ "(Note: Expermiment on a new world when editing these numbers because it may cause damages to your own worlds)");
		AllNestRarity = prop.getInt();
		propOrder.add(prop.getName());

		// surface world nest
		prop = config.get(CATEGORY_WORLDGEN, "Common Nest Rarity 2", AllNestRarity1);
		prop.setComment("Determines how rare Terra, Ice dragon nests will mainly spawn. Higher numbers = higher rarity (in other words  how many blocks for another nest to spawn), "
				+ "(Note: Expermiment on a new world when editing these numbers because it may cause damages to your own worlds)");
		AllNestRarity1 = prop.getInt();
		propOrder.add(prop.getName());

		prop = config.get(CATEGORY_WORLDGEN, "Forest Nest Rarity", AllNestRarity1);
		prop.setComment("Determines how rare Forest Plains dragon nests will mainly spawn. I did this because the forest biome is too common thus making the forest breed to common. Higher numbers = higher rarity (in other words  how many blocks for another nest to spawn), "
				+ "(Note: Expermiment on a new world when editing these numbers because it may cause damages to your own worlds)");
		AllNestRarity1 = prop.getInt();
		propOrder.add(prop.getName());

		// sunlight world nest
		prop = config.get(CATEGORY_WORLDGEN, "Sunlight Nest Rarity", SunlightNestRarity);
		prop.setComment("Determines how rare sunlight dragon temples will mainly spawn. Higher numbers = higher rarity (in other words  how many blocks for another nest to spawn), "
				+ "(Note: Expermiment on a new world when editing these numbers because it may cause damages to your own worlds)");
		SunlightNestRarity = prop.getInt();
		propOrder.add(prop.getName());

		// sunlight world nest
		prop = config.get(CATEGORY_WORLDGEN, "Ocean Nest Rarity", OceanNestRarity);
		prop.setComment("Determines how rare moonlight or aether dragon temples will spawn above the ocean. Higher numbers = higher rarity (in other words  how many blocks for another nest to spawn), "
				+ "(Note: Expermiment on a new world when editing these numbers because it may cause damages to your own worlds)");
		OceanNestRarity = prop.getInt();
		propOrder.add(prop.getName());

		prop = config.get(CATEGORY_WORLDGEN, "Jungle Nest Rarity", JungleNestRarity);
		prop.setComment("Determines how rare forest jungnle dragon nests will mainly spawn. Higher numbers = higher rarity (in other words  how many blocks for another nest to spawn), "
				+ "(Note: Expermiment on a new world when editing these numbers because it may cause damages to your own worlds)");
		JungleNestRarity = prop.getInt();
		propOrder.add(prop.getName());

		prop = config.get(CATEGORY_WORLDGEN, "Enchant Nest Rarity", EnchantNestRarity);
		prop.setComment("Determines how rare forest enchant dragon nests will mainly spawn. Higher numbers = higher rarity (in other words  how many blocks for another nest to spawn), "
				+ "(Note: Expermiment on a new world when editing these numbers because it may cause damages to your own worlds)");
		EnchantNestRarity = prop.getInt();
		propOrder.add(prop.getName());

		// nether nest
		prop = config.get(CATEGORY_WORLDGEN, "Nether Nest Chance", netherNestRarity);
		prop.setComment("Determines how rare nether nests will mainly spawn. Higher numbers = higher rarity (in other words  how many blocks for another nest to spawn)"
				+ "(Note: Expermiment on a new world when editing these numbers because it may cause damages to your own worlds)");
		netherNestRarity = prop.getInt();
		propOrder.add(prop.getName());

		prop = config.get(CATEGORY_WORLDGEN, "2 Nether Nest Rarity X", netherNestRarerityInX);
		prop.setComment("Determines how rare nether nests will spawn in the X Axis. Higher numbers = higher rarity (in other words  how many blocks for another nest to spawn)"
				+ "(Note: Expermiment on a new world when editing these numbers because it may cause damages to your own worlds)");
		netherNestRarerityInX = prop.getInt();
		propOrder.add(prop.getName());

		prop = config.get(CATEGORY_WORLDGEN, "2 Nest Nether Rarity Z", netherNestRarerityInZ);
		prop.setComment("Determines how rare nether nests will spawn in the Z Axis. Higher numbers = higher rarity (in other words  how many blocks for another nest to spawn)"
				+ "(Note: Expermiment on a new world when editing these numbers because it may cause damages to your own worlds)");
		netherNestRarerityInZ = prop.getInt();
		propOrder.add(prop.getName());


		config.setCategoryPropertyOrder(CATEGORY_WORLDGEN, propOrder);
		config.save();

		if (config.hasChanged()) {
			config.save();
		}
	}

	public static class ConfigEventHadler {

		@SubscribeEvent(priority = EventPriority.LOWEST)
		public void onEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
			if(event.getModID().equals(DragonMounts.MODID)) {
				syncFromGui();
			}
		}
	}

}
