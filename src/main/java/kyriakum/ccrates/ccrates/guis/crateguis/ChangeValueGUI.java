package kyriakum.ccrates.ccrates.guis.crateguis;

import kyriakum.ccrates.ccrates.CCrates;
import kyriakum.ccrates.ccrates.entities.Crate;

public class ChangeValueGUI {


    private Crate crate;
    private CCrates cCrates;
    private ValueType type;

    public ChangeValueGUI(CCrates cCrates, Crate crate, ValueType type){
        this.cCrates = cCrates;
        this.crate = crate;
        this.type = type;
    }


    public void accept(){
        //TODO
        // Change value of each Setting according to ValueType
    }
}
