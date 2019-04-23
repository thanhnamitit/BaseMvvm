package com.vti.base.functional

class Command {
    companion object {
        const val ALL_DATA_CHANGED = 1
        const val INSERTED_LIST = 2
        const val INSERTED_ONE = 3
        const val REMOVED = 4
        const val ALL_ITEM_LOADED = 5
        const val LOAD_MORE_FAILED = 6

        fun notifyAll() = Command(ALL_DATA_CHANGED)

        fun inserted(position: Int, count: Int) = Command(INSERTED_ONE).apply {
            this.position = position
            this.count = count
        }

        fun insertedOne(position: Int) = Command(INSERTED_LIST).apply {
            this.position = position
        }

        fun removed(position: Int) = Command(REMOVED).apply {
            this.position = position
        }

        fun allItemLoaded() = Command(ALL_ITEM_LOADED)
        fun loadMoreFailed(reason: Int) = Command(ALL_ITEM_LOADED, reason)
    }

    private constructor()
    private constructor(type: Int) {
        this.type = type
    }

    private constructor(type: Int, reason: Int) {
        this.type = type
        this.reason = reason
    }

    var type: Int = ALL_DATA_CHANGED
    var position = 0
    var count = 0
    var reason = 0
}