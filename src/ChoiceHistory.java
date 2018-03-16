

import java.util.LinkedList;

public class ChoiceHistory {
    private LinkedList<Integer> choiceHistoryList;
    private int choiceHistoryLength;

    public ChoiceHistory(int choiceHistoryLength) {
        this.choiceHistoryList = new LinkedList<>();
        this.choiceHistoryLength = choiceHistoryLength;
    }

    public void updateChoiceHistory(int i) {
        if (this.choiceHistoryList.size() < this.choiceHistoryLength) {
            this.choiceHistoryList.add(i);
        } else {
            this.choiceHistoryList.removeFirst();
            this.choiceHistoryList.add(i);
        }
    }

    public LinkedList<Integer> getPreviousMChoices() throws InsufficientHistoryException {
        if (this.choiceHistoryList.size() < this.choiceHistoryLength) {
            throw new InsufficientHistoryException();
        }
        return this.choiceHistoryList;
    }

    public boolean isShorterThanM() {
        return this.choiceHistoryList.size() < this.choiceHistoryLength;
    }

    public int getChoiceHistoryLength() {
        return this.choiceHistoryLength;
    }
}
