import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.application.runWriteAction
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiRecursiveElementWalkingVisitor
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.impl.source.tree.LeafPsiElement
import java.io.IOException

class PsiStatistics : AnAction() {
    /**
     * Написать тесты
     */

    /**
     * Метод подсчета элементов
     */
    fun countStats(list: List<PsiElement>): MutableMap<String, Int> {
        val mapLeafString = mutableMapOf<String, Int>()
        for (elem in list) {
            val stringElement = elem.toString()
            if (stringElement in mapLeafString) {
                mapLeafString[stringElement] = mapLeafString[stringElement]!! + 1
            } else {
                mapLeafString[stringElement] = 1
            }
        }
        return mapLeafString
    }

    fun collectPsi(psiFile: PsiFile): MutableList<LeafPsiElement> {
        val psiLeafElements = mutableListOf<LeafPsiElement>()

        psiFile.accept(object : PsiRecursiveElementWalkingVisitor() {
            override fun visitElement(element: PsiElement) {
                super.visitElement(element)
                if (element is LeafPsiElement && element !is PsiWhiteSpace) {
                    psiLeafElements.add(element)
                }
            }
        })
        return psiLeafElements
    }

    /**
     * Главный метод для работы с action
     */
    override fun actionPerformed(event: AnActionEvent) {
        val psiFile = event.getData(CommonDataKeys.PSI_FILE)
        val psiFilePath = event.getData(CommonDataKeys.VIRTUAL_FILE)?.parent
        var statFile: VirtualFile? = null

        if (psiFile == null || psiFile.language.id != "kotlin") {
            Messages.showMessageDialog("Plugin work with .kt files", "Error", Messages.getErrorIcon())
        } else {
            try {
                runWriteAction {
                    statFile = psiFilePath?.createChildData("stat", "${psiFile.name}_PsiStat")
                }
                Messages.showMessageDialog("File successful created", "Error", Messages.getInformationIcon())

                val psiLeafElements = collectPsi(psiFile)        //Сбор PSI элементов
                val mapLeafString = countStats(psiLeafElements)  //подсчет статистики
                var infoStatistics = ""
                mapLeafString.forEach { (psiElement, number) -> infoStatistics += "$psiElement - $number\n" } //Заполнение файла
                runWriteAction {
                    statFile?.setBinaryContent(infoStatistics.toByteArray())
                }
            } catch (ex: IOException) {
                Messages.showMessageDialog("File already exists", "Error", Messages.getErrorIcon())
            }
        }
    }
}