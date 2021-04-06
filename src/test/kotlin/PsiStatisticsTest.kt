import com.intellij.psi.PsiElement
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import junit.framework.TestCase
import org.jetbrains.kotlin.idea.KotlinFileType
import org.jetbrains.kotlin.idea.debugger.readAction
import org.junit.jupiter.api.Test


internal class PsiStatisticsTest : BasePlatformTestCase() {

    @Test
    fun countStats() {
        setUp()
        val psiStatistics = PsiStatistics()
        var list = mutableListOf<PsiElement>()
        var f = myFixture.configureByText(
            KotlinFileType.INSTANCE, """
            class A(val a: String) {
               val a =2
               var b = 3
            }
 
        """.trimIndent()
        )
        readAction {
            list.addAll(psiStatistics.collectPsi(f))
        }
        list
        val l1 = f.language
        f
        assertEquals(2, 1 + 1)
    }


    fun collectPsi() {
    }
}