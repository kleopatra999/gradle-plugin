package hudson.plugins.gradle

import hudson.model.FreeStyleProject
import org.junit.Rule
import org.junit.Test
import org.jvnet.hudson.test.JenkinsRule
import org.jvnet.hudson.test.recipes.LocalData

class CompatibilityTest {
    @Rule
    public final JenkinsRule j = new JenkinsRule()

    @Test
    @LocalData
    void read_old_configuration_files() {
        FreeStyleProject p = (FreeStyleProject) j.jenkins.getItem("old");
        Gradle gradle = p.getBuildersList().get(Gradle)
        Gradle reference = configuredGradle()

        assert gradle.description == reference.description
        assert gradle.switches == reference.switches
        assert gradle.tasks == reference.tasks
        assert gradle.rootBuildScriptDir == reference.rootBuildScriptDir
        assert gradle.buildFile == reference.buildFile
        assert gradle.gradleName == reference.gradleName
        assert gradle.useWrapper == reference.useWrapper
        assert gradle.makeExecutable == reference.makeExecutable
        assert gradle.fromRootBuildScriptDir == reference.fromRootBuildScriptDir
        assert gradle.useWorkspaceAsHome == reference.useWorkspaceAsHome
        assert gradle.passAsProperties == reference.passAsProperties

        def installations = j.jenkins.getDescriptorByType(hudson.plugins.gradle.Gradle.DescriptorImpl).getInstallations()
        assert installations.size() == 1
        assert installations[0].name == '2.14'
    }

    private Gradle configuredGradle() {
        new Gradle("description", "switches", 'tasks', "rootBuildScript",
                "buildFile.gradle", '2.14', true, true, true,
                true, true)
    }
}
