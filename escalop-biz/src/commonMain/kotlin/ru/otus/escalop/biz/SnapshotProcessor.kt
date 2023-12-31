package ru.otus.escalop.biz

import ru.otus.common.EscalopContext
import ru.otus.common.EscalopCorSettings
import ru.otus.common.model.UserCommand
import ru.otus.escalop.biz.workers.*
import ru.otus.escalop.biz.workers.stubs.*
import ru.otus.escalop.biz.workers.validation.finishValidation
import ru.otus.escalop.biz.workers.validation.validateDocumentFormat
import ru.otus.escalop.biz.workers.validation.validation
import ru.otus.escalop.chain
import ru.otus.escalop.rootChain


class SnapshotProcessor(
    private val corSettings: EscalopCorSettings = EscalopCorSettings(),
) {

    suspend fun exec(ctx: EscalopContext) =
        BusinessChain.exec(ctx.apply { this.settings = this@SnapshotProcessor.corSettings })

    companion object {
        private val BusinessChain = rootChain<EscalopContext> {
            initStatus("Инициализация статуса")
            initStorage("Инициализация хранилища снапшотов")
            initGoogleCalendar("Инициализация клиентской части работы с календарем")
            operation("Пустая команда", UserCommand.NONE) {
                emptyResponse("Пустой ответ на пустую команду")
            }
            operation("Загрузка нового документа", UserCommand.UPLOAD) {
                stubs("Обработка стабов") {
                    stubCreateSuccessResponseOnUpload("Имитация успешной обработки")
                    stubSessionNotFoundException("Ошибка пользовательской сессии")
//                    stubValidationBadDescription("Имитация ошибки валидации описания")
//                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
//                    validateAutorization("Проверка авторизации пользователя")
                    validateDocumentFormat("Проверка документа для формирования снапшота")

                    finishValidation("Завершение проверок")
                }
                chain {
                    title = "Обработка документа"
                    generateSnapshot("Формирование снапшота")
                    saveSnapshotToStorage("Сохранение снапшота в кассандре")
                    createCalendarEventWithSnapshotInformation("Сохранение снапшота в календарь")
                    updateSnapshotInformation("Апдейт снапшта с учетом сохранения в календаре")

                    uploadFinished("Обработка загрузки документа завершена")
                }
                prepareUploadResult("Подготовка ответа")
            }
            operation("Чтение снапшота", UserCommand.READ) {
                stubs("Обработка стабов") {
                    stubCreateSuccessResponseOnRead("Возврат снапшота-загрушки")
                    stubSnapshotNotFound("Документ не найден")
                }
                validation {

                }
                chain {
                    title = "Чтение документа по индексу"
                    readSnapshotFromCalendar("Чтение документа из гугл календаря")
                }
                prepareReadSnapshotResult("Подготовка ответа")
            }
            operation("Поиск снапшота", UserCommand.SEARCH) {
                stubs("Обработка стабов") {
                    stubCreateSuccessResponseOnSearch("Возврат снапшота стаба")
                    stubSnapshotNotFoundOnSearch("Документ не найден")
                }
                validation {

                }
                chain {
                    title = "Поиск снапшота по параметрам"
                    prepareDbSnapshotFilterRequest("Подготовка запроса к базе хранения метаданных по снапшотам")
                    getSnapshotsInfoByDbSnapshotFilterRequest("Выполнение запроса по получению метаданных снапшотов, соответствующих фильтру")
                }
                prepareSearchSnapshotResult("Подготовка ответа по поиску снапшотов по указанным фильтрам")
            }
        }.build()
    }
}
