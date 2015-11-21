package com.jiradev.jira.plugins.panels.issue;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.plugin.issuetabpanel.AbstractIssueAction;
import com.atlassian.jira.plugin.issuetabpanel.IssueTabPanelModuleDescriptor;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.security.roles.ProjectRole;
import com.atlassian.jira.security.roles.ProjectRoleActors;
import com.atlassian.jira.security.roles.ProjectRoleManager;
import com.plex.smash.client.SmashClient;
import com.plex.smash.client.SmashConfiguration;
import com.plex.smash.client.jobs.JobConfigurationQuery;
import com.plex.smash.client.jobs.JobConfigurationService;
import com.plex.smash.client.jobs.JobConfigurationSimple;
import com.plex.smash.client.jobs.JobConfigurationSimpleList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
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

/**
 * Created by Pavel_Pesetskiy on 11/20/2015.
 */
public class UserRoleIssueAction extends AbstractIssueAction {
    private ProjectRoleManager projectRoleManager = ComponentAccessor.getComponent(ProjectRoleManager.class);
    private TreeMap people = new TreeMap();
    private Project project;
    private Map<String, String> JobNameUpdateDate;
    private static final Logger log = LoggerFactory.getLogger(UserRoleIssueTabPanel.class);
    private SmashClient smashClient;
    private JobConfigurationSimpleList jobbbs;
    static String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public UserRoleIssueAction(IssueTabPanelModuleDescriptor issueTabPanelModuleDescriptor, Project project){
        super(issueTabPanelModuleDescriptor);
        this.project = project;
    }

    public Date getTimePerformed(){
        return null;
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

    public void populateVelocityParams(Map params){
        SmashConfiguration smashConfiguration = new SmashConfiguration();
        smashConfiguration.setEndpoint("https://code.plex.com");
        String key = getBambooConnectorPrivateKey();
        smashConfiguration.setPrivateKey(key);
        smashClient = SmashClient.getClient(smashConfiguration);
        JobConfigurationService jobs = smashClient.getJobs();
        JobConfigurationQuery query = new JobConfigurationQuery();
        query.setCreatedBy(4);


        try {
            jobbbs = jobs.query(query);
            JobNameUpdateDate = new HashMap<String, String>();
            for(Iterator<JobConfigurationSimple> i = jobbbs.iterator(); i.hasNext(); ) {
                JobConfigurationSimple item = i.next();
                String date = "";
                if(item.getUpdatedDate() != null){
                    date = item.getUpdatedDate().toString();
                }
                JobNameUpdateDate.put(item.getName(), date);

                System.out.println(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        log.info("Smash client initialized.");
        params.put("jobs",JobNameUpdateDate);
        //params.put("avatarService", ComponentAccessor.getAvatarService());
    }
}
