package strategies.core;


import java.io.IOException;
import java.util.HashMap;

public interface IStrategy {
	public int generateChoice(HashMap<String, String> strategyResources);
	public void updateStrategy(String key, int value);
	public byte[] getIconAsBytes() throws IOException;
	public String getStrategyName();
	public String[] getAdditionalParameterNames();
}
