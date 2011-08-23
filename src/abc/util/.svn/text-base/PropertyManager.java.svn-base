package abc.util;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Properties;

public final class PropertyManager {

  private final static String DEFAULT_PROPERTIES_FILE = "ressources" + File.separator + "default.properties";
  private final static String APP_PROPERTIES_FILE = "ressources" + File.separator + "app.properties";

  private Properties defaultProps = new Properties();
  private Properties appProps = null;

  private Hashtable listeners = null;


  private static Object lock = new Object();
  private static PropertyManager instance  = null;

  private PropertyManager() {
  }

  public static PropertyManager getInstance() throws IOException {
	if (instance == null) {
		synchronized (lock) {
			if (instance == null) {
				instance = new PropertyManager();
				instance.loadProperties();
			}
		}
	}
	return (instance);

  }

  private void loadProperties()  throws IOException {

  	// create and load default properties
  	FileInputStream in = new FileInputStream(DEFAULT_PROPERTIES_FILE);
  	defaultProps.load(in);
  	in.close();

  	// create application properties with default
  	appProps = new Properties(defaultProps);

  	try {
	  // user/application properties
  	  in = new FileInputStream(APP_PROPERTIES_FILE);
  	  appProps.load(in);
  	  in.close();
    } catch (Throwable th) {
		// TODO: log something
	}

  }

  public void storeProperties() throws IOException {

    FileOutputStream out = new FileOutputStream(DEFAULT_PROPERTIES_FILE);
    defaultProps.store(out, "---Default properties---");
    out.close();


    out = new FileOutputStream(APP_PROPERTIES_FILE);
    appProps.store(out, "---App/User properties---");
    out.close();

  }

  public String getProperty(String key) {
	String val = null;
	val = (String)appProps.getProperty(key);
	if (val == null) {
		val = defaultProps.getProperty(key);
	}
	return (val);

  }

  /**
   * Sets Application/User String properties; default property values cannot be set.
   */
  public void setProperty(String key, String val) {

	ArrayList list  = null;
	Object oldValue = null;

	oldValue = getProperty(key);

	appProps.setProperty(key, val);
	if (listeners.containsKey(key)) {
	  list = (ArrayList)listeners.get(key);
	  int len = list.size();
	  if (len > 0) {
		PropertyChangeEvent evt = new PropertyChangeEvent(this, key, oldValue, val);
		for (int i=0; i < len; i++) {
		  if (list.get(i) instanceof PropertyChangeListener)
			((PropertyChangeListener)list.get(i)).propertyChange(evt);
		}
	  }
	}

  }

  public boolean addListener (String key, PropertyChangeListener listener) {
	boolean added = false;
	ArrayList list = null;
	if (listeners == null)
		listeners = new Hashtable();

	if (!listeners.contains(key)) {
		list = new ArrayList();
		added = list.add(listener);
		listeners.put(key, list);
    } else {
		list = (ArrayList)listeners.get(key);
		added = list.add(listener);
	}
	return (added);
  }

  public void removeListener (PropertyChangeListener listener) {
	if (listeners != null && listeners.size() > 0)
		listeners.remove(listener);
  }
  
}
