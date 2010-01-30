package jira;

// this class based on http://svn.atlassian.com/svn/public/atlassian/jira/jira-development-kit/trunk/examples/rpc-client-sample/src/java/com/atlassian/jira/rpc/client/sample/XmlRpcClientExample.java
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.XmlRpcException;

import play.Play;

import java.io.IOException;
import java.util.*;
import java.net.*;

public class XmlRpcJiraClient {

    public static final String JIRA_ENDPOINT = Play.configuration.getProperty("jiraEndpoint");
    public static final String USER_NAME = Play.configuration.getProperty("jiraUsername");
    public static final String PASSWORD = Play.configuration.getProperty("jiraPassword");

    public void doStuff() {
        try {
            XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
            config.setServerURL(new URL(JIRA_ENDPOINT));

            // Initialise RPC Client
            XmlRpcClient rpcClient = new XmlRpcClient();
            rpcClient.setConfig(config);


            // Login and retrieve logon token
            Vector loginParams = new Vector(2);
            loginParams.add(USER_NAME);
            loginParams.add(PASSWORD);
            String loginToken = (String) rpcClient.execute("jira1.login", loginParams);
            System.out.println(loginToken);

            // Retrieve & print projects
            Vector loginTokenVector = new Vector(1);
            loginTokenVector.add(loginToken);

            System.out.println(rpcClient.execute("jira1.getProjectsNoSchemes", loginTokenVector));

            HashMap[] projects = (HashMap[]) rpcClient.execute("jira1.getProjectsNoSchemes", loginTokenVector);
            HashMap p = (HashMap) projects[0];
            System.out.println(p);
            /*
            for (Iterator iterator = projects.iterator(); iterator.hasNext();)
            {
            Map project =  (Map) iterator.next();
            System.out.println(project.get("name") + " with lead " + project.get("lead"));
            }
             */
            /*
            // Create an issue
            Hashtable struct = new Hashtable();
            // Constants for issue creation
            struct.put("summary", "XML RPC issue " + new Date());
            struct.put("description", "old desc");
            struct.put("project", "TST");
            struct.put("type", "1");
            struct.put("assignee", "admin");
            struct.put("customFieldValues", makeVector(makeCustomFieldHashtable("customfield_10000", "10000")));

            //Map issueMap = (Map) rpcClient.execute("jira1.createIssue", new Vector(EasyList.build(loginToken, struct)));
            //String key = (String) issueMap.get("key");
            //System.out.println("Returned issue key: " + key);


            // Update an issue
            System.out.println("issueMap:old description: " + issueMap.get("description"));
            Hashtable updateFields = makeHashtable("description", makeVector("New updated description " + new Date()));
            //issueMap = (Map) rpcClient.execute("jira1.updateIssue", new Vector(EasyList.build(loginToken, key, updateFields)));
            //System.out.println("issueMap:new description: " + issueMap.get("description"));

            // Add a comment
            //rpcClient.execute("jira1.addComment", new Vector(EasyList.build(loginToken, key, "New comment " + new Date())));

            //Vector issues = (Vector) rpcClient.execute("jira1.getIssuesFromFilter", new Vector(EasyList.build(loginToken, "10070")));
            //for (Iterator iterator = issues.iterator(); iterator.hasNext();)
            //{
            //    System.out.println("Issues: " + iterator.next());
            //}
             */
            // Log out
            Boolean bool = (Boolean) rpcClient.execute("jira1.logout", loginTokenVector);
            System.out.println("Logout successful: " + bool);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlRpcException e) {
            e.printStackTrace();
        }

    }

    private static Hashtable makeHashtable(String s, Object s1) {
        Hashtable t = new Hashtable();
        t.put(s, s1);
        return t;
    }

    private static Hashtable makeCustomFieldHashtable(String customFieldId, String value) {
        Hashtable t = new Hashtable();
        t.put("customfieldId", customFieldId);
        t.put("values", makeVector(value));
        return t;
    }

    private static Vector makeVector(Object p0, Object p1) {
        Vector v = new Vector(2);
        v.add(p0);
        v.add(p1);
        return v;
    }

    private static Vector makeVector(Object p0) {
        Vector v = new Vector(1);
        v.add(p0);
        return v;
    }
}
