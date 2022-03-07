package cat.albertvilalta.pyromaniac;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;

public class PyroCapability{

	public static final Capability<PyroCapabilityInterface> PYRO = CapabilityManager.get(new CapabilityToken<>() {});
	
	public static void register(RegisterCapabilitiesEvent event) {
		event.register(PyroCapabilityInterface.class);
	}
	
	private PyroCapability() {
		
	}
	
	
}
