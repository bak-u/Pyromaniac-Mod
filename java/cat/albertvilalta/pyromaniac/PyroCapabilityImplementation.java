package cat.albertvilalta.pyromaniac;

import static cat.albertvilalta.pyromaniac.Constants.LOGGER;
import static cat.albertvilalta.pyromaniac.Constants.PREFIX;

import java.util.ArrayList;

import net.minecraft.nbt.CompoundTag;

public class PyroCapabilityImplementation implements PyroCapabilityInterface {

	private boolean running = false;
    private boolean readyToFire = false;
    private ArrayList<Objectiu> blocs;	

	@Override
	public CompoundTag serializeNBT() {
		LOGGER.info(PREFIX+"SERIALIZE NBT");

		final CompoundTag tag = new CompoundTag();
		
		tag.putBoolean("RUNNING", this.running);
		tag.putBoolean("READYTOFIRE", this.readyToFire);
		
		// TODO
		// Serialize class Objectiu
		
		return tag;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		LOGGER.info(PREFIX+"DESERIALIZE NBT");

		this.running = nbt.getBoolean("RUNNING");
		this.readyToFire = nbt.getBoolean("READYTOFIRE");
				
		// TODO
		// Deserialize class Objectiu
		
	}

	@Override
	public boolean isRunning() {
		return running;
	}

	@Override
	public void setRunning(boolean running) {
		this.running = running;
	}

	@Override
	public boolean isReadyToFire() {
		return readyToFire;
	}

	@Override
	public void setReadyToFire(boolean readyToFire) {
		this.readyToFire = readyToFire;
	}

	@Override
	public boolean hasObjectiu(Objectiu obj) {
		return this.blocs.contains(obj);
	}

	@Override
	public void addObjectiu(Objectiu obj) {
		this.blocs.add(obj);
	}

	@Override
	public void clearBlocs() {
		this.blocs = new ArrayList<Objectiu>();	
	}

	@Override
	public ArrayList<Objectiu> getObjectius() {
		return this.blocs;
	}

}
