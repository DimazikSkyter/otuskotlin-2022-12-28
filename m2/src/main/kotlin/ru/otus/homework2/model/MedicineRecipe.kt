package ru.otus.homework2.model

import ru.otus.homework2.entity.MedicineUnit

data class MedicineRecipe(
    var medicineUnit: MedicineUnit,
    var durationDays: Int,
    var timesPerDay: Int,
    var comment: String
)
