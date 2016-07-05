package circuits.components;

import circuits.CircuitBoard;
import circuits.wiring.Pin;

public class RomChip implements Component {

    private Pin[] pins;
    private boolean[][] memory;
    
    public RomChip(Pin[] pins, boolean[][] memory) {
        this.pins = pins;
        this.memory = memory;
    }

    @Override
    public int getNumberOfPins() {
        return 6;
    }

    @Override
    public Pin getPin(int pin) {
        
        if (pin >= 0 && pin < 6) {
            return pins[pin];
        }
        
        throw new IllegalArgumentException("invalid pin requested");
    }

    @Override
    public int findPin(Pin pin) {
        
        for (int i = 0; i < pins.length; i++) {
            if (pins[i].equals(pin)) {
                return i;
            }
        }
        
        return -1;
    }

    @Override
    public boolean isInputPin(int pin) {
        if (pin >= 0 && pin < 3) {
            return true;
        } else if (pin > 2 && pin < 6) {
            return false;
        }
        
        throw new IllegalArgumentException("invalid pin requested");
    }

    @Override
    public boolean isOutputPin(int pin) {
        if (pin >= 0 && pin < 3) {
            return false;
        } else if (pin > 2 && pin < 6) {
            return true;
        }
        
        throw new IllegalArgumentException("invalid pin requested");
    }

    @Override
    public void clock() {
        
        boolean[] addressBus = {pins[0].read(), pins[1].read(), pins[2].read()};
        int i = CircuitBoard.encode(addressBus);
        
        boolean[] data = memory[i];
        
        for (int j = 0; j < data.length; j++) {
            pins[j+3].write(data[j]);
        }
    }

}
