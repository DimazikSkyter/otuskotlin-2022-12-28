package ru.otus.homework2.entity

import jakarta.persistence.*
import ru.otus.homework2.converters.MedicineRecipeListConverter
import ru.otus.homework2.model.MedicineRecipe


@Entity
@Table(name = "medicine_results")
class MedicineResult(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Convert(converter = MedicineRecipeListConverter::class)
    var medicineRecipe: List<MedicineRecipe>
) {
    constructor() : this(null, listOf())
}