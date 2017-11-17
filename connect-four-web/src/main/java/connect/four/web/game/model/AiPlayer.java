package connect.four.web.game.model;

import connect.four.core.IPlayer;

public class AiPlayer implements IPlayer {

	private String name;

	public AiPlayer(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

}
