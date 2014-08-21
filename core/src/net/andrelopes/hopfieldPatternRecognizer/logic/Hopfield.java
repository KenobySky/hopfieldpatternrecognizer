package net.andrelopes.hopfieldPatternRecognizer.logic;

import java.util.logging.Level;
import java.util.logging.Logger;

/** @author André Vinícius Lopes */
public class Hopfield {

	private HopfieldNetwork network;

	public void train(boolean[] pattern) {
		network = new HopfieldNetwork(pattern.length);
		try {
			network.train(pattern);
		} catch(Exception ex) {
			Logger.getLogger(Hopfield.class.getName()).log(Level.SEVERE, null, ex);
		}
		System.out.println("Training Hopfield network with: " + formatBoolean(pattern));
	}

	public boolean[] present(boolean[] presentPattern) {
		System.out.println("Presenting Pattern to Hopfield network with: " + formatBoolean(presentPattern));
		boolean[] result = network.present(presentPattern);
		System.out.println("Result Pattern of the Hopfield network : " + formatBoolean(result));
		return result;
	}

	//I didnt change this method from Jeff heaton Book.
	public static String formatBoolean(final boolean b[]) {
		final StringBuilder result = new StringBuilder();
		result.append('[');
		for(int i = 0; i < b.length; i++) {
			if(b[i]) {
				result.append("T");
			} else {
				result.append("F");
			}
			if(i != b.length - 1) {
				result.append(",");
			}
		}
		result.append(']');
		return (result.toString());
	}

}
