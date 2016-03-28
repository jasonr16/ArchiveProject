import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import chronoTimerMain.simulator.HardwareHandlerTest;
import chronoTimerMain.software.ChronoTimerEventHandler;
import chronoTimerMain.software.RaceIND;
import chronoTimerMain.software.RacePARIND;
import chronoTimerMain.software.Timer;
@RunWith(Suite.class)
@Suite.SuiteClasses({
   HardwareHandlerTest.class,
   Timer.class,
   ChronoTimerEventHandler.class,
   RacePARIND.class,
   RaceIND.class
})
// ChronoTimerEventHandler, RacePARIND, and RaceIND Tests must be run individually from each class - they aren't currently runnable through test suite
public class ChronoTestSuite {

}
