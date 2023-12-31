package ru.otus.escalop.biz.workers

import ru.otus.common.EscalopContext
import ru.otus.common.calendar.ICalendarClient
import ru.otus.common.errors.errorAdministration
import ru.otus.common.errors.fail
import ru.otus.common.model.WorkMode
import ru.otus.escalop.ICorChainDsl
import ru.otus.escalop.worker

fun ICorChainDsl<EscalopContext>.initGoogleCalendar(title: String) = worker {
    this.title = title
    description = """
        Вычисление клиента работы с гугл календарем        
    """.trimIndent()
    handle {

        calendarClient = when {
            workMode == WorkMode.TEST -> settings.calendarTest
            workMode == WorkMode.STUB -> settings.calendarStub
            else -> settings.calendarProd
        }
        if (workMode != WorkMode.STUB && calendarClient == ICalendarClient.NONE) fail(
            errorAdministration(
                field = "clientCalendar",
                violationCode = "googleCalendarNotConfigured",
                description = "The google client is unconfigured for chosen workmode ($workMode). " +
                        "Please, contact the administrator staff!"
            )
        )
    }
}