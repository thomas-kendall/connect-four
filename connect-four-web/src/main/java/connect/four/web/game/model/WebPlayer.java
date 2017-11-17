package connect.four.web.game.model;

import connect.four.core.IPlayer;

public class WebPlayer implements IPlayer {

	private String name;

	public WebPlayer(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

}
