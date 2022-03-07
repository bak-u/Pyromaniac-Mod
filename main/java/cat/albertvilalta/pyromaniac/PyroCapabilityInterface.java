package cat.albertvilalta.pyromaniac;

import java.util.ArrayList;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface PyroCapabilityInterface extends INBTSerializable<CompoundTag> {
	
	boolean running = false;
    boolean readyToFire = false;
    static final ArrayList<Objectiu> blocs = new ArrayList<Objectiu>();	
		
	boolean isRunning();
	void setRunning(boolean running);
	
	boolean isReadyToFire();
	void setReadyToFire(boolean readyToFire);
	
	
	boolean hasObjectiu(Objectiu obj);
	void addObjectiu(Objectiu obj);
	ArrayList<Objectiu> getObjectius();
	
	void clearBlocs();	
}
