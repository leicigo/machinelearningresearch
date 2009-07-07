/*
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 2 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program; if not, write to the Free Software
 *    Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

/*
 *    SoftClassifiedFullInstance.java
 *    Copyright (C) 2003 Ray Mooney
 *
 */

package weka.core;

import java.util.*;
import java.io.*;

/**
 * An Instance that has a probability distribution across class values.
 * Particularly useful for EM using a SoftClassifier
 *
 * @author Ray Mooney (mooney@cs.utexas.edu)
*/

public class SoftClassifiedFullInstance extends Instance implements SoftClassifiedInstance{

    /** An array of probabilities giving the probability of each class
     * for this Instance */  
    protected double[] m_ClassDistribution;

    /**
     * Constructor that copies the attribute values and the weight from
     * the given instance and gives SoftInstance random class probabilities
     * generated by the given randomizer.
     *
   */
    public SoftClassifiedFullInstance(Instance instance, Random randomizer) {
	m_AttValues = instance.m_AttValues;
	m_Weight = instance.m_Weight;
	m_Dataset = instance.m_Dataset;
	m_ClassDistribution = randomClassDistribution(randomizer);
    }

    /** 
     * Constructor that copies the attribute values and the weight from
     * the given instance and gives SoftInstance random class probabilities
     * that assign all probability (1) to the instance's given class
     */
    public SoftClassifiedFullInstance(Instance instance) {
	m_AttValues = instance.m_AttValues;
	m_Weight = instance.m_Weight;
	m_Dataset = instance.m_Dataset;
	m_ClassDistribution = new double[classAttribute().numValues()];
	if(instance.classIsMissing())
	    for (int i = 0; i < classAttribute().numValues(); i++) {
		m_ClassDistribution[i] = 1.0/classAttribute().numValues();
	    }
	else
	m_ClassDistribution[(int)classValue()] = 1.0;
    }

    public SoftClassifiedFullInstance() {
    };

    /** Return a random class distribution */    
    protected double[] randomClassDistribution(Random randomizer) {
	double[] dist = new double[classAttribute().numValues()];
	for (int i = 0; i < classAttribute().numValues(); i++) {
	    dist[i] = randomizer.nextDouble();
	}
	Utils.normalize(dist);
	return dist;
    }
	
    /** Return the probability the instance is in the given class */
    public double getClassProbability (int classNum) {
	return m_ClassDistribution[classNum];
    }

    /** Set the probability the instance is in the given class */
    public void setClassProbability (int classNum, double prob) {
	m_ClassDistribution[classNum] = prob;
    }

    /** Get the class distribution for this instance */
    public double[] getClassDistribution () {
	return m_ClassDistribution;
    }

    /** Set the class distribution for this instance */
    public void setClassDistribution (double[] dist) throws Exception {
	if (dist.length != numClasses()) 
	    throw new Exception("Class distribution of incorrect length: " + dist);
	m_ClassDistribution = dist;
    }

  /**
   * Produces a shallow copy of this instance. The copy has
   * access to the same dataset. (if you want to make a copy
   * that doesn't have access to the dataset, use 
   * <code>new Instance(instance)</code>
   *
   * @return the shallow copy
   */
  public Object copy() {
    SoftClassifiedFullInstance result = new SoftClassifiedFullInstance();
    result.m_AttValues = m_AttValues;
    result.m_Weight = m_Weight;
    result.m_Dataset = m_Dataset;
    result.m_ClassDistribution = m_ClassDistribution;
    return result;
  }

}

    


