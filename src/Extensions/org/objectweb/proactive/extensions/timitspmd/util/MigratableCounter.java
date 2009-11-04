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
package org.objectweb.proactive.extensions.timitspmd.util;

/**
 * A MigratableCounter is a TimerCounter which can be migrated using ProActive
 * migration.<br>
 * This counter use a network clock and is much slower than a classic
 * TimerCounter.<br>
 * Use with care.
 *
 * @author The ProActive Team
 *
 */
public class MigratableCounter extends TimerCounter {

    /**
     *
     */
    private TimItReductor netclock;
    private long elapsed;
    private long latency;

    /**
     * Create an instance of MigratableCounter
     *
     * @param s
     *            the name of the counter you want to create
     */
    public MigratableCounter(String s) {
        super(s);
    }

    /**
     * Used by the Timer to know if a counter can be migrated
     */
    @Override
    public boolean isMigratable() {
        return true;
    }

    /**
     * Invoked by TimItReductor to setup network clock (which is localized on
     * TimItReductor instance node
     *
     * @param tr
     *            the TimItReductor instance
     */
    public void setClock(TimItReductor tr) {
        this.netclock = tr;
    }

    /**
     * Start the counter (perform a distant method call)
     */
    @Override
    public void start() {
        this.latency = System.currentTimeMillis();
        this.elapsed = this.netclock.getCurrentTimeMillis(); // ask time to
        // network clock

        this.latency = System.currentTimeMillis() - this.latency;
        this.elapsed -= (this.latency / 2); // adjust and consider an symetrical
        // connection
    }

    /**
     * Stop the counter (perform a distant method call)
     */
    @Override
    public void stop() {
        this.latency = System.currentTimeMillis();
        this.elapsed = this.netclock.getCurrentTimeMillis() - this.elapsed;
        this.latency = System.currentTimeMillis() - this.latency;
        this.elapsed -= (this.latency / 2); // adjust and consider an symetrical
        // connection

        super.setValue((int) this.elapsed);
    }
}
