package net.shadowfacts.discordchat;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.shadowfacts.discordchat.discord.DiscordThread;
import net.shadowfacts.discordchat.utils.MiscUtils;

public class PlayerEventHandler {

	@SubscribeEvent
	public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		FMLLog.info("DiscordChat: %s logged in", event.player.getDisplayName());
		if (DCConfig.sendPlayerJoinLeaveMessages) {
			DiscordThread.instance.sendMessageToAllChannels(MiscUtils.createLoggedInMessage(event.player));
		}
	}

	@SubscribeEvent
	public void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
		FMLLog.info("DiscordChat: %s logged out", event.player.getDisplayName());
		if (DCConfig.sendPlayerJoinLeaveMessages) {
			DiscordThread.instance.sendMessageToAllChannels(MiscUtils.createLoggedOutMessage(event.player));
		}
	}

}
