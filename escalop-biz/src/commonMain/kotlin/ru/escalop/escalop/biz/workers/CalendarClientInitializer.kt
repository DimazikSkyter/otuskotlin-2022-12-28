package ru.escalop.escalop.biz.workers

import ru.escalop.common.EscalopContext
import ru.escalop.common.calendar.ICalendarClient
import ru.escalop.common.errors.errorAdministration
import ru.escalop.common.errors.fail
import ru.escalop.common.model.WorkMode
import ru.escalop.escalop.ICorChainDsl
import ru.escalop.escalop.worker

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