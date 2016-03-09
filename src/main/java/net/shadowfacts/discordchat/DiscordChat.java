package net.shadowfacts.discordchat;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.common.MinecraftForge;
import net.shadowfacts.discordchat.discord.DiscordThread;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author shadowfacts
 */
@Mod(modid = DiscordChat.modId, useMetadata = true, acceptableRemoteVersions = "*")
public class DiscordChat {

	public static final String modId = "DiscordChat";

	public static Logger log = LogManager.getLogger(modId);

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		DCConfig.init(event.getModConfigurationDirectory());

		if (DCConfig.enabled) {
			MinecraftForge.EVENT_BUS.register(new ForgeEventHandler());
			FMLCommonHandler.instance().bus().register(new PlayerEventHandler());
		}
	}

	@Mod.EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
		DiscordThread.runThread();
	}

}
