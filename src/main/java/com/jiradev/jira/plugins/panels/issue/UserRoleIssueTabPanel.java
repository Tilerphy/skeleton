package com.jiradev.jira.plugins.panels.issue;

import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.util.EasyList;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.atlassian.jira.plugin.issuetabpanel.AbstractIssueTabPanel;
import com.atlassian.jira.plugin.issuetabpanel.IssueTabPanel;
import com.atlassian.jira.issue.tabpanels.GenericMessageAction;
import com.atlassian.jira.issue.Issue;
import com.atlassian.crowd.embedded.api.User;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
//import com.plex.smash.client.SmashClient;

public class UserRoleIssueTabPanel extends AbstractIssueTabPanel
{
    private static final Logger log = LoggerFactory.getLogger(UserRoleIssueTabPanel.class);

    public static String getBambooConnectorPrivateKey() {
        String filename = "d:/1448058129759.pem";
        String key = null;
        try {
            key = IOUtils.toString(UserRoleIssueTabPanel.class.getClassLoader().getResourceAsStream(filename), "UTF8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return key;
    }

    public List getActions(Issue issue, ApplicationUser remoteUser) {

        return EasyList.build(new UserRoleIssueAction(super.descriptor, issue.getProjectObject()));
    }

    public boolean showPanel(Issue issue, ApplicationUser remoteUser)
    {
        return true;
    }
}
