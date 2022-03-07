package cat.albertvilalta.pyromaniac;


import static cat.albertvilalta.pyromaniac.Constants.LOGGER;
import static cat.albertvilalta.pyromaniac.Constants.PREFIX;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public final class PyroCommand {
	
	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
		
		dispatcher.register(Commands.literal("pyro")
			.then(Commands.literal("start")
					.executes(PyroCommand::start))
			.then(Commands.literal("stop")
					.executes(PyroCommand::stop))
			.then(Commands.literal("fire")
					.executes(PyroCommand::fire))
			);
		
	  }
	
	private static int start(CommandContext<CommandSourceStack> context)  throws CommandSyntaxException {
		LOGGER.info(PREFIX+"START");
		Player sender = context.getSource().getPlayerOrException();
		if(PyroCapability.PYRO.isRegistered()) {
			PyroCapabilityInterface pyro = sender.getCapability(PyroCapability.PYRO).orElse(null);
			if(!pyro.isRunning()) {
				pyro.setRunning(true);
				pyro.setReadyToFire(false);
				pyro.clearBlocs();
				sender.sendMessage(new TextComponent(PREFIX + "Pyromaniac selection started. Right click blocks to fire."), sender.getUUID());
			}else {
				sender.sendMessage(new TextComponent(PREFIX + "Pyromaniac selection already started."), sender.getUUID());
			}			
		}else {
			sender.sendMessage(new TextComponent(PREFIX + "[ERROR] You don't have the pyromaniac capability :("), sender.getUUID());
		}
		return 1;
	}
	
	private static int stop(CommandContext<CommandSourceStack> context)  throws CommandSyntaxException {
		LOGGER.info(PREFIX+"STOP");
		
		Player sender = context.getSource().getPlayerOrException();
		if(PyroCapability.PYRO.isRegistered()) {
			PyroCapabilityInterface pyro = sender.getCapability(PyroCapability.PYRO).orElse(null);
			if(pyro.isRunning()) {
				pyro.setRunning(false);
				sender.sendMessage(new TextComponent(PREFIX + "Pyromaniac selection stopped. Please run twice '/pyro fire' to start fires."), sender.getUUID());
			}else {
				sender.sendMessage(new TextComponent(PREFIX + "Pyromaniac selection not started. Please run /pyro start."), sender.getUUID());
			}
		}
		return 1;
	}
	
	private static int fire(CommandContext<CommandSourceStack> context)  throws CommandSyntaxException {
		LOGGER.info(PREFIX+"FIRE");
		
		Player sender = context.getSource().getPlayerOrException();
		if(PyroCapability.PYRO.isRegistered()) {
			PyroCapabilityInterface pyro = sender.getCapability(PyroCapability.PYRO).orElse(null);
			if((!(pyro.getObjectius() == null)) && (!(pyro.getObjectius().isEmpty()))) {
				if(!pyro.isReadyToFire()) {
					sender.sendMessage(new TextComponent(PREFIX + "Run '/pyro fire' again to start the magic."), sender.getUUID());
					pyro.setReadyToFire(true);
				}else {
					sender.sendMessage(new TextComponent(PREFIX + "Firing. Muuaaaahahahahahaha :D"), sender.getUUID());
					for(Objectiu obj : pyro.getObjectius()) {
						Block bloc = Blocks.FIRE;
						
						Level l = obj.getLevel();
						Player p = obj.getPlayer();
						BlockPos pos = obj.getPos();
						Direction dir = obj.getDir();
						
						BlockPos pos2 = pos.relative(dir);
						
						if (BaseFireBlock.canBePlacedAt(l, pos2, dir)) {
							l.setBlock(pos2, bloc.defaultBlockState(), 3);
							bloc.setPlacedBy(l, pos2, bloc.defaultBlockState(), p, ItemStack.EMPTY);
						}
					}
				}
			}else {
				sender.sendMessage(new TextComponent(PREFIX + "[ERROR] I don't know where to set the fire. Run '/pyro start' first."), sender.getUUID());
			}
		}
		return 1;
	}
	
}