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
 * as published by the Free Software Foundation; either version
 * 2 of the License, or any later version.
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
 *  Initial developer(s):               The ProActive Team
 *                        http://proactive.inria.fr/team_members.htm
 *  Contributor(s):
 *
 * ################################################################
 * $$PROACTIVE_INITIAL_DEV$$
 */
package org.objectweb.proactive.ic2d.timit.actions.timeline;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.objectweb.proactive.ic2d.timit.Activator;
import org.objectweb.proactive.ic2d.timit.editparts.timeline.TimeLineChartEditPart;


/**
 * This action is used when the user wants to expand the time line. 
 * @author The ProActive Team
 *
 */
public class ExpandSizeAction extends Action {
    public static final String EXPAND_TIMELINE_ACTION = "Expand TimeLine Action";
    private TimeLineChartEditPart durationChartEditPart;

    public ExpandSizeAction() {
        super.setId(EXPAND_TIMELINE_ACTION);
        super.setImageDescriptor(ImageDescriptor.createFromURL(FileLocator.find(Activator.getDefault()
                .getBundle(), new Path("icons/expand_timeline.gif"), null)));
        super.setToolTipText(EXPAND_TIMELINE_ACTION);
        super.setEnabled(false);
    }

    public final void setTarget(final TimeLineChartEditPart durationChartEditPart) {
        super.setEnabled(true);
        this.durationChartEditPart = durationChartEditPart;
    }

    @Override
    public final void run() {
        if (this.durationChartEditPart != null) {
            this.durationChartEditPart.expandWidth();
        }
    }
}
