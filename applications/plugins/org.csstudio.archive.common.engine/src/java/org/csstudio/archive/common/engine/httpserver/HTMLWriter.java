/*******************************************************************************
 * Copyright (c) 2010 Oak Ridge National Laboratory.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package org.csstudio.archive.common.engine.httpserver;

import java.io.PrintWriter;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletResponse;

import org.csstudio.data.values.ITimestamp;
import org.csstudio.data.values.TimestampFactory;

/** Helper for creating uniform HTML pages for a servlet response.
 *  @author Kay Kasemir
 */
@SuppressWarnings("nls")
public class HTMLWriter {
    protected static final String BACKGROUND = "images/blueback.jpg"; //$NON-NLS-1$
    protected static final String RED_FONT = "<font color='#ff0000'>"; //$NON-NLS-1$
    protected static final String CLOSE_FONT = "</font>"; //$NON-NLS-1$

    /** Writer */
    private final PrintWriter html;

    /** Helper for marking every other table line */
    private boolean oddTableRow = true;

    /** @return HTML Writer with start of HTML page.
     *  @param resp Response for which to create the writer
     *  @param title HTML title
     *  @throws Exception on error
     */
    public HTMLWriter(@Nonnull final HttpServletResponse resp,
                      @Nonnull final String title) throws Exception {
        resp.setContentType("text/html");
        html = resp.getWriter();
        text("<html>");
        text("<head>");
        text("<title>" + title + "</title>");
        text("<script type=\"text/javascript\" src=\"/sorttable.js\"></script>\n");
        text("</head>");
        text("<body background='" + BACKGROUND + "'>");
        text("<blockquote>");
        h1(title);

    }

    /** Add end of HTML page. */
    public void close() {
        text("<p>");
        text("<hr width='50%' align='left'>");

        text("<a href=\"/main\">-Main-</a> ");
        text("<a href=\"/groups\">-Groups-</a> ");
        text("<a href=\"/disconnected\">-Disconnected-</a> ");
        text("<a href=\"/version.html\">-Version-</a> ");

        text("<address>");
        text(TimestampFactory.now().format(ITimestamp.Format.DateTimeSeconds));
        text("   <i>(Use web browser's Reload to refresh this page)</i>");
        text("</address>");

        text("</blockquote>");
        text("</body>");
        text("</html>");
        html.close();
    }

    /** Add text to HTML */
    protected void text(@Nonnull final String text) {
        html.println(text);
    }
    /** Add header */
    protected void h1(@Nonnull final String text) {
        text("<h1>" + text + "</h1>");
    }

    /** Add header */
    protected void h2(@Nonnull final String text) {
        text("<h2>" + text + "</h2>");
    }

    /** Start a table.
     *  <p>
     *  The intial column header might span more than one column.
     *  In fact, it might be the only columns header.
     *  Otherwise, the remaining column headers each span one column.
     *
     *  @param initialColSpan Number of columns for the first header.
     *  @param header Headers for all the columns
     *  @see #tableLine(String[])
     *  @see #closeTable()
     */
    protected void openTable(final int initialColSpan,
                             @Nonnull final String[] headers) {
        text("<table border='0' class='sortable'>");
        text("<thead>");
        text("  <tr bgcolor='#FFCC66'>");
        text("    <th align='center' colspan='" + initialColSpan + "'>" +
                        headers[0] + "</th>");
        for (int i=1; i<headers.length; ++i) {
            text("    <th align='center'>" + headers[i] + "</th>");
        }
        text("  </tr>");
        text("</thead>");
        text("<tbody>");
        oddTableRow = true;
    }

    /** One table line.
     *  @param columns Text for each column.
     *                 Count must match the colspan of openTable
     *  @see #openTable(int, String[])
     */
    protected void tableLine(@Nonnull final String[] columns) {
        text("  <tr>");
        boolean first = true;
        for (final String column : columns) {
            if (first) {
                first = false;
                if (oddTableRow) {
                    text("    <th align='left' valign='top'>" + column + "</th>");
                } else {
                    text("    <th align='left' valign='top' bgcolor='#DFDFFF'>" + column + "</th>");
                }
            } else {
                if (oddTableRow) {
                    text("    <td align='center' valign='top'>" + column + "</td>");
                } else {
                    text("    <td align='center' valign='top' bgcolor='#DFDFFF'>" + column + "</td>");
                }
            }
        }
        text("  </tr>");
        oddTableRow = !oddTableRow;
    }

    /** Close a table */
    protected void closeTable() {
        text("</tbody>");
        text("</table>");
    }

    /** Create HTML for a link
     *  @param url Linked URL
     *  @param text Text to display
     *  @return HTML for the link
     */
    @Nonnull
    public static String makeLink(@Nonnull final String url,
                                  @Nonnull final String text) {
        return "<a href=\"" + url + "\">" + text + "</a>";
    }

    /** @return HTML for red text */
    @Nonnull
    public static String makeRedText(@Nonnull final String text) {
        return RED_FONT + text + CLOSE_FONT;
    }

}
