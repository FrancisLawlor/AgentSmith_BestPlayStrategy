

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import strategies.core.IStrategy;

public class ImplementedStrategy implements IStrategy {
	private static final String WINNING_CHOICE = "winning_choice";
	private final String STRATEGY_NAME = "Best Play";
	private final String[] ADDITIONAL_PARAMETER_NAMES = {"number_of_choices", "history_length"};	
	
	private ChoiceHistory choiceHistory;
	private int[] strategyVector;
	private int numberOfChoices;

	public ImplementedStrategy(HashMap<String, String> additionalParameters) {
		if (additionalParameters == null) {
			return;
		}
		
		this.numberOfChoices = Integer.parseInt(additionalParameters.get(ADDITIONAL_PARAMETER_NAMES[0]));
		this.choiceHistory = new ChoiceHistory(Integer.parseInt(additionalParameters.get(ADDITIONAL_PARAMETER_NAMES[1])));
		this.strategyVector = generateStrategyVector(choiceHistory.getChoiceHistoryLength());
	}

	private int[] generateStrategyVector(int m) {
		int sizeOfStrategyVector = (int) Math.pow(this.numberOfChoices, m);
		int[] strategyVector = new int[sizeOfStrategyVector];

		for (int i = 0; i < sizeOfStrategyVector; i++) {
			int randomChoice = (int) (Math.random() * this.numberOfChoices);

			strategyVector[i] = randomChoice;
		}

		return strategyVector;
	}

	@Override
	public int generateChoice(HashMap<String, Object> strategyResources) {
		if (this.choiceHistory.isShorterThanM()) {
			int randomChoice = (int) (Math.random() * this.numberOfChoices);
			
			return randomChoice;
		} else {
			LinkedList<Integer> previousMChoices = null;
			
			try {
				previousMChoices = choiceHistory.getPreviousMChoices();
			} catch (InsufficientHistoryException e) {
				e.printStackTrace();
			}

			int strategyIndex = 0;
			
			// n-ary mapping to array index
			for (int i = 0; i < this.choiceHistory.getChoiceHistoryLength(); i++) {
				strategyIndex += this.strategyVector[this.choiceHistory.getChoiceHistoryLength() - 1 - i] * Math.pow(this.numberOfChoices, i);
			}
			
			return this.strategyVector[strategyIndex];
		}
	}
	
	public void updateStrategy(String key, int value) {
		if (key.equals(WINNING_CHOICE)) {
			this.choiceHistory.updateChoiceHistory(value);
		}
	}

	@Override
	public byte[] getIconAsBytes() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getStrategyName() {
		return this.STRATEGY_NAME;
	}

	@Override
	public String[] getAdditionalParameterNames() {
		return this.ADDITIONAL_PARAMETER_NAMES;
	}
}
