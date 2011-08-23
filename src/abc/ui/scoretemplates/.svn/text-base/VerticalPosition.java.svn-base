/**
 * 
 */
package abc.ui.scoretemplates;

/**
 * @author Administrateur
 *
 */
public abstract class VerticalPosition {

	public static final byte TOP = 1;
	public static final byte BOTTOM = 2;
	public static final byte MIDDLE = 3;
	public static final byte ABOVE_STAFF = 4;
	public static final byte UNDER_STAFF = 5;
	
	public static String toString(byte v) {
		switch (v) {
		case TOP: return "top";
		case BOTTOM: return "bottom";
		case MIDDLE: return "middle";
		case ABOVE_STAFF: return "above staff";
		case UNDER_STAFF: return "under staff";
		default: return "<unknown vertical align>";
		}
	}
	
	public static byte toVerticalAlign(String s) {
		s = s.trim().toLowerCase();
		if (s.equals("top")) return TOP;
		else if (s.equals("bottom")) return BOTTOM;
		else if (s.equals("middle")) return MIDDLE;
		else if (s.equals("above staff")) return ABOVE_STAFF;
		else if (s.equals("under staff")) return UNDER_STAFF;
		else throw new RuntimeException("unknown vertical align: "+s);
	}
}
