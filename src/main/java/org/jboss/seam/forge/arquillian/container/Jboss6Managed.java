package org.jboss.seam.forge.arquillian.container;

import javax.inject.Inject;

import org.jboss.forge.project.Project;
import org.jboss.forge.project.dependencies.DependencyBuilder;
import org.jboss.forge.project.dependencies.ScopeType;

public class Jboss6Managed implements Container
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
                .setArtifactId("arquillian-jbossas-managed-6")
                .setVersion(arquillianVersion)
                .setScopeType(ScopeType.TEST);

      DependencyBuilder dep2 = DependencyBuilder.create()
                .setGroupId("org.jboss.jbossas")
                .setArtifactId("jboss-server-manager")
                .setVersion("1.0.3.GA")
                .setScopeType(ScopeType.TEST);

      DependencyBuilder dep3 = DependencyBuilder.create()
                .setGroupId("org.jboss.jbossas")
                .setArtifactId("jboss-as-client")
                .setVersion("6.0.0.Final")
                .setPackagingType("pom")
                .setScopeType(ScopeType.TEST);

      builder.addProfile("jbossas-managed-6", dep1, dep2, dep3);
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
