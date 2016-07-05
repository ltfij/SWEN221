package com.bytebach.server;

import java.util.*;
import java.io.*;
import java.net.*;
import com.bytebach.model.*;

/**
 * This is a very simple implementation of a webserver. All it does is process
 * simple HTTP GET commands, and then respond appropriately.
 * 
 * @author djp
 * 
 */
public class WebServer {
    private volatile boolean exit = false;
    private final String stylesheet;
    private final Database bachDB;

    public WebServer(Database bachDB, String stylesheet) {
        this.bachDB = bachDB;
        this.stylesheet = stylesheet;
    }

    public void run() {
        try {
            ServerSocket ss = new ServerSocket(8080);
            while (!exit) {
                // Wait for a socket
                Socket s = ss.accept();
                // Ok, if we get here, then we got a connection
                System.out.println("ACCEPTED CONNECTION FROM: " + s.getInetAddress());
                processRequest(s);
                // finally, close the socket!
                s.close();
            }

        } catch (IOException e) {
            // something bad happened
            System.err.println(e.getMessage());
        }
    }

    public void processRequest(Socket s) throws IOException {
        // First, determine page being requested.
        try {
            String request = readRequest(s);
            System.out.println("RECEIVED REQUEST: " + request.length() + " bytes");
            String httpCommand = stripHttpGetCommand(request);
            String page = httpCommand.split(" ")[1];
            String response = "";

            if (page.equals("/")) {
                response = mainPage();
            } else if (page.startsWith("/view")) {
                Map<String, String> params = splitParameters(page);
                response = tablePage(params.get("table"));
            } else if (page.startsWith("/add")) {
                Map<String, String> params = splitParameters(page);
                if (params.size() == 1) {
                    int id = createTableRow(params.get("table"));
                    response = editPage(id, params.get("table"));
                } else {
                    // process update
                    response = updatePage(params);
                }
            } else if (page.startsWith("/edit")) {
                Map<String, String> params = splitParameters(page);
                if (params.size() == 2) {
                    response = editPage(Integer.parseInt(params.get("id")), params.get("table"));
                } else {
                    // process update
                    response = updatePage(params);
                }
            } else if (page.startsWith("/delete")) {
                Map<String, String> params = splitParameters(page);
                response = deletePage(Integer.parseInt(params.get("id")), params.get("table"));
            } else {
                response = htmlHeader() + "Unrecognised Request" + htmlFooter();
            }

            sendResponse(httpResponse(response), s);
            System.out.println("SENT RESPONSE: " + response.length() + " bytes");
        } catch (Exception e) {
            sendResponse(httpResponse(exceptionPage(e)), s);
            System.out.println("EXCEPTION: " + e);
        }
    }

    public String redirect(String target) {
        String r = htmlHeader();
        r += "<script type=\"text/javascript\">location.href='" + target + "'</script>";
        return r + htmlFooter();
    }

    private Map<String, String> splitParameters(String str) {
        int idx = str.indexOf('?');
        if (idx == -1 || idx >= str.length()) {
            return Collections.EMPTY_MAP;
        } else {
            HashMap<String, String> fields = new HashMap();
            String[] params = str.substring(idx + 1).split("&");
            for (String p : params) {
                String[] pair = p.split("=");
                if (pair.length > 1) {
                    fields.put(pair[0], pair[1].replace("+", " ").replace("%0D%0A", "\n"));
                } else if (pair.length > 0) {
                    fields.put(pair[0], "");
                }
            }
            return fields;
        }
    }

    /**
     * This method looks for the HTTP GET command, and returns that; or, null if
     * none was found.
     * 
     * @param request
     * @return
     */
    public String stripHttpGetCommand(String request) throws IOException {
        BufferedReader r = new BufferedReader(new StringReader(request));
        String line = "";
        while ((line = r.readLine()) != null) {
            if (line.startsWith("GET")) {
                // found the get command
                return line;
            }
        }
        return null;
    }

    /**
     * This method reads all possible data from the socket and returns it.
     * 
     * @param s
     * @return
     * @throws IOException
     */
    public String readRequest(Socket s) throws IOException {
        Reader input = new InputStreamReader(new BufferedInputStream(s.getInputStream()));
        String request = "";
        char[] buf = new char[1024];
        int nread;

        // Read from socket until nothing left.
        do {
            nread = input.read(buf);
            String in = new String(buf, 0, nread);
            request += in;
        } while (nread == 1024);

        return request;
    }

    public void sendResponse(String response, Socket s) throws IOException {
        Writer output = new OutputStreamWriter(s.getOutputStream());
        output.write(response);
        output.flush();
    }

    public String httpResponse(String body) {
        return "HTTP/1.1 200 OK\n" + "Content-Length: " + body.length() + "\n"
                + "Content-Type: text/html; charset=UTF-8\n\n" + body;
    }

    public String htmlHeader() {
        // Any HTML buff: feel free to tell me why this is bad...
        return "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">"
                + "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\">"
                + "<title>Bernies Byte Batch</title><link rel=\"stylesheet\" type=\"text/css\" media=\"screen\" href=\""
                + stylesheet
                + "\"></head><body><div id=\"frame\"><div id=\"header\"><h1>Byte Bach Order System</h1></div>"
                + "<div id=\"page\"><div id=\"innerpage\"><div id=\"content\">";
    }

    public String htmlFooter() {
        return "</div></div>" + toolBar()
                + "</div><div id=\"footer\"><h1>&copy; Copyright David J. Pearce 2010</h1></div></div></body></html>";
    }

    public String toolBar() {
        String r = "<div id=\"toolbar\"><form name=\"toolbuttons\"><div id=\"toolviews\">";
        r += "<input type=\"button\" value=\"Orders\" onclick=\"window.location.href='/view?table=Orders'\">";
        r += "<input type=\"button\" value=\"Customers\" onclick=\"window.location.href='/view?table=Customers'\">";
        r += "<input type=\"button\" value=\"Categories\" onclick=\"window.location.href='/view?table=Categories'\">";
        r += "<input type=\"button\" value=\"Items\" onclick=\"window.location.href='/view?table=Items'\">";
        return r + "</div></form></div>";
    }

    public int createTableRow(String title) {
        Table table = bachDB.table(title);
        // First, determine new id
        int id = 0;
        List<Field> fields = table.fields();
        for (List<Value> row : table.rows()) {
            for (int i = 0; i != row.size(); ++i) {
                if (fields.get(i).isKey()) {
                    IntegerValue iv = (IntegerValue) row.get(i);
                    id = Math.max(iv.value() + 1, id);
                }
            }
        }

        // Second, add a default row
        List<Value> row = new ArrayList<Value>();
        for (Field f : fields) {
            if (f.isKey()) {
                row.add(new IntegerValue(id));
            } else if (f.type() == Field.Type.BOOLEAN) {
                row.add(new BooleanValue(false));
            } else if (f.type() == Field.Type.INTEGER) {
                row.add(new IntegerValue(0));
            } else if (f.type() == Field.Type.TEXT) {
                row.add(new StringValue(""));
            } else if (f.type() == Field.Type.TEXTAREA) {
                row.add(new StringValue(""));
            } else {
                row.add(null); // default value for references
            }
        }

        table.rows().add(row);

        return id;
    }

    public String tableView(Table table) {
        String r = "<center><table class=\"listing\">";
        r += "<tr class=\"title\">";
        for (Field f : table.fields()) {
            r += "<td>" + f.title() + "</td>";
        }
        r += "</tr>";
        boolean odd = false;
        for (List<Value> values : table.rows()) {
            if (odd) {
                r += "<tr class=\"odd\">";
            } else {
                r += "<tr class=\"even\">";
            }
            boolean firstTime = true;
            for (Value o : values) {

                if (o instanceof IntegerValue) {
                    r += "<td class=\"id\">";
                } else {
                    r += "<td>";
                }

                if (firstTime) {
                    r += "<a href=\"/edit?table=" + table.name() + "&id=" + o + "\">" + o + "</a></td>";
                    firstTime = false;
                } else {
                    if (o instanceof ReferenceValue) {
                        ReferenceValue ref = (ReferenceValue) o;
                        List<Value> row = bachDB.table(ref.table()).row(ref.keys());
                        r += " <a href=\"/edit?table=" + ref.table() + "&id=" + row.get(0) + "\">" + ""
                                + referenceString(ref) + "</a>";
                    } else {
                        r += o + "</td>";
                    }
                }
            }
            r += "</tr>";
            odd = !odd;
        }
        r += "</table><form>";
        r += "<input type=\"button\" value=\"Add New Entry\" onclick=\"window.location.href='/add?table=" + table.name()
                + "'\">";
        return r + "</form></center>";
    }

    private String referenceString(ReferenceValue ref) {
        List<Value> row = bachDB.table(ref.table()).row(ref.keys());
        List<Field> fields = bachDB.table(ref.table()).fields();
        String r = "";
        boolean firstTime = true;
        for (int i = 0; i != fields.size(); ++i) {
            Field f = fields.get(i);
            Object o = row.get(i);
            if (!f.isKey()) {
                if (!firstTime) {
                    r += ",";
                }
                firstTime = false;
                r += o;
            }
        }
        if (r.length() > 50) {
            return r.substring(0, 50) + "...";
        } else {
            return r;
        }
    }

    private List<FormItem> createFormItems(Table table, List<Value> data) {
        ArrayList<FormItem> form = new ArrayList<FormItem>();
        List<Field> fields = table.fields();

        for (int i = 0; i != data.size(); ++i) {
            Value d = data.get(i);
            Field f = fields.get(i);
            Field.Type type = f.type();
            if (f.isKey()) {
                form.add(new TextLabel(f.title(), d.toString()));
            } else if (type == Field.Type.BOOLEAN) {
                form.add(new CheckBox(f.title(), ((BooleanValue) d).value()));
            } else if (type == Field.Type.INTEGER) {
                form.add(new TextField(f.title(), ((IntegerValue) d).toString()));
            } else if (type == Field.Type.TEXT) {
                form.add(new TextField(f.title(), ((StringValue) d).value()));
            } else if (type == Field.Type.TEXTAREA) {
                form.add(new TextArea(f.title(), ((StringValue) d).value(), 5, 25));
            } else if (type == Field.Type.REFERENCE) {
                ArrayList<String> values = new ArrayList<String>();
                Table refTable = bachDB.table(f.refTable());
                for (List<Value> row : refTable.rows()) {
                    String r = "[" + refTable.name();
                    for (Object o : row) {
                        r += ":" + o;
                    }
                    r += "]";
                    values.add(r);
                }
                form.add(new ComboBox(f.title(), values));
            }
        }

        return form;
    }

    public String deletePage(int id, String title) {
        Table table = bachDB.table(title);
        table.delete(new IntegerValue(id));
        return redirect("/view?table=" + title);
    }

    public String editPage(int id, String title) {
        Table table = bachDB.table(title);
        List<Value> row = table.row(new IntegerValue(id));
        List<FormItem> form = createFormItems(table, row);

        String page = htmlHeader();
        page += "<h2>" + title + "</h2>";
        page += "<div id=\"editbox\"><form name=\"dunno\">";
        page += "<input type=\"hidden\" name=\"table\" value=\"" + title + "\">";
        page += "<input type=\"hidden\" name=\"id\" value=\"" + id + "\">";
        page += "<table>";
        for (FormItem e : form) {
            page += "<tr><td valign=top>" + e.title + "</td><td>" + e.body();
            page += "</td></tr>";
        }
        page += "</table><br><br><center><input type=\"submit\" value=\"Update\">";
        page += "<input type=\"button\" value=\"Delete\" onclick=\"window.location.href='/delete?table=" + title
                + "&id=" + id + "'\">";
        page += "</center></form></div>";
        return page + htmlFooter();
    }

    /**
     * Process an update to the database.
     * 
     * @param params
     * @return
     */
    public String updatePage(Map<String, String> params) {
        String title = params.get("table");
        Table table = bachDB.table(title);
        int id = Integer.parseInt(params.get("id"));
        List<Value> row = table.row(new IntegerValue(id));
        List<Field> fields = table.fields();

        for (int i = 0; i != fields.size(); ++i) {
            Field f = fields.get(i);
            if (f.isKey()) {
                continue;
            }
            String nval = params.get(f.title());
            Field.Type type = f.type();
            if (type == Field.Type.BOOLEAN) {
                if (nval == null) {
                    row.set(i, new BooleanValue(false));
                } else {
                    row.set(i, new BooleanValue(true));
                }
            } else if (type == Field.Type.INTEGER) {
                row.set(i, new IntegerValue(Integer.parseInt(nval)));
            } else if (type == Field.Type.REFERENCE) {
                // the following is a big naughty
                nval = nval.replace("%3A", ":").replace("%5B", "[").replace("%5D", "]");
                nval = nval.substring(1, nval.length() - 1);
                String[] s = nval.split(":");
                String tab = s[0];
                Object[] keys = new Object[1];
                keys[0] = Integer.parseInt(s[1]);
                row.set(i, new ReferenceValue(tab, keys));
            } else {
                row.set(i, new StringValue(nval));
            }
        }

        return redirect("/view?table=" + title);
    }

    public String exceptionPage(Exception e) {
        String page = htmlHeader();
        page += "<h2>Internal Failure</h2>";
        page += e.getClass().getName() + "<br>";
        page += e.getMessage() + "<br>";
        for (StackTraceElement s : e.getStackTrace()) {
            page += s + "<br>";
        }
        return page + htmlFooter();
    }

    public String mainPage() {
        String page = htmlHeader();
        page += "Welcome to Bernies Byte Bach";
        return page + htmlFooter();
    }

    public String tablePage(String title) {
        String page = htmlHeader();
        page += "<h2>" + title + "</h2>";
        page += tableView(bachDB.table(title));
        return page + htmlFooter();
    }

    public abstract class FormItem {
        public String title;

        public abstract String body();
    }

    public class TextLabel extends FormItem {
        private String text;

        public TextLabel(String title, String text) {
            this.title = title;
            this.text = text;
        }

        public String body() {
            return text;
        }
    }

    public class CheckBox extends FormItem {
        private boolean checked;

        public CheckBox(String t, boolean checked) {
            this.title = t;
            this.checked = checked;
        }

        public String body() {
            String r = "<input type=\"checkbox\" name=\"" + title + "\"";
            if (checked) {
                r += " checked";
            }
            return r + ">";
        }
    }

    public class ComboBox extends FormItem {
        public List<String> values;

        public ComboBox(String t, List<String> values) {
            this.title = t;
            this.values = values;
        }

        public String body() {
            String r = "<select name=\"" + title + "\">";
            for (String value : values) {
                if (value.length() > 50) {
                    r += "<option value=\"" + value + "\">" + value.substring(0, 50) + "...";
                } else {
                    r += "<option value=\"" + value + "\">" + value;
                }
            }
            r += "</select>";
            return r;

        }
    }

    public class TextField extends FormItem {
        public String value;

        public TextField(String t, String v) {
            this.title = t;
            this.value = v;
        }

        public String body() {
            return "<input type=\"text\" name=\"" + title + "\" value=\"" + value + "\">";
        }
    }

    public class TextArea extends FormItem {
        public int nrows;
        public int ncols;
        public String value;

        public TextArea(String t, String v, int nrows, int ncols) {
            this.title = t;
            this.value = v;
            this.nrows = nrows;
            this.ncols = ncols;
        }

        public String body() {
            return "<textarea name=\"" + title + "\" cols=" + ncols + " rows=" + nrows + ">" + value + "</textarea>";
        }
    }
}
