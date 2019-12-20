package connect.four.core;

import connect.four.core.exception.InvalidGridLocationException;

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
		try {
			// X|O|O|-|-|-|-
			for (int row = GameProperties.TOP_ROW_INDEX; row >= 0; row--) {
				for (int col = 0; col < GameProperties.COLS; col++) {
					if (col > 0) {
						System.out.print('|');
					}
					String checker = game.getGrid().getChecker(row, col);
					if (checker == null) {
						System.out.print('-');
					} else {
						System.out.print(checker);
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
		} catch (InvalidGridLocationException e) {
			e.printStackTrace();
		}
	}
}
