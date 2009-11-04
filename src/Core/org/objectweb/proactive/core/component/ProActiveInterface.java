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
package org.objectweb.proactive.core.component;

import org.objectweb.fractal.api.Interface;
import org.objectweb.proactive.annotation.PublicAPI;
import org.objectweb.proactive.core.mop.StubObject;


/**
 * Extension of the Fractal {@link Interface} to deal with ProActive concerns.
 *
 * @author The ProActive Team
 */
@PublicAPI
public interface ProActiveInterface extends Interface, StubObject {

    /**
     * Return the object implementing this interface.
     * 
     * @return The implementing object
     */
    public abstract Object getFcItfImpl();

    /**
     * Sets the object to which this interface reference object should delegate
     * method calls.
     *
     * @param impl the object to which this interface reference object should
     *      delegate method calls.
     * @see #getFcItfImpl getFcItfImpl
     */
    public abstract void setFcItfImpl(final Object impl);
}
