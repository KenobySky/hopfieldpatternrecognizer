package net.andrelopes.hopfieldPatternRecognizer.utils;

import cookie.Matrixes.DoubleMatrix;

/**
 *
 * @author André Vinícius Lopes
 */
public class ExtraMatrixUtils {

	public static DoubleMatrix getCol(DoubleMatrix matrix, int col) {
		DoubleMatrix result = new DoubleMatrix(matrix.numRows, 1);
		for (int row = 0; row < result.numRows; row++) {
			result.setValueAt(row, 0, matrix.getValueAt(row, col));
		}
		return result;
	}

}