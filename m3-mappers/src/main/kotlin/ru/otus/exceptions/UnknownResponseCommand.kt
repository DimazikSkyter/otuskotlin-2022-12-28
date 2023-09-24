package ru.otus.exceptions

import ru.otus.common.model.UserCommand

class UnknownResponseCommand(cmd: UserCommand) : RuntimeException("Command $cmd cannot be mapped to IResponse")
