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
package org.objectweb.proactive.ic2d;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.objectweb.proactive.core.config.PAProperties;


/**
 * This class controls all aspects of the application's execution
 */
public class Application implements IApplication {
    private static final String fileName = "ic2d.java.policy";

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.IApplicationContext)
     */
    public Object start(IApplicationContext context) throws Exception {
        Application.searchJavaPolicyFile();

        // initialize the default log4j configuration
        if (System.getProperty("log4j.configuration") == null) {
            try {
                InputStream in = PAProperties.class.getResourceAsStream("proactive-log4j");

                // testing the availability of the file
                Properties p = new Properties();
                p.load(in);
                PropertyConfigurator.configure(p);
                PAProperties.LOG4J.setValue(PAProperties.class.getResource("proactive-log4j").toString());
            } catch (Exception e) {
                final URL u = PAProperties.class.getResource("proactive-log4j");
                System.err.println("IC2D:the default log4j configuration file (" + u +
                    ") is not accessible, logging is disabled");
            }
        }

        final Display display = PlatformUI.createDisplay();
        try {
            final int returnCode = PlatformUI.createAndRunWorkbench(display,
                    new ApplicationWorkbenchAdvisor());
            if (returnCode == PlatformUI.RETURN_RESTART) {
                return IApplication.EXIT_RESTART;
            }
            return IApplication.EXIT_OK;
        } finally {
            display.dispose();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.equinox.app.IApplication#stop()
     */
    public void stop() {
        final IWorkbench workbench = PlatformUI.getWorkbench();
        if (workbench == null)
            return;
        final Display display = workbench.getDisplay();
        display.syncExec(new Runnable() {
            public void run() {
                if (!display.isDisposed())
                    workbench.close();
            }
        });
    }

    /**
     * Searches the '.ic2d.java.policy' file, if this one doesn't exist then a
     * new file is created.
     */
    private static final void searchJavaPolicyFile() {
        final String pathName = System.getProperty("user.home");
        final String separator = System.getProperty("file.separator");
        final String file = pathName + separator + fileName;

        try {
            // Seaches the '.ic2d.java.policy'
            new FileReader(file);
        }
        // If it doesn't exist
        catch (FileNotFoundException e) {
            BufferedWriter bw = null;
            try {
                System.out.println("[IC2D] Creates a new file: " + file);
                // Creates an '.ic2d.java.policy' file
                bw = new BufferedWriter(new FileWriter(file, false));
                PrintWriter pw = new PrintWriter(bw, true);
                pw.println("grant {");
                pw.println("permission java.security.AllPermission;");
                pw.println("};");
            } catch (IOException eio) {
                eio.printStackTrace();
            } finally {
                try {
                    bw.close();
                } catch (IOException eio) {
                    eio.printStackTrace();
                }
            }
        }
    }
}