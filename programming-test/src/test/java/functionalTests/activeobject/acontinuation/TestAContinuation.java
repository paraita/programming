/*
 * ProActive Parallel Suite(TM):
 * The Open Source library for parallel and distributed
 * Workflows & Scheduling, Orchestration, Cloud Automation
 * and Big Data Analysis on Enterprise Grids & Clouds.
 *
 * Copyright (c) 2007 - 2017 ActiveEon
 * Contact: contact@activeeon.com
 *
 * This library is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation: version 3 of
 * the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * If needed, contact us to obtain a release under GPL Version 2 or 3
 * or a different license than the AGPL.
 */
package functionalTests.activeobject.acontinuation;

import static org.junit.Assert.assertTrue;

import java.util.Vector;

import org.junit.Test;
import org.objectweb.proactive.api.PAActiveObject;
import org.objectweb.proactive.api.PAFuture;
import org.objectweb.proactive.core.config.CentralPAPropertyRepository;

import functionalTests.FunctionalTest;


/**
 * Test automatic continuations by results and parameters
 */

public class TestAContinuation extends FunctionalTest {
    AOAContinuation a;

    AOAContinuation b;

    AOAContinuation t1;

    AOAContinuation t2;

    AOAContinuation lastA;

    Id idPrincipal;

    Id idDeleguate;

    boolean futureByResult;

    private String finalResult;

    private String lastAResult;

    private String t1IdName;

    private String t2IdName;

    @Test
    public void action() throws Exception {
        boolean initial_ca_setting = CentralPAPropertyRepository.PA_FUTURE_AC.getValue();
        if (!CentralPAPropertyRepository.PA_FUTURE_AC.isTrue()) {
            CentralPAPropertyRepository.PA_FUTURE_AC.setValue(true);
        }
        ACThread acthread = new ACThread();
        acthread.start();
        acthread.join();
        CentralPAPropertyRepository.PA_FUTURE_AC.setValue(initial_ca_setting);

        assertTrue(futureByResult && a.isSuccessful());

        assertTrue(finalResult.equals("dummy"));
        assertTrue(lastAResult.equals("e"));

        assertTrue(t1IdName.equals("d"));
        assertTrue(t2IdName.equals("d"));
    }

    private class ACThread extends Thread {
        @Override
        public void run() {
            try {
                a = PAActiveObject.newActive(AOAContinuation.class, new Object[] { "principal" });
                //test future by result
                a.initFirstDeleguate();
                idDeleguate = a.getId("deleguate2");
                idPrincipal = a.getId("principal");
                Vector<Id> v = new Vector<Id>(2);
                v.add(idDeleguate);
                v.add(idPrincipal);
                if (PAFuture.waitForAny(v) == 0) {
                    futureByResult = false;
                } else {
                    futureByResult = true;
                }

                //test future passed as parameter
                b = PAActiveObject.newActive(AOAContinuation.class, new Object[] { "dummy" });
                idPrincipal = b.getIdforFuture();
                a.forwardID(idPrincipal);
                finalResult = a.getFinalResult();

                //Test non-blocking when future passed as parameter
                AOAContinuation c = PAActiveObject.newActive(AOAContinuation.class, new Object[] { "c" });
                AOAContinuation d = PAActiveObject.newActive(AOAContinuation.class, new Object[] { "d" });
                AOAContinuation e = PAActiveObject.newActive(AOAContinuation.class, new Object[] { "e" });

                AOAContinuation de = d.getA(e);
                AOAContinuation cde = c.getA(de);
                lastA = e.getA(cde);
                lastAResult = lastA.getIdName();

                //test multiple wrapped futures with multiples AC destinations
                AOAContinuation f = PAActiveObject.newActive(AOAContinuation.class, new Object[] { "f" });
                c.initSecondDeleguate();
                AOAContinuation t = c.delegatedGetA(d);
                t1 = e.getA(t);
                t2 = f.getA(t);

                t1IdName = t1.getIdName();
                t2IdName = t2.getIdName();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
