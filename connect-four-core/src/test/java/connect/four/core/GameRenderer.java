package connect.four.core;

public class GameRenderer {

	// -|-|-|-|-|-|-
	// -|-|-|-|-|-|-
	// -|-|-|-|-|-|-
	// -|-|-|-|-|-|-
	// -|X|-|-|-|-|-
	// X|O|O|-|-|-|-
	// -------------
	// 0|1|2|3|4|5|6

	public static void renderGame(IGame game) {
		// X|O|O|-|-|-|-
		for (int row = GameProperties.ROWS - 1; row >= 0; row--) {
			for (int col = 0; col < GameProperties.COLS; col++) {
				if (col > 0) {
					System.out.print('|');
				}
				Checker checker = game.getGrid().getChecker(row, col);
				if (checker == null) {
					System.out.print('-');
				} else {
					Player player = (Player) checker.getOwner();
					System.out.print(player.getToken());
				}
			}
			System.out.println();
		}

		// -------------
		for (int i = 0; i < GameProperties.COLS * 2 - 1; i++) {
			System.out.print('-');
		}
		System.out.println();

		// 0|1|2|3|4|5|6
		for (int col = 0; col < GameProperties.COLS; col++) {
			if (col > 0) {
				System.out.print('|');
			}
			System.out.print(col);
		}
		System.out.println();
	}
}
