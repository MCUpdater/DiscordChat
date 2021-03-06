package net.shadowfacts.discordchat;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AchievementEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.shadowfacts.discordchat.discord.DiscordThread;
import net.shadowfacts.discordchat.utils.MiscUtils;

/**
 * @author shadowfacts
 */
public class ForgeEventHandler {

	@SubscribeEvent
	public void serverChat(ServerChatEvent event) {
		if (DCConfig.enabled && !MiscUtils.isMessageFromDiscord(event.message)) {
			DiscordThread.instance.sendMessageToAllChannels(MiscUtils.toDiscordMessage(event.username, event.message));
		}
	}

	@SubscribeEvent
	public void onPlayerDeath(LivingDeathEvent event) {
		if (DCConfig.sendPlayerDeathMessages) {
			if (event.entityLiving instanceof EntityPlayer) {
				DiscordThread.instance.sendMessageToAllChannels(MiscUtils.createDiscordDeathMessage((EntityPlayer)event.entityLiving));
			}
		}
	}

	@SubscribeEvent
	public void onPlayerRecieveAchievement(AchievementEvent event) {
		if (DCConfig.sendPlayerAchievementMessages) {
			if (event.entityPlayer instanceof EntityPlayerMP) {
				if (((EntityPlayerMP) event.entityPlayer).getStatFile().hasAchievementUnlocked(event.achievement)) {
					return;
				}
				if (!((EntityPlayerMP) event.entityPlayer).getStatFile().canUnlockAchievement(event.achievement)) {
					return;
				}
				DiscordThread.instance.sendMessageToAllChannels(MiscUtils.createAchievementMessage(event.entityPlayer, event.achievement));
			}
		}
	}

	@SubscribeEvent
	public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		if (DCConfig.sendPlayerJoinLeaveMessages) {
			DiscordThread.instance.sendMessageToAllChannels(MiscUtils.createLoggedInMessage(event.player));
		}
	}

	@SubscribeEvent
	public void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
		if (DCConfig.sendPlayerJoinLeaveMessages) {
			DiscordThread.instance.sendMessageToAllChannels(MiscUtils.createLoggedOutMessage(event.player));
		}
	}

}
