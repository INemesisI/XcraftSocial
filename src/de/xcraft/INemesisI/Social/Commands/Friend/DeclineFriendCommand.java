package de.xcraft.INemesisI.Social.Commands.Friend;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.xcraft.INemesisI.Library.Command.XcraftCommand;
import de.xcraft.INemesisI.Library.Manager.XcraftCommandManager;
import de.xcraft.INemesisI.Library.Manager.XcraftPluginManager;
import de.xcraft.INemesisI.Social.Msg;
import de.xcraft.INemesisI.Social.Msg.Replace;
import de.xcraft.INemesisI.Social.Manager.SocialManager;
import de.xcraft.INemesisI.Social.Manager.SocialPlayer;


public class DeclineFriendCommand extends XcraftCommand {



	public DeclineFriendCommand(XcraftCommandManager cManager, String command, String name, String pattern, String usage, String desc, String permission) {
		super(cManager, command, name, pattern, usage, desc, permission);
	}

	@Override
	public boolean execute(XcraftPluginManager manager, CommandSender sender, String[] args) {
		SocialManager sManager = (SocialManager) manager;
		SocialPlayer data = sManager.getPlayer(sender.getName());
		String player = null;
		for (String f : data.getFriends()) {
			if (f.startsWith("?") && f.toLowerCase().contains(args[0].toLowerCase())) {
				player = f.replace("?", "");
				break;
			}
		}
		if (player == null) {
			manager.plugin.getMessenger().sendInfo(sender,
					Msg.FRIEND_REQUEST_NOT_FOUND.toString(Replace.PLAYER(args[0])), true);
			return true;
		}
		if (player.equals(sender.getName())) {
			manager.plugin.getMessenger().sendInfo(sender, Msg.ERR_PLAYER_IS_SENDER.toString(), true);
			return true;
		} else {
			data.getFriends().remove("?" + player);
			manager.plugin.getMessenger().sendInfo(sender,
					Msg.FRIEND__SUCCESSUFL.toString(Replace.PLAYER(player)), true);
			Player p = sManager.getBukkitPlayer(player);
			if (p != null) {
				manager.plugin.getMessenger().sendInfo(p,
						Msg.FRIEND_DECLINE_NOTIFY.toString(Replace.PLAYER(sender.getName())), true);
			}
			return true;
		}
	}
}