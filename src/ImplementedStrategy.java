

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import strategies.core.IStrategy;

public class ImplementedStrategy implements IStrategy {
	private static final String WINNING_CHOICE = "winning_choice";
	private final String STRATEGY_NAME = "Best Play";
	private final String[] ADDITIONAL_PARAMETER_NAMES = {"number_of_choices", "history_length"};	
	
	private ChoiceHistory choiceHistory;
	private Map<Integer, int[]> strategyVectors = new HashMap<>();
	private int numberOfChoices;

	public ImplementedStrategy(HashMap<String, String> additionalParameters) {
		if (additionalParameters == null) {
			return;
		}
		
		this.numberOfChoices = Integer.parseInt(additionalParameters.get(ADDITIONAL_PARAMETER_NAMES[0]));
		this.choiceHistory = new ChoiceHistory(Integer.parseInt(additionalParameters.get(ADDITIONAL_PARAMETER_NAMES[1])));
		this.strategyVectors.put(this.numberOfChoices, generateStrategyVector(choiceHistory.getChoiceHistoryLength(), this.numberOfChoices));
	}

	private int[] generateStrategyVector(int m, int _numberOfChoices) {
		int sizeOfStrategyVector = (int) Math.pow(_numberOfChoices, m);
		int[] strategyVector = new int[sizeOfStrategyVector];

		for (int i = 0; i < sizeOfStrategyVector; i++) {
			int randomChoice = (int) (Math.random() * _numberOfChoices);

			strategyVector[i] = randomChoice;
		}

		return strategyVector;
	}

	@Override
	public int generateChoice(HashMap<String, String> strategyResources) {
		this.numberOfChoices = Integer.parseInt(strategyResources.get(ADDITIONAL_PARAMETER_NAMES[0]));
		
		if (!strategyVectors.containsKey(this.numberOfChoices)) {
			this.strategyVectors.put(this.numberOfChoices, generateStrategyVector(choiceHistory.getChoiceHistoryLength(), this.numberOfChoices));
		}
		
		if (this.choiceHistory.isShorterThanM()) {
			int randomChoice = (int) (Math.random() * this.numberOfChoices);
			
			return randomChoice;
		} else {
			int strategyIndex = 0;
			
			for (int i = 0; i < this.choiceHistory.getChoiceHistoryLength(); i++) {
				strategyIndex += this.strategyVectors.get(this.numberOfChoices)[this.choiceHistory.getChoiceHistoryLength() - 1 - i] * Math.pow(this.numberOfChoices, i);
			}
			
			return this.strategyVectors.get(this.numberOfChoices)[strategyIndex];
		}
	}
	
	public void updateStrategy(String key, int value) {
		if (key.equals(WINNING_CHOICE)) {
			this.choiceHistory.updateChoiceHistory(value);
		}
	}

	@Override
	public byte[] getIconAsBytes() throws IOException {
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
