package cat.albertvilalta.pyromaniac;

import java.util.Objects;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;


public class Objectiu {
	
	private Level level;
	private Player player;
	private BlockPos pos;
	private Direction dir;
	
	public Objectiu(Level level, Player player, BlockPos pos, Direction dir) {
		this.level = level;
		this.player = player;
		this.pos = pos;
		this.dir = dir;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public BlockPos getPos() {
		return pos;
	}

	public void setPos(BlockPos pos) {
		this.pos = pos;
	}

	public Direction getDir() {
		return dir;
	}

	public void setDir(Direction dir) {
		this.dir = dir;
	}

	@Override
    public boolean equals(Object o) {
		if(this == o)
			return true;
		if(o == null)
			return false;
		if(this.getClass() != o.getClass())
			return false;
		Objectiu obj = (Objectiu) o;
		return Objects.equals(this.getPos(), obj.getPos())
				&& Objects.equals(this.getDir(), obj.getDir())
				&& Objects.equals(this.getLevel(), obj.getLevel())
				&& Objects.equals(this.getPlayer(), obj.getPlayer());
    }


}