package chronoTimerMain.simulator.hardwareHandler;

import static org.junit.Assert.*;
import org.junit.*;

import chronoTimerMain.simulator.Sensor;
public class HardwareHandlerTest {
	ChronoHardwareHandler test;

	ChronoHardwareHandler hh;
	@Before
	public void setUp() {
		test = new ChronoHardwareHandler();
		hh = new ChronoHardwareHandler();
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
	
	@Test
	public void testConn() {
		// check all channels are null
		Sensor[] sensors = hh.sensors;
		assertEquals(13, sensors.length);
		for(int i = 0; i < sensors.length; ++i) {
			assertEquals(null, sensors[i]);
		}
		
		// connect normal channel
		hh.conn("GATE", 1);
		assertEquals(null, hh.sensors[0]);
		assertTrue(hh.sensors[1] != null);
		
		// connect out-of-range channel
		assertEquals(-10, hh.conn("GATE", 15));
	}
	@Test
	public void testDisc() {
		// disconnect connected channel
		hh.conn("EYE", 1);
		assertEquals(1, hh.disc(1));
		assertTrue(hh.sensors[1] == null);
		
		// disconnect unconnected channel
		assertEquals(-10, hh.disc(1));
		assertTrue(hh.sensors[1] == null);
		
		// disconnect out-of-range channel
		assertEquals(-10, hh.disc(13));
	}
	@Test
	public void testToggle() {
		// toggle when power is off
		assertFalse(hh.toggle(1));
		// toggle when channel is connected
		hh.ON();
		hh.conn("GATE", 1);
		assertTrue(hh.toggle(1));
		assertTrue(hh.isEnabledSensor[1]);
		// toggle when channel is not connected
		assertFalse(hh.toggle(2));
		assertFalse(hh.isEnabledSensor[2]);
		// toggle out-of-range channel
		assertFalse(hh.toggle(-1));
	}
	@Test
	public void testNoPower(){
		hh = new ChronoHardwareHandler();
		hh.OFF();

		//test power from off
		hh.OFF();
		hh.inputFromSimulator("POWER", null, "");
		assertTrue(!hh.power());
		
		//test power from on
		hh.ON();
		hh.inputFromSimulator("POWER", null,"");
		assertTrue(hh.power());
		
		//test ON from off
		hh.OFF();
		hh.inputFromSimulator("ON", null,"");
		assertTrue(!hh.power());
				
		//test ON from on
		hh.ON();
		hh.inputFromSimulator("ON", null, "");
		assertTrue(!hh.power());
		
		//test OFF from off
		hh.OFF();
		hh.inputFromSimulator("OFF", null, "");
		assertTrue(hh.power());
		//test OFF from on
		hh.ON();
		hh.inputFromSimulator("OFF", null, "");
		assertTrue(hh.power());
		
		//test ON from off
		hh.OFF();
		hh.inputFromSimulator("ON", null, "");
		assertTrue(!hh.power());
						
		//test ON from on
		hh.ON();
		hh.inputFromSimulator("ON", null,"");
		assertTrue(!hh.power());
		
		//test toggle when power of OFF
		hh.OFF();
		String[] test = {"1"};
		String[] test2 = {"EYE","2"};
		hh.conn("GATE", 1);
		hh.inputFromSimulator("TOGGLE", test , "");
		assertFalse(hh.isEnabledSensor[1]);
		//test toggle when power is ON
		hh.ON();
		hh.inputFromSimulator("TOGGLE", test , "");
		assertTrue(hh.isEnabledSensor[1]);
		// toggle when channel is not connected
		hh.inputFromSimulator("TOGGLE", test2 , "");
		assertFalse(hh.isEnabledSensor[2]);
		
		// disconnect connected channel when off
		hh.OFF();
		hh.inputFromSimulator("DISC", test, "");
		assertFalse(hh.sensors[1] == null);
		// disconnect connected channel when on
		hh.ON();
		hh.inputFromSimulator("DISC", test, "");
		assertTrue(hh.sensors[1] == null);
		
		//test conn when off
		hh.OFF();
		hh.inputFromSimulator("CONN", test2, "");
		assertEquals(null, hh.sensors[2]);
		//test conn when on
		hh.ON();
		hh.inputFromSimulator("CONN", test2,"");
		assertTrue(hh.sensors[2] != null);
	}	
}	