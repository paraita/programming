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
package org.objectweb.proactive.ic2d.timit.editparts.timeline;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.objectweb.proactive.ic2d.timit.data.timeline.SequenceObject;
import org.objectweb.proactive.ic2d.timit.figures.timeline.SequenceFigure;


public class SequenceEditPart extends AbstractGraphicalEditPart {
    protected Label sequenceLabelFigure;
    protected SequenceFigure sequenceFigure;

    public SequenceEditPart(SequenceObject model) {
        setModel(model);
        model.setEp(this);
    }

    @Override
    protected IFigure createFigure() {
        SequenceObject model = (SequenceObject) getModel();
        TimeLineChartEditPart parent = (TimeLineChartEditPart) getParent();
        this.sequenceFigure = new SequenceFigure(parent.getTimeIntervalManager(), model,
            (ScrollingGraphicalViewer) parent.getViewer());
        return sequenceFigure;
    }

    @Override
    protected void createEditPolicies() {
    }
}