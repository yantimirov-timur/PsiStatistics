package lesson3

import java.util.*
import kotlin.math.max

// attention: Comparable is supported but Comparator is not
class KtBinarySearchTree<T : Comparable<T>> : AbstractMutableSet<T>(), CheckableSortedSet<T> {

    private class Node<T>(
        val value: T
    ) {
        var left: Node<T>? = null
        var right: Node<T>? = null
    }

    private var root: Node<T>? = null

    override var size = 0
        private set

    private fun find(value: T): Node<T>? =
        root?.let { find(it, value) }

    private fun find(start: Node<T>, value: T): Node<T> {
        val comparison = value.compareTo(start.value)
        return when {
            comparison == 0 -> start
            comparison < 0 -> start.left?.let { find(it, value) } ?: start
            else -> start.right?.let { find(it, value) } ?: start
        }
    }

    override operator fun contains(element: T): Boolean {
        val closest = find(element)
        return closest != null && element.compareTo(closest.value) == 0
    }

    /**
     * Добавление элемента в дерево
     *
     * Если элемента нет в множестве, функция добавляет его в дерево и возвращает true.
     * В ином случае функция оставляет множество нетронутым и возвращает false.
     *
     * Спецификация: [java.util.Set.add] (Ctrl+Click по add)
     *
     * Пример
     */
    override fun add(element: T): Boolean {
        val closest = find(element)
        val comparison = if (closest == null) -1 else element.compareTo(closest.value)
        if (comparison == 0) {
            return false
        }
        val newNode = Node(element)
        when {
            closest == null -> root = newNode
            comparison < 0 -> {
                assert(closest.left == null)
                closest.left = newNode
            }
            else -> {
                assert(closest.right == null)
                closest.right = newNode
            }
        }
        size++
        return true
    }

    /**
     * Удаление элемента из дерева
     *
     * Если элемент есть в множестве, функция удаляет его из дерева и возвращает true.
     * В ином случае функция оставляет множество нетронутым и возвращает false.
     * Высота дерева не должна увеличиться в результате удаления.
     *
     * Спецификация: [java.util.Set.remove] (Ctrl+Click по remove)
     * (в Котлине тип параметера изменён с Object на тип хранимых в дереве данных)
     *
     * Средняя
     */
    override fun remove(element: T): Boolean {
        var current = root
        var parent = current
        var isLeft = false

        //Поиск элемента и его родителя и положения
        while (current!!.value != element) {
            parent = current

            if (element < current.value) {
                current = current.left
                isLeft = true
            } else {
                isLeft = false
                current = current.right
            }
            if (current == null) {
                return false
            }
        }
        //удаление листа
        if (current.left == null && current.right == null) {
            if (isLeft) {
                parent?.left = null
            } else
                parent?.right = null
        }
        //удаление левого поддерава
        else if (current.right == null) {
            if (current == root)
                root = current.left
            if (isLeft)
                parent?.left = current.left;
            else
                parent?.right = current.left
        }
        //удаление правого поддерева
        else if (current.left == null) {
            if (current == root)
                root = current.right
            if (isLeft)
                parent?.left = current.right
            else
                parent?.right = current.right
        }
        //удаление элемента имеющего два поддерева
        else if (current.left != null && current.right != null) {
            //нахождение преемника
            val successor: Node<T> = findSuccessor(current)

            //случай, когда у преемника нет потомка
            if (successor.left == null) {
                successor.left = current.left
                successor.right = current.right

                if (successor.left?.value == successor.value) {
                    successor.left = null
                } else {
                    var deleteNode = successor.left
                    var deleteNodeParent = successor.left

                    while (deleteNode?.value != successor.value) {
                        deleteNodeParent = deleteNode
                        deleteNode = deleteNode?.right
                    }

                    deleteNodeParent?.right = null
                }
            }
            //случай, когда у преемника есть потомок, которому нужно переприсвоить родителя
            else {
                var parentSuccessor = current.left
                var currentSuccessor = current.left

                if (currentSuccessor?.value == successor.value) {
                    successor.right = current.right
                } else {
                    while (currentSuccessor?.value != successor.value) {
                        parentSuccessor = currentSuccessor
                        currentSuccessor = currentSuccessor?.right

                    }
                    parentSuccessor!!.right = successor.left

                    successor.left = current.left
                    successor.right = current.right
                }
            }

            if (isLeft) {
                if (current == root)
                    root = successor
                else
                    parent?.left = successor
            } else {
                if (current == root)
                    root = successor
                else
                    parent?.right = successor
            }
        }

        size--
        return true
        //время: O(высота дерева)
        //средний случай: O(logN), худший случай: O(N)

        //По моему мнению, тесты к этой задаче достаточно полны
        //Если я не прав, поправьте
    }

    /**
     * Поиск преемника
     */
    private fun findSuccessor(deleteNode: Node<T>): Node<T> {
        var successor = deleteNode
        var current = successor.left

        while (current != null) {
            successor = current
            current = current.right
        }

        return successor
    }


    override fun comparator(): Comparator<in T>? =
        null

    override fun iterator(): MutableIterator<T> =
        BinarySearchTreeIterator()

    inner class BinarySearchTreeIterator internal constructor() : MutableIterator<T> {
        private var arrayDeque = ArrayDeque<Node<T>>()
        private var elementForDelete: Node<T>? = null

        init {
            if (root != null) {
                arrayDeque.addFirst(root!!)
            }
        }

        /**
         * Проверка наличия следующего элемента
         *
         * Функция возвращает true, если итерация по множеству ещё не окончена (то есть, если вызов next() вернёт
         * следующий элемент множества, а не бросит исключение); иначе возвращает false.
         *
         * Спецификация: [java.util.Iterator.hasNext] (Ctrl+Click по hasNext)
         *
         * Средняя
         */
        override fun hasNext(): Boolean = arrayDeque.isNotEmpty()  //время O(1)

        /**
         * Получение следующего элемента
         *
         * Функция возвращает следующий элемент множества.
         * Так как BinarySearchTree реализует интерфейс SortedSet, последовательные
         * вызовы next() должны возвращать элементы в порядке возрастания.
         *
         * Бросает NoSuchElementException, если все элементы уже были возвращены.
         *
         * Спецификация: [java.util.Iterator.next] (Ctrl+Click по next)
         *
         * Средняя
         */
        override fun next(): T {
            if (arrayDeque.isEmpty())
                throw IllegalStateException()

            val next = arrayDeque.pollFirst()//получение элемента и его удаление
            elementForDelete = next
            if (next.right != null) {
                arrayDeque.addFirst(next.right!!)
            }

            return next.value
            //время O(logN)
        }

        /**
         * Удаление предыдущего элемента
         *
         * Функция удаляет из множества элемент, возвращённый крайним вызовом функции next().
         *
         * Бросает IllegalStateException, если функция была вызвана до первого вызова next() или же была вызвана
         * более одного раза после любого вызова next().
         *
         * Спецификация: [java.util.Iterator.remove] (Ctrl+Click по remove)
         *
         * Сложная
         */
        override fun remove() {
            if (elementForDelete == null) {
                throw IllegalStateException()
            } else {
                //рекурсивное удаление
                remove(elementForDelete?.value)
                elementForDelete = null
            }
            //время O(n)
        }
    }

    /**
     * Подмножество всех элементов в диапазоне [fromElement, toElement)
     *
     * Функция возвращает множество, содержащее в себе все элементы дерева, которые
     * больше или равны fromElement и строго меньше toElement.
     * При равенстве fromElement и toElement возвращается пустое множество.
     * Изменения в дереве должны отображаться в полученном подмножестве, и наоборот.
     *
     * При попытке добавить в подмножество элемент за пределами указанного диапазона
     * должен быть брошен IllegalArgumentException.
     *
     * Спецификация: [java.util.SortedSet.subSet] (Ctrl+Click по subSet)
     * (настоятельно рекомендуется прочитать и понять спецификацию перед выполнением задачи)
     *
     * Очень сложная (в том случае, если спецификация реализуется в полном объёме)
     */
    override fun subSet(fromElement: T, toElement: T): SortedSet<T> {
        TODO()
    }

    /**
     * Подмножество всех элементов строго меньше заданного
     *
     * Функция возвращает множество, содержащее в себе все элементы дерева строго меньше toElement.
     * Изменения в дереве должны отображаться в полученном подмножестве, и наоборот.
     *
     * При попытке добавить в подмножество элемент за пределами указанного диапазона
     * должен быть брошен IllegalArgumentException.
     *
     * Спецификация: [java.util.SortedSet.headSet] (Ctrl+Click по headSet)
     * (настоятельно рекомендуется прочитать и понять спецификацию перед выполнением задачи)
     *
     * Сложная
     */
    override fun headSet(toElement: T): SortedSet<T> {
        TODO()
    }

    /**
     * Подмножество всех элементов нестрого больше заданного
     *
     * Функция возвращает множество, содержащее в себе все элементы дерева нестрого больше toElement.
     * Изменения в дереве должны отображаться в полученном подмножестве, и наоборот.
     *
     * При попытке добавить в подмножество элемент за пределами указанного диапазона
     * должен быть брошен IllegalArgumentException.
     *
     * Спецификация: [java.util.SortedSet.tailSet] (Ctrl+Click по tailSet)
     * (настоятельно рекомендуется прочитать и понять спецификацию перед выполнением задачи)
     *
     * Сложная
     */
    override fun tailSet(fromElement: T): SortedSet<T> {
        TODO()
    }

    override fun first(): T {
        var current: Node<T> = root ?: throw NoSuchElementException()
        while (current.left != null) {
            current = current.left!!
        }
        return current.value
    }

    override fun last(): T {
        var current: Node<T> = root ?: throw NoSuchElementException()
        while (current.right != null) {
            current = current.right!!
        }
        return current.value
    }

    override fun height(): Int =
        height(root)

    private fun height(node: Node<T>?): Int {
        if (node == null) return 0
        return 1 + max(height(node.left), height(node.right))
    }

    override fun checkInvariant(): Boolean =
        root?.let { checkInvariant(it) } ?: true

    private fun checkInvariant(node: Node<T>): Boolean {
        val left = node.left
        if (left != null && (left.value >= node.value || !checkInvariant(left))) return false
        val right = node.right
        return right == null || right.value > node.value && checkInvariant(right)
    }

}