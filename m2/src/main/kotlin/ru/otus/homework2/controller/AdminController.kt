package ru.otus.homework2.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.otus.homework2.responsemodel.AdminStatus

@RestController
@RequestMapping("/admin")
class AdminController {

    @PostMapping
    fun replaceClinic() : AdminStatus {
        return AdminStatus.ok("Clinic replaced.")
    }
}