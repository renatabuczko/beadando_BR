package model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CostPlannerCalculatorTest {

    private CostPlannerCalculator undertest;

    @BeforeEach
    void setUp() { undertest = new CostPlannerCalculator();}

    @AfterEach
    void tearDown() {
    }


    //String int-tel:
    @Test
    void calculateSaving() {
        assertEquals("100", undertest.calculateSaving("150","10","10","10", "10","10"));
    }
    //String int-tel:
    @Test
    void addAllCosts() {
        assertEquals("2315", undertest.addAllCosts("1315","200","350","300","150"));
    }
    //String float-tal:
    @Test
    void percentPickedCost() throws InvalidDivisionException {
        assertEquals("7,45", undertest.percentPickedCost("150","123","555","423","763"));
    }
    @Test
    void percentPickedCostDividedByZero(){
        assertThrows(InvalidDivisionException.class,
                () -> undertest.percentPickedCost("0","0", "0","0", "0"));
        assertThrows(InvalidDivisionException.class,
                () -> undertest.percentPickedCost("-10","0", "10","0", "0"));
    }

}