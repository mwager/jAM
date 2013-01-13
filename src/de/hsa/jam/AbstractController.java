package de.hsa.jam;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import de.hsa.jam.audio.AbstractModel;
import de.hsa.jam.ui.AbstractView;

/**
 * This class provides base level functionality for each controller. This
 * includes the ability to register multiple models and views, propogating model
 * change events to each of the views, and providing a utility function to
 * broadcast model property changes when necessary.
 * 
 * @author Robert Eckstein
 */
public abstract class AbstractController implements PropertyChangeListener {

	// Vectors that hold a list of the registered models and views for this
	// controller.

	private ArrayList<AbstractView> registeredViews;
	private ArrayList<AbstractModel> registeredModels;

	/** Creates a new instance of Controller */
	public AbstractController() {
		registeredViews = new ArrayList<AbstractView>();
		registeredModels = new ArrayList<AbstractModel>();
	}

	/**
	 * Binds a model to this controller. Once added, the controller will listen
	 * for all model property changes and propogate them on to registered views.
	 * In addition, it is also responsible for resetting the model properties
	 * when a view changes state.
	 * 
	 * @param model
	 *            The model to be added
	 */
	public void addModel(AbstractModel model) {
		registeredModels.add(model);
		model.addPropertyChangeListener(this);
	}

	/**
	 * Unbinds a model from this controller.
	 * 
	 * @param model
	 *            The model to be removed
	 */
	public void removeModel(AbstractModel model) {
		registeredModels.remove(model);
		model.removePropertyChangeListener(this);
	}

	/**
	 * Binds a view to this controller. The controller will propogate all model
	 * property changes to each view for consideration.
	 * 
	 * @param view
	 *            The view to be added
	 */
	public void addView(AbstractView view) { /* AbstractViewPanel view */
		registeredViews.add(view);
	}

	/**
	 * Unbinds a view from this controller.
	 * 
	 * @param view
	 *            The view to be removed
	 */
	public void removeView(AbstractView view) { /* AbstractViewPanel view */
		registeredViews.remove(view);
	}

	// Used to observe property changes from registered models and propogate
	// them on to all the views.
	/**
	 * This method is used to implement the PropertyChangeListener interface.
	 * Any model changes will be sent to this controller through the use of this
	 * method.
	 * 
	 * @param evt
	 *            An object that describes the model's property change.
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		// System.out.println("AbstractController: propertyChange() -> ComponentUpdate: "
		// + evt.getPropertyName());

		for (AbstractView view : registeredViews) {
			view.modelPropertyChange(evt);
		}
	}

	/**
	 * Convienence method that subclasses can call upon to fire off property
	 * changes back to the models. This method used reflection to inspect each
	 * of the model classes to determine if it is the owner of the property in
	 * question. If it isn't, a NoSuchMethodException is throws (which the
	 * method ignores).
	 * 
	 * @param propertyName
	 *            The name of the property
	 * @param newValue
	 *            An object that represents the new value of the property.
	 */
	protected void setModelProperty(String propertyName, Object newValue) {
		// System.out.println("AbstractController: setModelProperty()" +
		// newValue);
		for (AbstractModel model : registeredModels) {
			try {
				// Method method =
				// model.getClass().getMethod("set"+propertyName, new Class[]
				// {newValue.getClass()});

				Method method = model.getClass().getMethod(propertyName,
						new Class[] { newValue.getClass() });
				// Class [] klasse = newValue==null ? new Class[] {null} : new
				// Class[] {newValue.getClass()};
				// Method method = model.getClass().getMethod(propertyName,
				// klasse);
				method.invoke(model, newValue);

			} catch (InvocationTargetException ex) { // d.h. dass exception in
														// der aufgerufenen
														// methoden ausgeloest
														// wurde!
				ex.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
}