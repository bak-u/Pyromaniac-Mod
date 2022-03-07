package cat.albertvilalta.pyromaniac;

import static cat.albertvilalta.pyromaniac.Constants.LOGGER;

import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


@Mod("pyromaniac")
public class PyromaniacMod
{

    public PyromaniacMod() {
    	MinecraftForge.EVENT_BUS.register(PyroCapabilityAttacher.class);
        MinecraftForge.EVENT_BUS.register(this);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(PyroCapability::register);        
    }
    
    @SubscribeEvent
    public void onCommandsRegister(RegisterCommandsEvent event) {
    	
    	CommandDispatcher<CommandSourceStack> commandDispatcher = event.getDispatcher();
    	PyroCommand.register(commandDispatcher);	
    }   
    
    @SubscribeEvent
    public void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event){
    	
    	if(PyroCapability.PYRO.isRegistered()) {
    		PyroCapabilityInterface pyro = event.getPlayer().getCapability(PyroCapability.PYRO).orElse(null);
    		if(pyro.isRunning()) {
    			LOGGER.info("Click!");
        		event.setCanceled(true);
        		
        		BlockPos pos = event.getPos();
        		Direction dir = event.getFace();
        		Player p = event.getPlayer();
        		Level l = event.getWorld();
        		
        		Objectiu obj = new Objectiu(l, p, pos, dir);
        		
        		if(!pyro.hasObjectiu(obj)) {
        			pyro.addObjectiu(obj);
        			LOGGER.info(pos.toShortString());
        			LOGGER.info(dir.toString());
        		}
    		}
    	}
    }
    
}
