import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.application.runWriteAction
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiRecursiveElementWalkingVisitor
import com.intellij.psi.impl.source.tree.LeafPsiElement
import java.io.IOException

class PsiStatistics : AnAction() {
    /**
     * Написать тесты
     */

    /**
     * Метод подсчета элементов
     */
    private fun countStats(list: List<PsiElement>): MutableMap<String, Int> {
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

    /**
     * Главный метод
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
                val psiLeafElements = mutableListOf<LeafPsiElement>()

                //Сбор PSI элементов
                psiFile.accept(object : PsiRecursiveElementWalkingVisitor() {
                    override fun visitElement(element: PsiElement) {
                        super.visitElement(element)
                        if (element is LeafPsiElement) {
                            psiLeafElements.add(element)
                        }
                    }
                })
                //подсчет статистики
                val mapLeafString = countStats(psiLeafElements)

                //Заполнение файла
                var infoStatistics = ""
                mapLeafString.forEach { (psiElement, number) -> infoStatistics += "$psiElement - $number\n" }

                runWriteAction {
                    statFile?.setBinaryContent(infoStatistics.toByteArray())
                }
            }
            catch (ex: IOException) {
                Messages.showMessageDialog("File already exists", "Error", Messages.getErrorIcon())
            }
        }
    }
}