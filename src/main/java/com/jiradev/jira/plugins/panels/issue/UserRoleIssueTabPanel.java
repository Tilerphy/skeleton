package com.jiradev.jira.plugins.panels.issue;

import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.util.EasyList;
import com.plex.smash.client.SmashClient;
import com.plex.smash.client.SmashConfiguration;
import com.plex.smash.client.jobs.JobConfigurationQuery;
import com.plex.smash.client.jobs.JobConfigurationService;
import com.plex.smash.client.jobs.JobConfigurationSimple;
import com.plex.smash.client.jobs.JobConfigurationSimpleList;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.atlassian.jira.plugin.issuetabpanel.AbstractIssueTabPanel;
import com.atlassian.jira.plugin.issuetabpanel.IssueTabPanel;
import com.atlassian.jira.issue.tabpanels.GenericMessageAction;
import com.atlassian.jira.issue.Issue;
import com.atlassian.crowd.embedded.api.User;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;
//import com.plex.smash.client.SmashClient;

public class UserRoleIssueTabPanel extends AbstractIssueTabPanel
{
    private static final Logger log = LoggerFactory.getLogger(UserRoleIssueTabPanel.class);
    private SmashClient smashClient;
    private JobConfigurationSimpleList jobbbs;
    static String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public static String getBambooConnectorPrivateKey() {
        String filename = "d:/downloads/1448079544013.pem";


        String str = "";
        try {
            str = readFile(filename, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    public List getActions(Issue issue, ApplicationUser remoteUser) {
        log.debug(issue.getId().toString());
        return EasyList.build(new UserRoleIssueAction(super.descriptor, issue.getProjectObject()));
    }

    public boolean showPanel(Issue issue, ApplicationUser remoteUser)
    {
        return true;
    }
}
