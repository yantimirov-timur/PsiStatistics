import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.ui.Messages
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiRecursiveElementWalkingVisitor
import com.intellij.psi.impl.source.tree.LeafPsiElement

class PsiStatistics : AnAction() {
    /**
     * -Рассмотреть случай когда уже есть файл
     * -Разбить на функции
     * -Изменить request
     */

    override fun actionPerformed(event: AnActionEvent) {
        val psiFile = event.getData(CommonDataKeys.PSI_FILE)
        val psiFilePath = event.getData(CommonDataKeys.VIRTUAL_FILE)?.parent
        val statFile = psiFilePath?.createChildData("da", "${psiFile?.name}_PsiStat") //change requestor
        Messages.showMessageDialog("File successful created", "Stats", Messages.getInformationIcon())

        val psiLeafElements = mutableListOf<PsiElement>()

        //Сбор PSI элементов
        psiFile?.accept(object : PsiRecursiveElementWalkingVisitor() {
            override fun visitElement(element: PsiElement) {
                super.visitElement(element)
                if (element is LeafPsiElement) {
                    psiLeafElements.add(element)
                }
            }
        })
        //Подсчет статистики
        val mapLeafString = mutableMapOf<String, Int>()
        for (elem in psiLeafElements) {
            val stringElement = elem.toString()
            if (stringElement in mapLeafString) {
                mapLeafString[stringElement] = mapLeafString[stringElement]!! + 1
            } else {
                mapLeafString[stringElement] = 1
            }
        }
        //Заполнение файла
        var infoStatistics = ""
        mapLeafString.forEach { (psiElement, number) -> infoStatistics += "$psiElement - $number\n" }
        statFile?.setBinaryContent(infoStatistics.toByteArray())
    }
}