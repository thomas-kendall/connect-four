package connect.four.core;

public class Checker {
	private IPlayer owner;

	public Checker(IPlayer owner) {
		this.owner = owner;
	}

	public IPlayer getOwner() {
		return owner;
	}
}
