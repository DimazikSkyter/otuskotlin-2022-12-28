package ru.otus.exceptions

import java.lang.RuntimeException

class UnknownRequestClass(clazz: Class<*>) : RuntimeException("Class $clazz cannot be mapped to RequestContext")