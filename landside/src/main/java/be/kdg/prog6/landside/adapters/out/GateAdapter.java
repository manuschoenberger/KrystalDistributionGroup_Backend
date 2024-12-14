package be.kdg.prog6.landside.adapters.out;

import be.kdg.prog6.landside.ports.out.GateControlPort;
import org.springframework.stereotype.Component;

@Component
public class GateAdapter implements GateControlPort {

    public void controlGate() {
        System.out.println("Gate control activated");
    }
}
