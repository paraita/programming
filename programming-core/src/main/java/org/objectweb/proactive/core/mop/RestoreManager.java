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
package org.objectweb.proactive.core.mop;

import java.util.ArrayList;
import java.util.List;


/**
 * A restore manager logs all modifications made to an object
 * through the method  @see MOP#replaceObject() and is able to
 * restore the initial state of the object
 */
public class RestoreManager implements FieldToRestore {

    protected Object objectToRestore;

    protected List<FieldToRestore> list;

    public RestoreManager() {
        this.list = new ArrayList<FieldToRestore>();
    }

    /**
     * add a new modification to be taken into account by this manager
     * @param f
     */
    public void add(FieldToRestore f) {
        list.add(f);
    }

    public Object restore(Object modifiedObject) throws IllegalArgumentException, IllegalAccessException {
        Object result = modifiedObject;

        for (FieldToRestore f : list) {
            Object r = f.restore(modifiedObject);
            if (r != null) {
                result = r;
            }
        }
        return result;
    }

}
