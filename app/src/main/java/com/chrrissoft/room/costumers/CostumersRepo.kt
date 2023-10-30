package com.chrrissoft.room.costumers

import kotlinx.coroutines.flow.StateFlow

interface CostumersRepo {

    val all: StateFlow<Costumer>

    fun insert(vararg costumer: Costumer) : List<Boolean>

    fun update(vararg costumer: Costumer) : List<Long>

    fun delete(vararg costumer: Costumer) : List<Long>
}

