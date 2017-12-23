package connect.four.core.utility;

public class MoveInformation {
	public static final MoveInformation SINGLE_LOCATION = new MoveInformation(1, 1);

	private int immediateSequenceLength;
	private int maxPotentialSequenceLength;

	public MoveInformation(int immediateSequenceLength, int maxPotentialSequenceLength) {
		this.immediateSequenceLength = immediateSequenceLength;
		this.maxPotentialSequenceLength = maxPotentialSequenceLength;
	}

	public MoveInformation add(MoveInformation moveInfo) {
		return new MoveInformation(immediateSequenceLength + moveInfo.getImmediateSequenceLength(),
				maxPotentialSequenceLength + moveInfo.getMaxPotentialSequenceLength());
	}

	public int getImmediateSequenceLength() {
		return immediateSequenceLength;
	}

	public int getMaxPotentialSequenceLength() {
		return maxPotentialSequenceLength;
	}
}
