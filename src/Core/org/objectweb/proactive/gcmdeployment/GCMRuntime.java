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
package org.objectweb.proactive.gcmdeployment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.objectweb.proactive.annotation.PublicAPI;
import org.objectweb.proactive.core.node.Node;
import org.objectweb.proactive.core.runtime.VMInformation;


@PublicAPI
public class GCMRuntime implements Serializable {
    protected VMInformation vmInfo;
    protected List<Node> nodes;

    public GCMRuntime(VMInformation vmInfo, Set<Node> nodes) {
        super();
        this.vmInfo = vmInfo;
        this.nodes = new ArrayList<Node>(nodes);
    }

    public String getName() {
        return vmInfo.getName();
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void update(Set<Node> nodes) {
        for (Node node : nodes) {
            if (!this.nodes.contains(node)) {
                this.nodes.add(node);
            }
        }
    }
}
