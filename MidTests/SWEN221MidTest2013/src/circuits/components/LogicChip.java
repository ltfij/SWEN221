package circuits.components;

import circuits.wiring.Pin;

public class LogicChip implements Component {

    private LogicGate[] gates;
    
    public LogicChip(LogicGate[] gates) {
        this.gates = gates;
    }

    @Override
    public int getNumberOfPins() {
        return gates.length * 3;
    }

    @Override
    public Pin getPin(int pin) {
        
        if (pin < 0 || pin >= gates.length * 3) {
            throw new IllegalArgumentException("invalid pin requested");
        }
        
        int gateNo = pin / 3;
        int pinNo = pin % 3;
        
        return gates[gateNo].getPin(pinNo);
        
    }

    @Override
    public int findPin(Pin pin) {
        
        for (int i = 0; i < gates.length; i++) {
            LogicGate lgate = gates[i];
            for (int j = 0; j < lgate.getNumberOfPins(); j++) {
                if (lgate.getPin(j).equals(pin)) {
                    return i*3 +j; 
                }
            }
        }
        
        return -1; // not contained
    }

    @Override
    public boolean isInputPin(int pin) {
        return pin % 3 == 0 || pin % 3 == 1;
    }

    @Override
    public boolean isOutputPin(int pin) {
        return pin % 3 == 2;
    }

    @Override
    public void clock() {
        
        for (int i = 0; i < gates.length; i++) {
            LogicGate lgate = gates[i];
            lgate.clock();
        }
    }

}
