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
package functionalTests.component.nonfunctional.creation.local.primitive;

import org.junit.Assert;
import org.objectweb.fractal.api.Component;
import org.objectweb.fractal.api.type.InterfaceType;
import org.objectweb.fractal.api.type.TypeFactory;
import org.objectweb.fractal.util.Fractal;
import org.objectweb.proactive.core.component.Constants;
import org.objectweb.proactive.core.component.ContentDescription;
import org.objectweb.proactive.core.component.ControllerDescription;
import org.objectweb.proactive.core.component.Fractive;
import org.objectweb.proactive.core.component.factory.ProActiveGenericFactory;
import org.objectweb.proactive.core.component.representative.ProActiveNFComponentRepresentative;

import functionalTests.ComponentTest;
import functionalTests.component.nonfunctional.creation.DummyControllerComponentImpl;
import functionalTests.component.nonfunctional.creation.DummyControllerItf;


/**
 * @author The ProActive Team
 *
 * creates a new non-functional component, marked as non-functional
 */
public class Test extends ComponentTest {
    Component dummyNFComponent;
    String name;
    String nodeUrl;

    public Test() {
        super("Creation of a primitive non functional-component on the local default node",
                "Test newNFcInstance method for a primitive component on the local default node");
    }

    /**
     * @see testsuite.test.FunctionalTest#action()
     */
    @org.junit.Test
    public void action() throws Exception {
        Thread.sleep(2000);
        Component boot = Fractal.getBootstrapComponent(); /*
         * Getting the Fractal-Proactive
         * bootstrap component
         */
        TypeFactory type_factory = Fractal.getTypeFactory(boot); /*
         * Getting the Fractal-ProActive
         * type factory
         */
        ProActiveGenericFactory cf = Fractive.getGenericFactory(boot); /*
         * Getting the
         * Fractal-ProActive generic
         * factory
         */

        dummyNFComponent = cf.newNFcInstance(type_factory.createFcType(new InterfaceType[] { type_factory
                .createFcItfType("fitness-controller-membrane", DummyControllerItf.class.getName(),
                        TypeFactory.SERVER, TypeFactory.MANDATORY, TypeFactory.SINGLE), }),
                new ControllerDescription("fitnessController", Constants.PRIMITIVE), new ContentDescription(
                    DummyControllerComponentImpl.class.getName()));

        Fractal.getLifeCycleController(dummyNFComponent).startFc();
        DummyControllerItf ref = (DummyControllerItf) dummyNFComponent
                .getFcInterface("fitness-controller-membrane");
        name = ref.dummyMethodWithResult();
        ref.dummyVoidMethod("Message");
        Assert.assertTrue(dummyNFComponent instanceof ProActiveNFComponentRepresentative);
        Fractal.getLifeCycleController(dummyNFComponent).stopFc();
    }
}
