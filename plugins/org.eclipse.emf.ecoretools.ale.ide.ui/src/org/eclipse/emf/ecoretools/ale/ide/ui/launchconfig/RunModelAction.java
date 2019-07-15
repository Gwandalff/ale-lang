/*******************************************************************************
 * Copyright (c) 2017 Inria and Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Inria - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecoretools.ale.ide.ui.launchconfig;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.ecoretools.ale.ALEInterpreter;
import org.eclipse.emf.ecoretools.ale.core.parser.Dsl;
import org.eclipse.emf.ecoretools.ale.ide.ui.EclipseWorkbenchDsl;
import org.eclipse.sirius.common.tools.api.interpreter.IInterpreterWithDiagnostic.IEvaluationResult;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.dialogs.ResourceListSelectionDialog;

public class RunModelAction {

	/**
	 * Open a selection dialog to get the model file before launch
	 */
	public static void launch(Shell shell, IResource dslFile) {
		
		/*
		 * Selected model
		 */
		ResourceListSelectionDialog dialog = new ResourceListSelectionDialog(shell, ResourcesPlugin.getWorkspace().getRoot(), IResource.FILE);
		dialog.setTitle("Resource Selection");
		dialog.open();
		Object[] selected = dialog.getResult();
		
		if(selected != null && selected.length == 1 && selected[0] instanceof IResource) {
			launch(dslFile,(IResource)selected[0]);
		}
	}
	
	/**
	 * Execute a DSL on a model
	 */
	public static void launch(IResource dslFile, IResource modelFile) {
		
		String dslProject = dslFile.getProject().getName();
		String modelLocation = modelFile.getLocationURI().toString();
		String modelProject = modelFile.getProject().getName();
		
		/*
		 * Init interpreter
		 */
		Set<String> projects = new HashSet<String>();
		Set<String> plugins = new HashSet<String>();
		projects.add(dslProject);
		projects.add(modelProject);
		ALEInterpreter interpreter = new ALEInterpreter();
		interpreter.javaExtensions.updateScope(plugins,projects);
		interpreter.javaExtensions.reloadIfNeeded();
		
		/*
		 * Eval
		 */
		Job evalJob = new Job("AQL Eval") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				
				MessageConsole console = findConsole("ALE Console");
				PrintStream oldOut = System.out;
				System.setOut(new PrintStream(console.newMessageStream()));
				
				System.out.println("\nRun "+dslFile.getName());
				System.out.println("------------");
				
				Thread execThread = new Thread("Aql eval thread"){
					@Override
					public void run() {
						try {
							Dsl dsl = new EclipseWorkbenchDsl(dslFile.getLocationURI().getPath().toString());
							IEvaluationResult result = interpreter.eval(modelLocation, new ArrayList<Object>(), dsl);
							interpreter.getLogger().diagnosticForHuman();
							
							if(result.getDiagnostic().getMessage() != null) {
								System.out.println(result.getDiagnostic().getMessage());
							}
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
						this.stop();
					}
				};
				execThread.start();
				
				while(execThread.isAlive()){
					if(monitor.isCanceled()){
						execThread.stop();
						return Status.CANCEL_STATUS;
					}
					 try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				if(execThread.isAlive()){
					execThread.stop();
				}
				
				System.setOut(oldOut);
				return Status.OK_STATUS;
			}
		};
		evalJob.schedule();
	}
	
	private static MessageConsole findConsole(String name) {
		ConsolePlugin plugin = ConsolePlugin.getDefault();
		IConsoleManager conMan = plugin.getConsoleManager();
		IConsole[] existing = conMan.getConsoles();
		for (int i = 0; i < existing.length; i++)
			if (name.equals(existing[i].getName()))
				return (MessageConsole) existing[i];
		// no console found, so create a new one
		MessageConsole myConsole = new MessageConsole(name, null);
		conMan.addConsoles(new IConsole[] { myConsole });
		return myConsole;
	}
}
