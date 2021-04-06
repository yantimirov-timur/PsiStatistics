import com.intellij.psi.PsiElement
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import org.jetbrains.kotlin.idea.debugger.readAction
import org.junit.jupiter.api.Test


internal class PsiStatisticsTest : BasePlatformTestCase() {

    @Test
    fun countStats() {
        setUp()
        val psiStatistics = PsiStatistics()
        val list = mutableListOf<PsiElement>()
        myFixture.testDataPath = "src/test/resources/"
        val psiFileA = myFixture.configureByFile("A.kt")

        readAction {
            list.addAll(psiStatistics.collectPsi(psiFileA))
        }
        val expectedMapA = mapOf(
            "PsiElement(class)" to 1,
            "PsiElement(IDENTIFIER)" to 15,
            "PsiElement(LBRACE)" to 2,
            "PsiElement(val)" to 1,
            "PsiElement(EQ)" to 3,
            "PsiElement(INTEGER_LITERAL)" to 3,
            "PsiElement(var)" to 1,
            "PsiElement(fun)" to 2,
            "PsiElement(LPAR)" to 4,
            "PsiElement(COLON)" to 2,
            "PsiElement(COMMA)" to 2,
            "PsiElement(RPAR)" to 4,
            "PsiElement(PLUS)" to 2,
            "PsiElement(RBRACE)" to 2
        )
        val statisticsMapA = psiStatistics.countStats(list)
        list.clear()
        val psiFileB = myFixture.configureByFile("B.kt")

        readAction {
            list.addAll(psiStatistics.collectPsi(psiFileB))
        }
        val statisticsMapB = psiStatistics.countStats(list)

        val expectedMapB = mapOf(
            "PsiElement(class)" to 1,
            "PsiElement(IDENTIFIER)" to 7,
            "PsiElement(LBRACE)" to 1,
            "PsiElement(val)" to 3,
            "PsiElement(EQ)" to 6,
            "PsiElement(INTEGER_LITERAL)" to 6,
            "PsiElement(var)" to 3,
            "PsiElement(RBRACE)" to 1
        )

        assertEquals(expectedMapA, statisticsMapA)
        assertEquals(expectedMapB, statisticsMapB)
    }

}