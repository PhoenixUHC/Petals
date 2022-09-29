package io.github.petals.structures

import groovy.transform.CompileStatic
import groovy.transform.TypeCheckingMode
import io.github.petals.api.structures.*

import redis.clients.jedis.JedisPooled

@CompileStatic
class PetalsBaseImpl implements PetalsBase {
    private String uniqueId
    protected JedisPooled pooled

    PetalsBaseImpl(String uniqueId, JedisPooled pooled) {
        this.uniqueId = uniqueId
        this.pooled = pooled
    }

    String uniqueId() {
        this.uniqueId
    }

    boolean exists() {
        this.pooled.exists(this.uniqueId)
    }

    void delete() {
        this.pooled.del(this.uniqueId)
    }

    def getProperty(String name) {
        if (name == "uniqueId") return this.uniqueId
        if (name == "pooled") return this.pooled

        String value = this.pooled.hget(this.uniqueId, name)
        if (value == null) return null
        if (value == "true") return true
        if (value == "false") return false

        if (value.isNumber()) {
            if (value.contains(".")) return value.toDouble()
            else return value.toLong()
        }
        return value
    }

    void setProperty(String name, value) {
        if (name == "uniqueId" || name == "pooled") return

        this.pooled.hset(this.uniqueId, name, String.valueOf(value))
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    def asType(Class target) {
        def res = target.getConstructor().newInstance()
        res.metaClass = this.metaClass

        target.getDeclaredMethods().each {
            def name = "${Character.toLowerCase(it.name.charAt(3))}${it.name.substring(4)}"
            if (name != "metaClass") {
                if (it.name.startsWith("get"))
                    res.metaClass."${it.name}" = { this.getProperty(name) }
                else if (it.name.startsWith("set"))
                    res.metaClass."${it.name}" = { value -> this.setProperty(name, value)}
            }
        }

        return res
    }
}

