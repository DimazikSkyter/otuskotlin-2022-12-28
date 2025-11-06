package ru.escalop.exceptions

import ru.escalop.common.model.UserCommand

class UnknownResponseCommand(cmd: UserCommand) : RuntimeException("Command $cmd cannot be mapped to IResponse")
