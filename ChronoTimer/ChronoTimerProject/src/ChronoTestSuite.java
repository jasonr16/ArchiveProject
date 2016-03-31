import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import chronoTimerMain.simulator.hardwareHandler.HardwareHandlerTest;
import chronoTimerMain.software.RaceIND;
import chronoTimerMain.software.RacePARIND;
import chronoTimerMain.software.Timer.Timer;
import chronoTimerMain.software.Timer.TimerTest;
import chronoTimerMain.software.eventHandler.ChronoTimerEventHandler;
import chronoTimerMain.software.eventHandler.ChronoTimerEventHandlerTest;
@RunWith(Suite.class)
@Suite.SuiteClasses({
   HardwareHandlerTest.class,
   TimerTest.class,
   ChronoTimerEventHandlerTest.class, //Will not run automated! Run test class by itself.
   RacePARIND.class, //Will not run automated! Run test class by itself.
   RaceIND.class //Will not run automated! Run test class by itself.
})
// ChronoTimerEventHandler, RacePARIND, and RaceIND Tests must be run individually from each class - they aren't currently runnable through test suite
public class ChronoTestSuite {

}
