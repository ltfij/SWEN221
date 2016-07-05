package circuits.wiring;

/**
 * A pin represents a special position in a circuit which connects wires and
 * components together. Every pin has a current value, which can be read or
 * written to.  
 * 
 * @author David J. Pearce
 * 
 */
public class Pin extends Joint {
	private boolean value;
	
	public Pin(int x, int y, boolean value) {
		super(x,y);
		this.value = value;
	}
	
	public boolean read() {
		return value;
	}
	
	public void write(boolean value) {
		this.value = value;
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (value ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Pin other = (Pin) obj;
        if (value != other.value)
            return false;
        return true;
    }
	

}
