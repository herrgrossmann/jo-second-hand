/*
 * Copyright (c) 2011, grossmann
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 * * Neither the name of the jo-widgets.org nor the
 *   names of its contributors may be used to endorse or promote products
 *   derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL jo-widgets.org BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */

package org.jowidgets.sherp.app.ui.workbench;

import org.jowidgets.cap.ui.tools.workbench.CapWorkbenchModelBuilder;
import org.jowidgets.sherp.app.ui.application.SecondHandApplicationFactory;
import org.jowidgets.sherp.app.ui.defaults.SecondHandDefaultsInitializer;
import org.jowidgets.sherp.app.ui.defaults.SecondHandSilkIconsInitializer;
import org.jowidgets.sherp.app.ui.lookup.LookupInitializer;
import org.jowidgets.useradmin.ui.action.UserAdminPasswordChangeAction;
import org.jowidgets.useradmin.ui.application.UserAdminApplicationFactory;
import org.jowidgets.useradmin.ui.workbench.WorkbenchSettingsMenu;
import org.jowidgets.workbench.api.IWorkbench;
import org.jowidgets.workbench.api.IWorkbenchContext;
import org.jowidgets.workbench.api.IWorkbenchFactory;
import org.jowidgets.workbench.toolkit.api.IWorkbenchApplicationModel;
import org.jowidgets.workbench.toolkit.api.IWorkbenchInitializeCallback;
import org.jowidgets.workbench.toolkit.api.IWorkbenchModel;
import org.jowidgets.workbench.toolkit.api.IWorkbenchModelBuilder;
import org.jowidgets.workbench.toolkit.api.WorkbenchPartFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

public final class SecondHandWorkbench implements IWorkbenchFactory {

	private final boolean webapp;

	public SecondHandWorkbench() {
		this(false);
	}

	public SecondHandWorkbench(final boolean webapp) {
		this.webapp = webapp;
	}

	@Override
	public IWorkbench create() {
		SLF4JBridgeHandler.removeHandlersForRootLogger();
		SLF4JBridgeHandler.install();

		final IWorkbenchModelBuilder builder = new CapWorkbenchModelBuilder();
		builder.setLabel("SecondHand");

		if (webapp) {
			builder.setInitialMaximized(true);
			builder.setDecorated(false);
		}

		SLF4JBridgeHandler.removeHandlersForRootLogger();
		SLF4JBridgeHandler.install();

		SecondHandSilkIconsInitializer.initialize();
		SecondHandDefaultsInitializer.initialize(!webapp);

		LookupInitializer.initializeLookupsAsync();

		builder.addInitializeCallback(new IWorkbenchInitializeCallback() {
			@Override
			public void onContextInitialize(final IWorkbenchModel model, final IWorkbenchContext context) {
				model.getMenuBar().addMenu(new WorkbenchSettingsMenu());
				model.getToolBar().addSeparator();
				model.getToolBar().addAction(new UserAdminPasswordChangeAction());

				final IWorkbenchApplicationModel secondHandApp = SecondHandApplicationFactory.create();
				if (secondHandApp.getChildrenCount() > 0) {
					model.addApplication(secondHandApp);
				}

				final IWorkbenchApplicationModel userAdminApp = UserAdminApplicationFactory.create();
				if (userAdminApp.getChildrenCount() > 0) {
					model.addApplication(userAdminApp);
				}
			}
		});

		return WorkbenchPartFactory.workbench(builder.build());

	}

}
