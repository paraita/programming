/*
 * ################################################################
 *
 * ProActive: The Java(TM) library for Parallel, Distributed,
 *            Concurrent computing with Security and Mobility
 *
 * Copyright (C) 1997-2009 INRIA/University of Nice-Sophia Antipolis
 * Contact: proactive@ow2.org
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; version 3 of
 * the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 * USA
 *
 * If needed, contact us to obtain a release under GPL version 2 of
 * the License.
 *
 *  Initial developer(s):               The ProActive Team
 *                        http://proactive.inria.fr/team_members.htm
 *  Contributor(s):
 *
 * ################################################################
 * $$PROACTIVE_INITIAL_DEV$$
 */
package functionalTests.component.collectiveitf.gathercast;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.objectweb.fractal.adl.Factory;
import org.objectweb.fractal.api.Component;
import org.objectweb.fractal.util.Fractal;
import org.objectweb.proactive.core.util.wrapper.BooleanWrapper;

import functionalTests.ComponentTest;


public class Test extends ComponentTest {

    /**
     *
     */
    public static final String MESSAGE = "-Main-";
    public static final int NB_CONNECTED_ITFS = 2;
    public static final String VALUE_1 = "10";
    public static final String VALUE_2 = "20";

    public Test() {
        super("Gather interfaces", "Gather interfaces");
    }

    /*
     * @see testsuite.test.FunctionalTest#action()
     */
    @org.junit.Test
    public void action() throws Exception {
        Factory f = org.objectweb.proactive.core.component.adl.FactoryFactory.getFactory();
        Map<Object, Object> context = new HashMap<Object, Object>();
        Component testcase = (Component) f.newComponent(
                "functionalTests.component.collectiveitf.gathercast.testcase", context);
        //        Component clientB = (Component) f.newComponent("functionalTests.component.collectiveitf.gather.GatherClient("+VALUE_2+")",context);
        //        Component server = (Component) f.newComponent("functionalTests.component.collectiveitf.gather.GatherServer",context);
        //        Fractal.getBindingController(clientA).bindFc("client", server.getFcInterface("serverGather"));
        //        Fractal.getBindingController(clientB).bindFc("client", server.getFcInterface("serverGather"));
        Fractal.getLifeCycleController(testcase).startFc();

        for (int i = 0; i < 100; i++) {
            // several iterations for thoroughly testing concurrency issues
            BooleanWrapper result1 = ((TotoItf) testcase.getFcInterface("testA")).test();
            BooleanWrapper result2 = ((TotoItf) testcase.getFcInterface("testB")).test();

            Assert.assertTrue(result1.booleanValue());
            Assert.assertTrue(result2.booleanValue());
        }

        String result1 = ((TotoItf) testcase.getFcInterface("testA")).testWaitForAll().stringValue();
        String result2 = ((TotoItf) testcase.getFcInterface("testB")).testWaitForAll().stringValue();
        Assert.assertNotSame(result1, result2);
    }
}
