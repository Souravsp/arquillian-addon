package test.integration.support.assertions;

import org.jboss.forge.addon.dependencies.Dependency;
import org.jboss.forge.addon.dependencies.builder.DependencyBuilder;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.facets.DependencyFacet;

public class ManagedDependencyAssert extends DependencyAssert<ManagedDependencyAssert> {

    public ManagedDependencyAssert(Project project, String gav) {
        super(DependencyBuilder.create(gav), project.getFacet(DependencyFacet.class), ManagedDependencyAssert.class);
    }

    @Override
    public Dependency getDependency(DependencyBuilder dependencyBuilder) {
        return dependencyFacet.getDirectManagedDependency(dependencyBuilder);
    }

    @Override
    public boolean hasDependency(DependencyBuilder dependencyBuilder) {
        return dependencyFacet.hasDirectManagedDependency(dependencyBuilder);
    }

}
