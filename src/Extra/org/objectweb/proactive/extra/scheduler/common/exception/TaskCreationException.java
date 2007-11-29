/*
 * ################################################################
 *
 * ProActive: The Java(TM) library for Parallel, Distributed,
 *            Concurrent computing with Security and Mobility
 *
 * Copyright (C) 1997-2007 INRIA/University of Nice-Sophia Antipolis
 * Contact: proactive@objectweb.org
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
 */
package org.objectweb.proactive.extra.scheduler.common.exception;


/**
 * Exceptions Generated if a problem occurred while creating a task.
 *
 * @author jlscheef - ProActive Team
 * @version 1.0, Jun 29, 2007
 * @since ProActive 3.2
 */
public class TaskCreationException extends SchedulerException {

    /** Serial version UID */
    private static final long serialVersionUID = 7699281034780102890L;

    /**
     * Attaches a message to the Exception.
     *
     * @param msg message attached.
     */
    public TaskCreationException(String msg) {
        super(msg);
    }

    /**
     * Create a new instance of TaskCreationException.
     */
    public TaskCreationException() {
        super();
    }

    /**
     * Create a new instance of TaskCreationException with the given message and cause
     *
     * @param msg the message to attach.
     * @param cause the cause of the exception.
     */
    public TaskCreationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * Create a new instance of TaskCreationException with the given cause.
     *
     * @param cause the cause of the exception.
     */
    public TaskCreationException(Throwable cause) {
        super(cause);
    }
}
