package cat.albertvilalta.pyromaniac;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PyroCapabilityAttacher {

	private static class PyroCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
		
		public static final ResourceLocation IDENTIFIER = new ResourceLocation("pyromaniac", "pyro");
		
		private final PyroCapabilityInterface backend = new PyroCapabilityImplementation();
        private final LazyOptional<PyroCapabilityInterface> optionalData = LazyOptional.of(() -> backend);

		
		void invalidate() {
            this.optionalData.invalidate();
        }

		@Nonnull
		@Override
		public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
			return PyroCapability.PYRO.orEmpty(cap, this.optionalData);
		}

		@Override
		public CompoundTag serializeNBT() {
			return this.backend.serializeNBT();
		}

		@Override
		public void deserializeNBT(CompoundTag nbt) {
			this.backend.deserializeNBT(nbt);
			
		}
	}

	@SubscribeEvent
	public static void attach(final AttachCapabilitiesEvent<Entity> event) {
		if(event.getObject() instanceof Player) {
			final PyroCapabilityProvider provider = new PyroCapabilityProvider();
			
			event.addCapability(PyroCapabilityProvider.IDENTIFIER, provider);
			event.addListener(provider::invalidate);
		}
	}

    private PyroCapabilityAttacher() {
    }

}
