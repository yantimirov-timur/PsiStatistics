import com.intellij.build.output.KotlincOutputParser
import com.intellij.configurationStore.getOrCreateVirtualFile
import com.intellij.ide.SelectInManager.getProject
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectBundle
import com.intellij.openapi.project.getProjectDataPathRoot
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileFactory
import com.intellij.testFramework.ParsingTestCase
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.intellij.testFramework.fixtures.IdeaProjectTestFixture
import com.intellij.testFramework.fixtures.IdeaTestFixtureFactory
import com.intellij.testFramework.fixtures.TestFixtureBuilder

import org.junit.jupiter.api.Test
import com.intellij.testFramework.fixtures.JavaTestFixtureFactory

import com.intellij.testFramework.builders.JavaModuleFixtureBuilder


internal class PsiStatisticsTest{

    @Test
    fun test(){
        var l = mutableListOf<PsiElement>()
        var foo = PsiStatistics()
        foo.countStats(l)

    }

}