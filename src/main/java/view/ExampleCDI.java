package view;

import control.ExampleBean;

import javax.ejb.EJB;

public class ExampleCDI {

    @EJB
    private ExampleBean eb;

    public void createDB(){
        eb.createTabel();
    }
}
