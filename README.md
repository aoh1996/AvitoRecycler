Изменчивый Recycler
У нас есть экран с RecyclerView, на нем есть список в два столбца, изначально 15 элементов.

Требования:
У элемента отображается его номер и кнопка удалить, которая его удаляет, то есть такая плиточка с номером и кнопкой
В системе работает что-то асинхронное, которое раз в 5 секунд добавляет новый элемент на случайную позициию
Номер элемента все время наращивается
Добавления и удаления производятся с анимацией (можно стандартной)
Вся эта система поддерживает поворот экрана и продолжает работать после него

Требования посложнее:
Сделать пулл номеров удаленных элементов и новые добавлять из пула, и если там пусто просто наращивать номер
В вертикальном положении сделать две колонки, в горизонтальном четыре

Примечания:
Задание желательно выполнять на Kotlin.
Выполненное задание нужно загрузить на github.
