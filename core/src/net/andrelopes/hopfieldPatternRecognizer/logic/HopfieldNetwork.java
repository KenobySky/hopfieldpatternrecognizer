/**
 * Introduction to Neural Networks with Java, 2nd Edition Copyright 2008 by
 * Heaton Research, Inc. http://www.heatonresearch.com/books/java-neural-2/
 *
 * ISBN13: 978-1-60439-008-7 ISBN: 1-60439-008-5
 *
 * This class is released under the: GNU Lesser General Public License (LGPL)
 * http://www.gnu.org/copyleft/lesser.html
 */
package net.andrelopes.hopfieldPatternRecognizer.logic;

import cookie.Matrixes.DoubleMatrix;
import net.andrelopes.hopfieldPatternRecognizer.utils.BipolarUtilities;
import net.andrelopes.hopfieldPatternRecognizer.utils.ExtraMatrixUtils;

public class HopfieldNetwork {

    /**
     * The weight matrix for this neural network. A Hopfield neural network is a
     * single layer, fully connected neural network.
     *
     * The inputs and outputs to/from a Hopfield neural network are always
     * boolean values.
     */
    private DoubleMatrix weightMatrix;
    public boolean trained = false;

    public HopfieldNetwork(final int size) {
        this.weightMatrix = new DoubleMatrix(size, size);

    }

    /**
     * @return the weight matrix for this neural network.
     */
    public DoubleMatrix getMatrix() {
        return this.weightMatrix;
    }

    /**
     * @return the size of this neural network.
     */
    public int getSize() {
        return this.weightMatrix.numRows;
    }

    /**
     * Present a pattern to the neural network and receive the result.
     *
     * @param pattern The pattern to be presented to the neural network.
     * @return The output from the neural network.
     */
    public boolean[] present(final boolean[] pattern) {
        final boolean output[] = new boolean[pattern.length];

        // convert the input pattern into a matrix with a single row.
        // also convert the boolean values to bipolar(-1=false, 1=true)
        final DoubleMatrix inputMatrix = BipolarUtilities.bipolar2double(pattern);

        // Process each value in the pattern
        for (int col = 0; col < pattern.length; col++) {
            //SimpleMatrix columnMatrix = this.weightMatrix.getCol(col);
            DoubleMatrix columnMatrix = ExtraMatrixUtils.getCol(weightMatrix, col);

            //columnMatrix = MatrixMath.transpose(columnMatrix);
            columnMatrix = columnMatrix.transpose(columnMatrix);

            // The output for this input element is the dot product of the
            // input matrix and one column from the weight matrix.
            //final double dotProduct = MatrixMath.dotProduct(inputMatrix,columnMatrix);
            //               columnMatrix = columnMatrix.transpose();
            //            final double dotProduct = inputMatrix.dot(columnMatrix);
            double dotProduct = 0;
            for (int i = 0; i < inputMatrix.numCols; i++) {
                dotProduct = inputMatrix.getValueAt(0, i) * columnMatrix.getValueAt(0, i);
            }

            // Convert the dot product to either true or false.
            output[col] = dotProduct > 0;
        }

        return output;
    }

    public void train(final boolean[] pattern) throws Exception {
        if (pattern.length != this.weightMatrix.numRows) {
            throw new Exception("Can't train a pattern of size " + pattern.length + " on a hopfield network of size " + this.weightMatrix.numRows);
        }

        //Create a row matrix from the input, convert boolean to bipolar
        //final SimpleMatrix m2 = SimpleMatrix.createRowMatrix(BipolarUtilities.bipolar2double(pattern));
        DoubleMatrix m2 = BipolarUtilities.bipolar2double(pattern);

        // Transpose the matrix and multiply by the original input matrix
        DoubleMatrix m1 = m2.transpose(m2);
        DoubleMatrix m3 = m1.multiply(m1, m2);

        // matrix 3 should be square by now, so create an identity
        // matrix of the same size.
        //final SimpleMatrix identity = MatrixMath.identity(m3.getRows());
        DoubleMatrix identity = new DoubleMatrix(m3.numRows, m3.numCols);
        for (int i = 0; i < identity.numRows; i++) {
            for (int j = 0; j < identity.numCols; j++) {
                if (i == j) {
                    identity.setValueAt(i, j, (short) 1);
                } else {
                    identity.setValueAt(i, j, (short) 0);
                }
            }
        }

        // subtract the identity matrix
        //final Matrix m4 = MatrixMath.subtract(m3, identity);
        //Fm1 Size9;9
        //Fm2 Size9;1
        //M3 :10000;10000
        //M5 :10000;10000
        System.out.println(" M3 :" + m3.numRows + ";" + m3.numCols);
        System.out.println(" M5 :" + identity.numRows + ";" + identity.numCols);

        final DoubleMatrix m4 = m3.minus(m3, identity);

        // now add the calculated matrix, for this pattern, to the
        // existing weight matrix.
        //this.weightMatrix = MatrixMath.add(this.weightMatrix, m4);
        weightMatrix = weightMatrix.add(weightMatrix, m4);
        trained = true;
    }

    public boolean isTrained() {
        return trained;
    }

}
