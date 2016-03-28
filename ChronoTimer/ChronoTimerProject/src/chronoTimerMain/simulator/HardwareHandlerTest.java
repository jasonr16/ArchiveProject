package chronoTimerMain.simulator;

import static org.junit.Assert.*;
import org.junit.*;
public class HardwareHandlerTest {
	ChronoHardwareHandler test;
	
	@Before public void initialize() {
	}
	/**
	 * testPower() tests if the power turns when told to
	 */
	@Test public void testPower(){
		test = new ChronoHardwareHandler();
		//tests that the start is a turn on state
		assertTrue(test.power());
		//test flipping power using power
		assertTrue(!test.power());
		//test OFF() from OFF state
		test.OFF();
		assertTrue(test.power());//should flip it to ON state
		//test OFF() from on state
		test.OFF();
		assertTrue(test.power());//should flip it to ON state
		//test ON() from ON state
		test.ON();
		assertTrue(!test.power());
		//test on from Off state
		test.ON();
		assertTrue(!test.power());
	}
	
	/**
	 * testNoPower() tests that things that shouldn't work when power is
	 * off doesn't work ()
	 */
	@Test public void testNoPower(){
		test = new ChronoHardwareHandler();
		test.OFF();
		for( int i = 0; i < 3; i ++){
			
		}
		//test power from off
		test.OFF();
		test.inputFromSimulator("POWER", null, null);
		assertTrue(!test.power());
		
		//test power from on
		test.ON();
		test.inputFromSimulator("POWER", null, null);
		assertTrue(test.power());
		
		//test ON from off
		test.OFF();
		test.inputFromSimulator("ON", null, null);
		assertTrue(!test.power());
				
		//test ON from on
		test.ON();
		test.inputFromSimulator("ON", null, null);
		assertTrue(!test.power());
		
		//test OFF from off
		test.OFF();
		test.inputFromSimulator("OFF", null, null);
		assertTrue(test.power());
		//test OFF from on
		test.ON();
		test.inputFromSimulator("OFF", null, null);
		assertTrue(test.power());
		
		//test ON from off
		test.OFF();
		test.inputFromSimulator("ON", null, null);
		assertTrue(!test.power());
						
		//test ON from on
		test.ON();
		test.inputFromSimulator("ON", null, null);
		assertTrue(!test.power());
	}
}