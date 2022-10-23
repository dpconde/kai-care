package com.dpconde.kaicare.core.commons.service

interface DataTransformer<T, R> {

    /**
     * Object transformer
     */
    fun transform(from: T): R
}