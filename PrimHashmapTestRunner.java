import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/*
 * Author: @tgopal - Tejas Gopal, KPCB Engineering Fellow Applicant
 * 
 * Class PrimHashmapTestRunner will simply serve as a wrapper class for the 
 * execution of all the unit tests defined in PrimHashmapTest.java.
 */

public class PrimHashmapTestRunner {

    public static void main(String[] args) throws ClassNotFoundException {

    	System.out.println("\nNow running tests for PrimHashmap...\n");

        Result result = new JUnitCore().runClasses(PrimHashmapTest.class);

        for (Failure fail : result.getFailures()) {
        	System.err.println("Failure detected! -> " + fail.getDescription());
        }

        if (result.getFailureCount() == 0) {
            System.out.println("All tests passed successfully - good job! :)");
        }
        System.out.println("Test execution completed.\n");
    }
}
