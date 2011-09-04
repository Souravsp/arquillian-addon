package org.jboss.seam.forge.arquillian.container;

import java.io.ByteArrayInputStream;

import javax.inject.Inject;

import org.apache.maven.model.Build;
import org.apache.maven.model.BuildBase;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.Profile;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.codehaus.plexus.util.xml.Xpp3DomBuilder;
import org.jboss.forge.maven.MavenCoreFacet;
import org.jboss.forge.maven.dependencies.MavenDependencyAdapter;
import org.jboss.forge.project.Project;
import org.jboss.forge.project.ProjectModelException;
import org.jboss.forge.project.dependencies.DependencyBuilder;
import org.jboss.forge.project.dependencies.ScopeType;

public class Jboss6Embedded implements Container
{
   @Inject
   Project project;
   @Inject
   ProfileBuilder builder;

   @Override
   public void installDependencies(String arquillianVersion)
   {

      DependencyBuilder dep1 = DependencyBuilder.create()
                .setGroupId("org.jboss.arquillian.container")
                .setArtifactId("arquillian-jbossas-embedded-6")
                .setVersion(arquillianVersion)
                .setScopeType(ScopeType.TEST);

      DependencyBuilder dep2 = DependencyBuilder.create()
                .setGroupId("org.jboss.jbossas")
                .setArtifactId("jboss-as-depchain")
                .setVersion("6.0.0.Final")
                .setPackagingType("pom")
                .setScopeType(ScopeType.TEST);

      DependencyBuilder dep3 = DependencyBuilder.create()
                .setGroupId("org.jboss.jbossas")
                .setArtifactId("jboss-server-manager")
                .setVersion("1.0.3.GA")
                .setScopeType(ScopeType.TEST);

      MavenCoreFacet facet = project.getFacet(MavenCoreFacet.class);

      Profile profile = new Profile();
      profile.setId("jbossas-embedded-6");
      profile.addDependency(new MavenDependencyAdapter(dep1));
      profile.addDependency(new MavenDependencyAdapter(dep2));
      profile.addDependency(new MavenDependencyAdapter(dep3));

      Plugin plugin = new Plugin();
      plugin.setArtifactId("maven-surefire-plugin");

      try
      {
         Xpp3Dom dom = Xpp3DomBuilder
                  .build(
                           new ByteArrayInputStream(
                                    ("<configuration>"
                                             +
                                             "   <additionalClasspathElements>"
                                             +
                                             "       <additionalClasspathElement>${env.JBOSS_HOME}/client/jbossws-native-client.jar</additionalClasspathElement>"
                                             +
                                             "       <additionalClasspathElement>${env.JBOSS_HOME}/server/default/deploy/jbossweb.sar</additionalClasspathElement>"
                                             +
                                             "   </additionalClasspathElements>"
                                             +
                                             "   <redirectTestOutputToFile>true</redirectTestOutputToFile>"
                                             +
                                             "   <trimStackTrace>false</trimStackTrace>"
                                             +
                                             "   <printSummary>true</printSummary>"
                                             +
                                             "   <forkMode>once</forkMode>"
                                             +
                                             "   <argLine>-Xmx512m -XX:MaxPermSize=256m -Djava.net.preferIPv4Stack=true -Djava.util.logging.manager=org.jboss.logmanager.LogManager -Djava.endorsed.dirs=${env.JBOSS_HOME}/lib/endorsed -Djboss.home=${env.JBOSS_HOME} -Djboss.boot.server.log.dir=${env.JBOSS_HOME}</argLine>"
                                             +
                                             "</configuration>").getBytes()),
                           "UTF-8");

         plugin.setConfiguration(dom);
      }
      catch (Exception e)
      {
         throw new ProjectModelException(e);
      }

      BuildBase build = new Build();
      build.addPlugin(plugin);
      profile.setBuild(build);

      Model pom = facet.getPOM();
      pom.addProfile(profile);
      facet.setPOM(pom);
   }

    @Override
    public String installContainer(String location) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String installContainerToDefaultLocation() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean supportsContainerInstallation() {
        return false;
    }
}
