package com.chrrissoft.room.base.view.event

import com.chrrissoft.room.base.view.handler.BaseEventHandler

interface BaseEvent<H : BaseEventHandler> {
    fun resolve(handler: H)
}
