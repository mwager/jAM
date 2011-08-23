package de.hsa.jam.ui;

import java.beans.PropertyChangeEvent;

import javax.swing.JComponent;

/**
 * This class provides the base level abstraction for views in this example. All
 * views that extend this class also extend JPanel (which is useful for
 * performing GUI manipulations on the view in NetBeans Matisse), as well as
 * providing the modelPropertyChange() method that controllers can use to
 * propogate model property changes.
 */
public abstract class AbstractView /* extends JComponent */{

	/**
	 * Called by the controller when it needs to pass along a property change
	 * (==Component Update) from a model.
	 * 
	 * Alle Views müssen von hier ableiten und diese Methode überschreiben.
	 * 
	 * @param evt
	 *            The property change event from the model
	 */
	public abstract void modelPropertyChange(PropertyChangeEvent evt);
}