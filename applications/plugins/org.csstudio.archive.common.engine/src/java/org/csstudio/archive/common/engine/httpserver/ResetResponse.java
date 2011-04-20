/*******************************************************************************
 * Copyright (c) 2010 Oak Ridge National Laboratory.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package org.csstudio.archive.common.engine.httpserver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.csstudio.archive.common.engine.model.EngineModel;

/** Provide web page to reset engine statistics.
 *  @author Kay Kasemir
 */
@SuppressWarnings("nls")
class ResetResponse extends AbstractResponse
{
    /** Avoid serialization errors */
    private static final long serialVersionUID = 1L;

    ResetResponse(final EngineModel model)
    {
        super(model);
    }

    @Override
    protected void fillResponse(final HttpServletRequest req,
                    final HttpServletResponse resp) throws Exception
    {
        final HTMLWriter html =
            new HTMLWriter(resp, "Archive Engine Reset");

        html.text("Engine statistics are reset");
        getModel().resetStats();

        html.close();
    }
}
